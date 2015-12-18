package com.easychat.controller;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;
    private RedisTemplate redisTemplate;

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService,  RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
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
     * 修改用户信息接口
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

   /* @ResponseBody
    @RequestMapping(value="/{name}/contacts/users/{friend_username}",method = RequestMethod.POST)
    public boolean addFriend(@RequestHeader ("Authorization") Token token,
                             @PathVariable String name,
                             @PathVariable String friend_username)throws
                             */


    /**
     * 获取用户所有好友
     * @param name
     * @return
     * @throws NotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/contacts/users",method = RequestMethod.GET)
    public String getFriends(@PathVariable String name) throws NotFoundException{
        String json = userService.getFriends(name);
        return json;
    }
}
