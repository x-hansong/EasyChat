package com.easychat.service;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.entity.User;

/**
 * Created by yonah on 15-10-18
 */
public interface UserService {
    void addUser(String json)throws BadRequestException;
    User authenticate(String json) throws BadRequestException, NotFoundException;
    void modifyUserInfo(String name,String json) throws BadRequestException, NotFoundException;
    String getUser(String name)throws NotFoundException;
    String getFriends(String name) throws NotFoundException;
    void deleteFriend(String userName,String friendName) throws NotFoundException;
    String getFriendInfo(String name,String friend_name)throws NotFoundException;
    String getStrangerInfo(String name,String stranger_name)throws NotFoundException;
}
