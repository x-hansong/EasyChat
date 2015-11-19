package com.easychat.controller;

import com.easychat.controller.exception.BadRequestException;
import com.easychat.controller.exception.NotFoundException;
import com.easychat.model.ErrorType;
import com.easychat.service.UserService;
import com.easychat.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;
    private JedisPool pool;

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, JedisPool pool) {
        this.userService = userService;
        this.pool = pool;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestBody String json) throws BadRequestException {
        String resultData =null;
        resultData=userService.addUser(json);
        if (resultData.equals(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS)) {
            throw new BadRequestException(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS, "duplicate_unique_property_exists");
        } else if (resultData.equals(ErrorType.ILLEGAL_ARGUMENT)) {
            throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "illegal_argument");
        } else return json;
    }

    @ResponseBody
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String name) throws NotFoundException {
        try (Jedis jedis = pool.getResource()){
            jedis.incr("visit");
        }
        User user = userService.getUserByName(name);
        if (user == null){
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "Service resource not found");
        }
        return user;
    }

}
