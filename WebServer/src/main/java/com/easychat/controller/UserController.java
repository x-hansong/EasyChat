package com.easychat.controller;

import com.easychat.Service.UserService;
import com.easychat.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/User")
public class UserController {
    private UserService userService;
    private ObjectMapper mapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public User addUser(@RequestBody String json) throws IOException {
        Map<String, Object> data = mapper.readValue(json, Map.class);

        return userService.addUser(data);
    }

    @ResponseBody
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

}
