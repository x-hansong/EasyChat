package com.easychat.service.impl;

import com.easychat.model.entity.Group;
import com.easychat.model.entity.GroupRelationship;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.GroupRelationshipRepository;
import com.easychat.repository.GroupRepository;
import com.easychat.service.GroupService;
import com.easychat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by king on 2015/12/7.
 */
@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private GroupRelationshipRepository groupRelationshipRepository;



    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,GroupRelationshipRepository groupRelationshipRepository){
        this.groupRepository = groupRepository;
        this.groupRelationshipRepository = groupRelationshipRepository;
    }

    @Override
    public String createGroup(String json) {
        if (json.equals("")){
            return null;
        }
        Map<String,Object> data = JsonUtils.decode(json,Map.class);
        String name = (String)data.get("name");
        String avatar = (String)data.get("avatar");
        int creator = (Integer)data.get("creator");
        int userCnt = (Integer)data.get("userCnt");
        String annoucement = (String)data.get("annoucement");
        //Ⱥ��Ա��id����
        long [] members = (long[])data.get("members");

        Group group = new Group();
        group.setName(name);
        group.setAvatar(avatar);
        group.setCreator(creator);
        group.setUserCnt(userCnt);
        group.setAnnouncement(annoucement);

        Group groupTemp = groupRepository.save(group);
        long gid = groupTemp.getId();

        for (long uid : members){
            GroupRelationship groupRelationship = new GroupRelationship(gid,uid);
            groupRelationshipRepository.save(groupRelationship);
        }

        return JsonUtils.encode(groupTemp);
    }

    @Override
    public String deleteGroup(long gid, long uid) {
        Group group = groupRepository.findOne(gid);
        //�ж�uid�Ƿ�ΪȺ�Ĵ����ߣ�����û��Ȩ��
        if(uid != group.getCreator()){
            String resultData = ErrorType.INVALID_GRANT;
            return resultData;
        }
        //ɾ��group���е����
        groupRepository.delete(gid);

        //ɾ��groupRelationship���е����
        List<GroupRelationship> groupRelationshipList = groupRelationshipRepository.findByGid(gid);
        for (GroupRelationship groupRelationship : groupRelationshipList) {
            groupRelationshipRepository.delete(groupRelationship);
        }
        String resultData = "{" +
                "��gid��:\""+gid+"\"," +
                "\"uid\":\""+uid+"\"," +
                "}";
        return resultData;
    }
}
