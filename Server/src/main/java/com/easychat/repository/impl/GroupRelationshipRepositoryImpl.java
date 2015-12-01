package com.easychat.repository.impl;

import com.easychat.repository.custom.GroupRelationshipRepositoryCustom;

import java.util.Set;

/**
 * Created by yonah on 15-12-1.
 */
public class GroupRelationshipRepositoryImpl implements GroupRelationshipRepositoryCustom {
    @Override
    public Set<Long> getGroupMemberSet(Long gid) {
        return null;
    }

    @Override
    public Set<Long> getOnlineGroupMemberSet(Long gid) {
        return null;
    }

    @Override
    public Set<Long> getOfflineGroupMemberSet(Long gid) {
        return null;
    }
}
