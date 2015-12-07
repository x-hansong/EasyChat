package com.easychat.service;

import com.easychat.exception.BadRequestException;
import com.easychat.model.entity.User;
import com.easychat.model.session.Session;

/**
 * Created by yonah on 15-10-18.
 */
public interface UserService {
    void addUser(String json)throws BadRequestException;
    boolean hasUser(Long id);
    User getUserByName(String name);
   /* Session authenticate(String json)throws BadRequestException;
    boolean isValid(String name,String password);*/
}
