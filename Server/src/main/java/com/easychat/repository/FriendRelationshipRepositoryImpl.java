package com.easychat.repository;

import com.easychat.repository.FriendRelationshipRepositoryCustom;

import java.util.Set;

/**
 * Created by yonah on 15-12-1.
 */
public class FriendRelationshipRepositoryImpl implements FriendRelationshipRepositoryCustom{
    @Override
    public Set<Long> getFriendSet(Long uid) {
        return null;
    }

    @Override
    public Set<Long> getOnlineFriendSet(Long uid) {
        return null;
    }

    @Override
    public Set<Long> getOfflineFriendSet(Long uid) {
        return null;
    }
}
