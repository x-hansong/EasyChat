package com.easychat.repository;

import java.util.Set;

/**
 * Created by yonah on 15-11-27.
 */
public interface FriendRelationshipRepositoryCustom {
    Set<Long> getFriendSet(Long uid);
    Set<Long> getOnlineFriendSet(Long uid);
    Set<Long> getOfflineFriendSet(Long uid);
}
