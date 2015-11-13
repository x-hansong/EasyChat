package com.easychat.controller;

import com.easychat.controller.exception.NotFoundException;
import com.easychat.model.ErrorInfo;
import com.easychat.service.UserService;
import com.easychat.model.User;
import com.easychat.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;
    private ObjectMapper mapper;
    private JedisPool pool;

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, ObjectMapper mapper, JedisPool pool) {
        this.userService = userService;
        this.mapper = mapper;
        this.pool = pool;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody String json) throws IOException {
        Map<String, Object> data = mapper.readValue(json, Map.class);

        return userService.addUser(data);
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
