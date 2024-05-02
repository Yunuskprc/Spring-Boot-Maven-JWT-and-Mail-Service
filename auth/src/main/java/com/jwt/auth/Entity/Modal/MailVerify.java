package com.jwt.auth.Entity.Modal;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MailVerify")
public class MailVerify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    private Integer isComplicated; // 0 ise doğrulama tamamlanmamış 1 ise doğrulama tamamlanmış ve userInfo daki mailisVerify 1 olacak

    private Integer verifyCode;

    private LocalDateTime createdTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Integer getIsComplicated() {
        return isComplicated;
    }

    public void setIsComplicated(Integer isComplicated) {
        this.isComplicated = isComplicated;
    }

    public Integer getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(Integer verifyCode) {
        this.verifyCode = verifyCode;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
