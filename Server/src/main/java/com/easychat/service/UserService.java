package com.easychat.service;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.BasicException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.entity.User;
import com.easychat.model.session.Session;
import com.easychat.model.session.Token;

/**
 * Created by yonah on 15-10-18.
 */
public interface UserService {
    void addUser(String json)throws BadRequestException;
    boolean hasUser(Long id);
    User getUserByName(String name);
    Session authenticate(String json)throws BadRequestException;
    boolean isUserValid(String name,String password);
    boolean logOff(Token token)throws BadRequestException;
    void modifyUserInfo(Token token,String name,String json) throws BadRequestException;
    String getUser(String name)throws NotFoundException;
}
