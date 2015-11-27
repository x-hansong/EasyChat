package com.easychat.repository;

import com.easychat.model.chat.ChatGroup;

/**
 * Created by yonah on 15-11-19.
 */
public interface ChatGroupRepository {
    void add(ChatGroup group);
    void remove(Long gid);
    ChatGroup get(Long gid);
}
