package com.easychat.controller;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.model.msg.FriendInviteMsg;
import com.easychat.service.UserService;
import com.easychat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;
    private RedisTemplate redisTemplate;

    private SimpMessagingTemplate simpMessagingTemplate;

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, RedisTemplate redisTemplate, SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 用户注册接口
     * @param json
     * @return
     * @throws BadRequestException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody String json) throws BadRequestException {
        userService.addUser(json);
        return json;
    }

    /**获取用户信息接口
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public String getUser(@PathVariable String name, HttpSession httpSession) throws NotFoundException, BadRequestException {
        redisTemplate.opsForValue().increment("visit", 1);
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getUser(name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**
     * 用户登录接口
     * @param json
     * @return token
     * @throws BadRequestException
     */
    @RequestMapping(value="/authorization", method = RequestMethod.POST)
    @ResponseBody
    public void authenticate(@RequestBody String json, HttpSession httpSession) throws BadRequestException, NotFoundException {
        User user = userService.authenticate(json);
        httpSession.setAttribute("id", user.getId());
        httpSession.setAttribute("name",user.getName());
    }

    /**
     * 用户注销接口
     * @param httpSession
     * @throws BadRequestException
     */
    @ResponseBody
    @RequestMapping(value="/authorization",method=RequestMethod.DELETE)
    public void logOff(HttpSession httpSession) throws BadRequestException{
        httpSession.invalidate();
    }

    /**
     * 修改用户信息接口.
     * @param name
     * @param json
     * @return
     * @throws BadRequestException
     */
    @ResponseBody
    @RequestMapping(value="/{name}",method = RequestMethod.PUT)
    public String modifyUserInfo(@PathVariable String name,
                                  @RequestBody String json) throws BadRequestException, NotFoundException {
        userService.modifyUserInfo(name, json);
        return json;
    }

    /**
     * 获取用户所有好友
     * @param name
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/friends",method = RequestMethod.GET)
    public String getFriends(@PathVariable String name ,  HttpSession httpSession) throws NotFoundException,BadRequestException{
        String uname = (String)httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getFriends(name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }


    /**
     * 删除好友
     * @param name
     * @param friendname
     * @param httpSession
     * @throws NotFoundException
     * @throws BadRequestException
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/contacts/users/{friendname}",method = RequestMethod.DELETE)
    public void deleteFriend(@PathVariable String name,@PathVariable String friendname ,HttpSession httpSession)throws NotFoundException,BadRequestException{
        String uname = (String)httpSession.getAttribute("name");
        if (uname.equals(name)) {
            userService.deleteFriend(name,friendname);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**获取好友信息接口
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
         @RequestMapping(value = "/{name}/friend/{friend_name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
         public String getFriendInfo(@PathVariable String name,@PathVariable String friend_name , HttpSession httpSession) throws NotFoundException, BadRequestException {
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getFriendInfo(name,friend_name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }
    /**获取陌生人信息接口
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/stranger/{stranger_name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public String getStrangerInfo(@PathVariable String name,@PathVariable String stranger_name , HttpSession httpSession) throws NotFoundException, BadRequestException {
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getStrangerInfo(name,stranger_name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**
     *请求添加好友
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/contract/users/{friendName}", method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
    public void sendFriendInvite(@PathVariable String name, @PathVariable String friendName, @RequestBody String json) throws BadRequestException {
        Map data = JsonUtils.decode(json, Map.class);
        FriendInviteMsg friendInviteMsg = new FriendInviteMsg(name, friendName, (String) data.get("content"));
        simpMessagingTemplate.convertAndSend("/queue/system/" + friendName, friendInviteMsg);
    }

}
