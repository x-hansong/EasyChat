package com.easychat.repository;

import com.easychat.model.entity.FriendRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yonah on 15-11-23.
 */
public interface FriendRelationshipRepository extends JpaRepository<FriendRelationship, Long>, FriendRelationshipRepositoryCustom{
    List<FriendRelationship> findByAid(Long aid);
}
