package com.easychat.repository;

import com.easychat.model.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by king on 2015/12/7.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByName(String name);
}
