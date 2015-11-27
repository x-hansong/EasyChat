package com.easychat.model.chat;

import com.easychat.model.msg.text.TextMsg;

import java.util.concurrent.ConcurrentMap;


/**
 * Created by yonah on 15-11-19.
 */
public class ChatGroup {
    private int gid;

    private ConcurrentMap<Long, ChatChannel> channels;

    public ChatGroup(int gid) {
        this.gid = gid;
    }

    public void send(TextMsg msg) {

    }

    public void add(Long gid, ChatChannel chatChannel) {

    }

    public void close() {

    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
