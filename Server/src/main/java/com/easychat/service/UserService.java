package com.easychat.service;

import com.easychat.model.dto.input.UserDTO;
import com.easychat.model.dto.output.UserDetailDTO;
import com.easychat.model.entity.User;

/**
 * Created by yonah on 15-10-18
 */
public interface UserService {
    void addUser(UserDTO userDTO);
    User authenticate(UserDTO userDTO);
    void modifyUserInfo(String name,String json);
    UserDetailDTO getUser(String name);
    String getFriends(String name);
    void deleteFriend(String userName,String friendName);
    String getFriendInfo(String name,String friend_name);
    String getStrangerInfo(String name,String stranger_name);
    boolean setFriendRelationship(String aName, String bName);
}
