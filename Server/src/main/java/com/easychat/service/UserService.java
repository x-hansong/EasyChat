package com.easychat.service;

import com.easychat.model.entity.User;

/**
 * Created by yonah on 15-10-18
 */
public interface UserService {
    void addUser(String json);
    User authenticate(String json);
    void modifyUserInfo(String name,String json);
    String getUser(String name);
    String getFriends(String name);
    void deleteFriend(String userName,String friendName);
    String getFriendInfo(String name,String friend_name);
    String getStrangerInfo(String name,String stranger_name);
    boolean setFriendRelationship(String aName, String bName);
}
