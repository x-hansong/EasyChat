package com.easychat.model.entity;

/**
 * Created by king on 2015/12/19.
 */

/**
 * 用户类的临时存放对象
 */
public class UserTemp {
    private long  id;
    private String name;
    private String nick;
    private String sex;
    private String phone;
    private String email;
    private String avatar;
    private String sign_info;

    public UserTemp() {
    }


    public UserTemp(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.nick = user.getNick();
        this.sex = user.getSex();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.sign_info = user.getSignInfo();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getSign_info() {
        return sign_info;
    }

    public void setSign_info(String sign_info) {
        this.sign_info = sign_info;
    }
}
