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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody String json) throws BadRequestException {
        userService.addUser(json);
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String name) throws NotFoundException {

        redisTemplate.opsForValue().increment("visit",1);

        User user = userService.getUserByName(name);
        if (user == null){
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "Service resource not found");
        }
        return user;
    }

    /**
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

}
