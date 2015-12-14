package com.easychat.service.impl;

import com.easychat.model.entity.Group;
import com.easychat.model.entity.GroupRelationship;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.GroupRelationshipRepository;
import com.easychat.repository.GroupRepository;
import com.easychat.service.GroupService;
import com.easychat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2015/12/7.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private GroupRelationshipRepository groupRelationshipRepository;


    static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,GroupRelationshipRepository groupRelationshipRepository){
        this.groupRepository = groupRepository;
        this.groupRelationshipRepository = groupRelationshipRepository;
    }


    @Override
    public boolean exists(long gid) {
        return groupRepository.exists(gid);
    }

    @Override
    public String createGroup(String json) {
        if (json.equals("")){
            return null;
        }
        Map<String,Object> data = JsonUtils.decode(json,Map.class);
        String name = (String)data.get("name");
        String avatar = (String)data.get("avatar");
        long creator = (Long)data.get("creator");
        long userCnt = (Long)data.get("userCnt");
        String announcement = (String)data.get("announcement");
        //uid数组
        logger.debug(data.get("members").toString());
//        ArrayList<Integer> members = JsonUtils.decode(data.get("members").toString(), ArrayList.class);


        Group group = new Group();
        group.setName(name);
        group.setAvatar(avatar);
        group.setCreator(creator);
        group.setUserCnt(userCnt);
        group.setAnnouncement(announcement);

        Group groupTemp = groupRepository.save(group);
        long gid = groupTemp.getId();

//        for (int uid : members){
//            GroupRelationship groupRelationship = new GroupRelationship(gid,(long)uid);
//            groupRelationshipRepository.save(groupRelationship);
//        }

        //添加群主与群的关系
        GroupRelationship groupRelationship = new GroupRelationship(gid,creator);
        groupRelationshipRepository.save(groupRelationship);
        return JsonUtils.encode(groupTemp);
    }

    @Override
    public String deleteGroup(long gid, long uid) {
        Group group = groupRepository.findOne(gid);
        //判断uid是否为创建者
        if(uid != group.getCreator()){
            String resultData = ErrorType.INVALID_GRANT;
            return resultData;
        }

        groupRepository.delete(gid);

        List<GroupRelationship> groupRelationshipList = groupRelationshipRepository.findByGid(gid);
        for (GroupRelationship groupRelationship : groupRelationshipList) {
            groupRelationshipRepository.delete(groupRelationship);
        }

        Map<String,Long> stringLongMap = new HashMap<>();
        stringLongMap.put("group_id",gid);
        stringLongMap.put("user_id",uid);
        String resultData = JsonUtils.encode(stringLongMap);

        return resultData;
    }

    @Override
    public String updateGroupAvatarAndAnnouncement(long gid, long uid ,String json) {

        if (json.equals("")){
            return null;
        }
        Map<String,Object> data = JsonUtils.decode(json,Map.class);

        String newAvatar = (String)data.get("avatar");
        String newAnnouncement = (String)data.get("announcement");

        Group group = groupRepository.findOne(gid);
        //判断uid是否为创建者
        if(uid != group.getCreator()){
            String resultData = ErrorType.INVALID_GRANT;
            return resultData;
        }

        group.setAnnouncement(newAnnouncement);
        group.setAvatar(newAvatar);
        groupRepository.save(group);

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("avatar",newAvatar);
        stringMap.put("announcement",newAnnouncement);
        String resultData = JsonUtils.encode(stringMap);

        return  resultData;

    }


    @Override
    public String quitGroup(long gid, long uid) {

        GroupRelationship groupRelationship = new GroupRelationship(gid,uid);
        groupRelationshipRepository.delete(groupRelationship);

        Group group = groupRepository.findOne(gid);
        long userCnt = group.getUserCnt() - 1;

        group.setUserCnt(userCnt);
        groupRepository.save(group);

        Map<String,Long> stringLongMap = new HashMap<>();
        stringLongMap.put("group_id",gid);
        stringLongMap.put("user_id",uid);
        String resultData = JsonUtils.encode(stringLongMap);

        return resultData;
    }


    @Override
    public String inviteIntoGroup(long gid, String json) {
//        if (json.equals("")){
//            return null;
//        }
//        Group group = groupRepository.findOne(gid);
//        long oldUserCnt = group.getUserCnt();
//
//        ArrayList<Integer> uids = JsonUtils.decode(json, ArrayList.class);
//
//        //成功添加的uid
//        List<Long> validUids = new ArrayList<>();
//
//
//        for (int uid : uids){
//            if(groupRelationshipRepository.findByGidAndUid(gid,(long)uid) == null){
//                GroupRelationship groupRelationship = new GroupRelationship(gid,(long)uid);
//                groupRelationshipRepository.save(groupRelationship);
//                oldUserCnt++;
//                validUids.add((long)uid);
//            }
//
//        }
//
//        group.setUserCnt(oldUserCnt);
//        groupRepository.save(group);
//
//        Map<String,Long[]> map = new HashMap<>();
//        map.put("user_ids",(Long[])validUids.toArray());
//        String resultData = JsonUtils.encode(map);
//        return resultData;
        return null;
    }
}
