package com.easychat.service.impl;

import com.easychat.service.UserService;
import com.easychat.model.User;
import com.easychat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(Map data) {
        User user = new User();

        user.setName((String) data.get("name"));
        user.setPassword((String) data.get("password"));

        return userRepository.save(user);
    }

    @Override
    public boolean hasUser(Long id) {
        User user = userRepository.findOne(id);
        return user != null ? true:false;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findOne(id);
    }
}
