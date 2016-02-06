package com.easychat.service;

/**
 * Created by king on 2015/12/7.
 */
public interface GroupService {
    boolean exists(long gid);
    String createGroup(String json);
    String deleteGroup(long gid,long uid);
    String updateGroupAvatarAndAnnouncement(long gid ,long uid ,String json);
    String quitGroup(long gid, long uid );
    String inviteIntoGroup(long gid ,String json);
}
