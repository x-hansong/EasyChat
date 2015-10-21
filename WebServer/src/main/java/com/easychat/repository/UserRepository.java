package com.easychat.repository;

import com.easychat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yonah on 15-10-18.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findByName(String name);
}
