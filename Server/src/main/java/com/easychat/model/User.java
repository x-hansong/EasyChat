package com.easychat.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yonah on 15-10-18.
 */
@Entity
@Table(name="User")
public class User implements Serializable{
    public User() {
        this.sex = 0;
        this.name = "";
        this.nick = "";
        this.password = "";
        this.phone = "";
        this.email = "";
        this.avatar = "";
        this.signInfo = "";
    }

    @Id
    @GeneratedValue
    @Column(name="id")
    private Long id;

    @Column(name="sex")
    private int sex;

    @Column(name="name")
    private String name;

    @Column(name="nick")
    private String nick;

    @Column(name="password")
    private String password;


    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="avatar")
    private String avatar;


    @Column(name="sign_info")
    private String signInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getSignInfo() {
        return signInfo;
    }

    public void setSignInfo(String signInfo) {
        this.signInfo = signInfo;
    }
}
