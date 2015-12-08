package com.easychat.service;

/**
 * Created by king on 2015/12/7.
 */
public interface GroupService {
    String createGroup(String json);
    String deleteGroup(long gid,long uid);

}
