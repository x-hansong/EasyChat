package com.easychat.repository;

import java.util.Set;

/**
 * Created by yonah on 15-11-27.
 */
public interface GroupRelationshipRepositoryCustom {
    Set<Long> getGroupMemberSet(Long gid);
    Set<Long> getOnlineGroupMemberSet(Long gid);
    Set<Long> getOfflineGroupMemberSet(Long gid);
}
