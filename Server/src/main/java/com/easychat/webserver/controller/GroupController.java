package com.easychat.webserver.controller;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.error.ErrorType;
import com.easychat.service.impl.GroupServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by king on 2015/12/7.
 */
@RestController
@RequestMapping("/v1/groups")
public class GroupController {
    private GroupServiceImpl groupServiceImpl;
    private RedisTemplate redisTemplate;

    static Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    public GroupController(GroupServiceImpl groupServiceImpl,RedisTemplate redisTemplate){
        this.groupServiceImpl = groupServiceImpl;
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createGroup(@RequestBody String json) throws BadRequestException{
        String resultData = groupServiceImpl.createGroup(json);
        return resultData;
    }

    @RequestMapping(value = "/{uid}/{gid}",method = RequestMethod.DELETE,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteGroup(@PathVariable long uid,@PathVariable long gid)
            throws BadRequestException,NotFoundException{
        //判断群是否存在
        if(!groupServiceImpl.exists(gid)){
            throw new NotFoundException(ErrorType.NO_ERROR,"群组不存在");
        }

        String resultData = groupServiceImpl.deleteGroup(gid,uid);
        if (resultData.equals(ErrorType.INVALID_GRANT)){
            throw new BadRequestException(ErrorType.INVALID_GRANT,"invalid_grant");
        }else{
            return resultData;
        }
    }

    @RequestMapping(value = "/{uid}/{gid}",method = RequestMethod.PATCH ,produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateAvatarAndAnnouncement(@PathVariable long uid,@PathVariable long gid, @RequestBody String json)
            throws BadRequestException,NotFoundException{
        //判断群是否存在
        if(!groupServiceImpl.exists(gid)){
            throw new NotFoundException(ErrorType.NO_ERROR,"群组不存在");
        }
        String resultData = groupServiceImpl.updateGroupAvatarAndAnnouncement(gid,uid,json);
        if (resultData.equals(ErrorType.INVALID_GRANT)){
            throw new BadRequestException(ErrorType.INVALID_GRANT,"invalid_grant");
        }else{
            return resultData;
        }
    }

    @RequestMapping(value = "/{gid}/users/{uid}",method = RequestMethod.DELETE , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String quitGroup(@PathVariable long uid,@PathVariable long gid) throws NotFoundException{
        //判断群是否存在
        if(!groupServiceImpl.exists(gid)){
            throw new NotFoundException(ErrorType.NO_ERROR,"群组不存在");
        }
        String resultData = groupServiceImpl.quitGroup(gid,uid);
        return resultData;
    }

    @RequestMapping(value = "/{gid}/users" ,method = RequestMethod.POST , produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public String inviteIntoGroup(@PathVariable long gid,@RequestBody String json) throws NotFoundException{
        //判断群是否存在
        if(!groupServiceImpl.exists(gid)){
            throw new NotFoundException(ErrorType.NO_ERROR,"群组不存在");
        }
        String resultData = groupServiceImpl.inviteIntoGroup(gid,json);
        return resultData;
    }
}
