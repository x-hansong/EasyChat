package com.easychat.repository;

import com.easychat.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by king on 2015/12/7.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByName(String name);


}
