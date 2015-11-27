package com.easychat.model.session;

import com.easychat.utils.CommonUtils;

/**
 * Created by yonah on 15-11-19.
 */
public class Token {
    private String UUID;

    public Token() {
        UUID = CommonUtils.getUUID();
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Token(String uuid) {
        UUID = uuid;
    }
}
