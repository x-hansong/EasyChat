package com.easychat.model.session;

/**
 * Created by yonah on 15-11-19.
 */
public class Session {
    private Token token;
    private Long uid;
    private int expiredTime;


    public Session(Token token, Long uid, int expiredTime) {
        this.token = token;
        this.uid = uid;
        this.expiredTime = expiredTime;
    }

    public Session(Token token, Long uid) {
        this.token = token;
        this.uid = uid;
        this.expiredTime = 0;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }
}
