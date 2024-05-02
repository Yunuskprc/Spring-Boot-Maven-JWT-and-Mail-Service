package com.jwt.auth.Entity.Modal;

import jakarta.persistence.*;

@Entity
@Table(name = "userinfo")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String name;
    private String surName;
    private String adress;
    private Integer mailIsVerify;  // 0 ise doğrulanmamış 1 ise doğrulanmış
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getMailIsVerify() {
        return mailIsVerify;
    }

    public void setMailIsVerify(Integer mailIsVerify) {
        this.mailIsVerify = mailIsVerify;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
