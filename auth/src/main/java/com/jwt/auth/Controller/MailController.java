package com.jwt.auth.Controller;

import com.jwt.auth.Entity.dto.MailStructure;
import com.jwt.auth.Entity.Modal.User;
import com.jwt.auth.Repository.UserRepository;
import com.jwt.auth.Service.Token.JwtService;
import com.jwt.auth.Service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/mailSend/{mail}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String sendMail(@PathVariable("mail") String mail, @RequestBody MailStructure mailStructure){
        System.out.println("girdik");
        mailService.sendMail(mail,mailStructure);
        return "Success mail";
    }

    @PostMapping("/mailVerifyCodeGenerate/{mail}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> createVerifyCode(@PathVariable("mail") String mail, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String token = authorizationHeader.substring(7);
        System.out.println(jwtService.extractUsername(token));
        Optional<User> user = userRepository.findByUsername(jwtService.extractUsername(token));
        return mailService.mailVerifyCodeGenerate(user.get(),mail);
    }

    @PostMapping("/mailVerifyCode/{verifyCode}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> verifyCode(@PathVariable Integer verifyCode, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String token = authorizationHeader.substring(7);
        Optional<User> user = userRepository.findByUsername(jwtService.extractUsername(token));
        return mailService.mailVerify(user.get(),verifyCode);
    }

}
