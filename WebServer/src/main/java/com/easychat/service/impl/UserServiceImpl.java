package com.easychat.service.impl;

import com.easychat.service.UserService;
import com.easychat.model.User;
import com.easychat.repository.UserRepository;
import com.easychat.utils.CommonUtils;
import com.easychat.model.ErrorType;
import com.easychat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 处理UserController提交过来的数据
     * @return 用户名或者密码不合法，返回ILLEGAL_ARGUMENT
     * @return 用户名已存在，返回DUPLICATE_UNIQUE_PROPERTY_EXISTS
     * @return 上述条件都不满足，则新建用户到数据库，密码用md5加密，返回NO_ERROR
     * @param json
     */
    @Override
    public String addUser(String json) {
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String nameTest=(String) data.get("name");
        String passwordTest=(String) data.get("password");

        if(CommonUtils.checkName(nameTest)
                &&CommonUtils.checkPassword(passwordTest)) {
            User user = getUserByName(nameTest);
            if (user == null) {
                User userTest = new User();
                userTest.setName(nameTest);
                userTest.setPassword(CommonUtils.md5(passwordTest));
                userRepository.save(userTest);

                String resultData =  ErrorType.NO_ERROR;
                return resultData;
            } else {
                String resultData = ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS;
                return resultData;
            }
        }
        else {
            String resultData=ErrorType.ILLEGAL_ARGUMENT;
            return resultData;
        }
    }

    @Override
    public boolean hasUser(Long id) {
        User user = userRepository.findOne(id);
        return user != null ? true:false;
    }

    @Override
    public User getUserByName(String name) {
        User user = userRepository.findByName(name);
        return user != null ? user:null;
    }

}
