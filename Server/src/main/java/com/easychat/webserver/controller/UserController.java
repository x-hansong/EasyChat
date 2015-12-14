package com.easychat.webserver.controller;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.error.ErrorType;
import com.easychat.model.entity.User;
import com.easychat.model.session.Session;
import com.easychat.model.session.Token;
import com.easychat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    public String getUser(@PathVariable String name) throws NotFoundException {

        return userService.getUser(name);

    }

    /**
     * 用户登录接口
     * @param json
     * @return token
     * @throws BadRequestException
     */
    @RequestMapping(value="/authorization", method = RequestMethod.POST)
    @ResponseBody
    public Token authenticate(@RequestBody String json) throws BadRequestException {
        Session session=userService.authenticate(json);
        return session.getToken();

    }

    /**
     * 用户注销接口
     * @param token
     * @return
     * @throws BadRequestException
     */
    @ResponseBody
    @RequestMapping(value="/authorization",method=RequestMethod.DELETE)
    public boolean logOff(@RequestHeader("Authorization") Token token) throws BadRequestException{
        if(userService.logOff(token)){
            return true;
        }
        else return false;
    }

    /**
     * 修改用户信息接口
     * @param token
     * @param name
     * @param json
     * @return
     * @throws BadRequestException
     */
    @ResponseBody
    @RequestMapping(value="/{name}",method = RequestMethod.PUT)
    public String modifyUserInfo(@RequestHeader("Authorization") Token token,
                                  @PathVariable String name,
                                  @RequestBody String json)throws BadRequestException {
        userService.modifyUserInfo(token, name, json);
        return json;
    }

   /* @ResponseBody
    @RequestMapping(value="/{name}/contacts/users/{friend_username}",method = RequestMethod.POST)
    public boolean addFriend(@RequestHeader ("Authorization") Token token,
                             @PathVariable String name,
                             @PathVariable String friend_username)throws
                             */
}
