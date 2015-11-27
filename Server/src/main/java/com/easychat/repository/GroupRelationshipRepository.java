package com.easychat.repository;

import com.easychat.model.GroupRelationship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yonah on 15-11-23.
 */
public interface GroupRelationshipRepository extends JpaRepository<GroupRelationship, Long>{
    List<GroupRelationship> findByGid(Long gid);
}
