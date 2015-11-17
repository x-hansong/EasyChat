package com.easychat.service;

import com.easychat.model.User;

import java.util.Map;

/**
 * Created by yonah on 15-10-18.
 */
public interface UserService {
    String addUser(String json);
    boolean hasUser(Long id);
    User getUserByName(String name);
}
