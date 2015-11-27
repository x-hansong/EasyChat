package com.easychat.repository;

import com.easychat.model.chat.ChatChannel;

/**
 * Created by yonah on 15-11-19.
 */
public interface ChatChannelRepository {
    void add(ChatChannel chatChannel);
    void remove(Long uid);
    ChatChannel get(Long uid);
}
