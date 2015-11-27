package com.easychat.session;

/**
 * Created by yonah on 15-11-19.
 */
public class Session {
    private Token token;
    private String userName;
    private Long createdTime;
    private int expiredTime;


    public Session(Token token, String userName, Long createdTime, int expiredTime) {
        this.token = token;
        this.userName = userName;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
    }

    public Session(Token token, String userName) {
        this.token = token;
        this.userName = userName;
        this.createdTime = 0L;
        this.expiredTime = 0;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }
}
