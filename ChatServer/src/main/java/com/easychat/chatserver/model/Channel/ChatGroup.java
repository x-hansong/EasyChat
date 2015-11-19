package com.easychat.chatserver.model.Channel;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;


/**
 * Created by yonah on 15-11-19.
 */
public class ChatGroup {
    private ChannelGroup group;
    private int gid;

    public ChatGroup(int gid) {
        this.gid = gid;
        this.group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
