package com.easychat.chatserver.model.Channel;

import io.netty.channel.Channel;

/**
 * Created by yonah on 15-11-19.
 */
public class ChatChannel {
    private int uid;
    private String token;
    private Channel channel;

    public ChatChannel(int uid, String token, Channel channel) {
        this.uid = uid;
        this.token = token;
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
