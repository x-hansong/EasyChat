package com.easychat.repository;

import com.easychat.model.Channel.ChatGroup;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yonah on 15-11-19.
 */
public class ChatGroupRepository {
    private ConcurrentHashMap<Integer, ChatGroup> groups;

    private static final ChatGroupRepository repository = new ChatGroupRepository();

    private ChatGroupRepository() {
        this.groups = new ConcurrentHashMap<>();
    }

    public static ChatGroupRepository getInstance() {
        return repository;
    }
}
