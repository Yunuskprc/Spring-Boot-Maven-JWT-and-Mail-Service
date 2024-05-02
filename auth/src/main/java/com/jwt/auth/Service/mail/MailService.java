package com.jwt.auth.Service.mail;

import com.jwt.auth.Entity.dto.MailStructure;
import com.jwt.auth.Entity.Modal.MailVerify;
import com.jwt.auth.Entity.Modal.User;
import com.jwt.auth.Entity.Modal.UserInfo;
import com.jwt.auth.Repository.MailVerifyRepo;
import com.jwt.auth.Repository.UserInfoRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class MailService {

   @Autowired
   private JavaMailSender mailSender;

   @Autowired
   private MailVerifyRepo mailVerifyRepo;

   @Autowired
   private UserInfoRepo userInfoRepo;

   @Value("$(spring.mail.username)")
   private String fromMail;

    @Transactional
    public void sendMail(String mail, MailStructure mailStructure){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);
    }


    // verify 1 mi diye kontrol yapılmalı
    @Transactional
    public ResponseEntity<String> mailVerifyCodeGenerate(User user, String mail){
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int mailVerifyCode = min + random.nextInt(max - min + 1);

        try {
            Optional<MailVerify> findMailVerify = mailVerifyRepo.findByUserId(user.getId());
            if (findMailVerify.isPresent()){
                if(findMailVerify.get().getIsComplicated() == 1){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email already verified");
                }else if(findMailVerify.get().getIsComplicated() == 0){
                    findMailVerify.get().setVerifyCode(mailVerifyCode);
                    mailVerifyRepo.save(findMailVerify.get());
                }
            }else {
                MailVerify mailVerify = new MailVerify();
                LocalDateTime newDateTime = LocalDateTime.now();

                mailVerify.setIsComplicated(0);
                mailVerify.setVerifyCode(mailVerifyCode);
                mailVerify.setCreatedTime(newDateTime);
                mailVerify.setUser(user);
                mailVerifyRepo.save(mailVerify);
            }

            MailStructure mailStructure = new MailStructure();
            mailStructure.setSubject("Mail Verify");
            mailStructure.setMessage(Integer.toString(mailVerifyCode));
            sendMail(mail,mailStructure);

            return ResponseEntity.ok("Verify code sending mail is succes");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verify code sending mail is unsucces");
        }
    }


    @Transactional
    public ResponseEntity<String> mailVerify(User user,Integer verifyCode){
        try {
            Optional<MailVerify> mailVerify = mailVerifyRepo.findByUserId(user.getId());

            if(mailVerify.get().getIsComplicated() == 1){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already verified");
            }

            LocalDateTime isExpired = mailVerify.get().getCreatedTime().plusMinutes(5);
            LocalDateTime now = LocalDateTime.now();
            if(now.isAfter(isExpired))
            {
                mailVerifyRepo.deleteByUserId(user.getId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The verification code for the email has expired");
            }

            if(mailVerify.get().getVerifyCode().equals(verifyCode)){
                Optional<UserInfo> userInfo = userInfoRepo.findByUser(user);
                userInfo.get().setMailIsVerify(1);
                userInfoRepo.save(userInfo.get());
                Optional<MailVerify> newMailVerift = mailVerifyRepo.findByUserId(user.getId());
                newMailVerift.get().setIsComplicated(1);
                mailVerifyRepo.save(newMailVerift.get());
                return ResponseEntity.ok("verification process successful");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("incorrect verification code");
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
