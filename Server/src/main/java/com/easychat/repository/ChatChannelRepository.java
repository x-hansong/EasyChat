package com.easychat.repository;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yonah on 15-11-19.
 */
public class ChatChannelRepository {

    private ConcurrentHashMap<Integer, Channel> channels;

    private static final ChatChannelRepository repository = new ChatChannelRepository();

    private ChatChannelRepository() {
        this.channels = new ConcurrentHashMap<>();
    }
    public static ChatChannelRepository getInstance() {
        return repository;
    }


}
