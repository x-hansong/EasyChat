package com.easychat.service.impl;

import com.easychat.exception.BadRequestException;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.model.session.Session;
import com.easychat.model.session.Token;
import com.easychat.repository.UserRepository;
import com.easychat.service.SessionService;
import com.easychat.service.UserService;
import com.easychat.utils.CommonUtils;
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
    private SessionService sessionService;


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
    public void addUser(String json) throws BadRequestException {
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
            } else {
                throw new BadRequestException(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS, "duplicate_unique_property_exists");
            }
        }
        else {
            throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "illegal_argument");
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

    /**
     * 用户登录
     * @return 用户名或者密码不正确，返回ILLEGAL_ARGUMENT
     * @return 用户名和密码正确，创建session并返回
     */
    @Override
    public Session authenticate(String json) throws BadRequestException{
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String name=(String) data.get("name");
        String password=CommonUtils.md5((String) data.get("password"));
        if(isValid(name, password)){
            //因为SessionService没有完善所以暂时使用一个这一点的session
            User user=getUserByName(name);
            Token token=new Token();
            Session session=new Session(token,user.getId());
            return session;
            //return sessionService.createSession(name);
        }
        else{
            throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "illegal_argument");
        }

    }

    @Override
    public boolean isValid(String name,String password){
        User user=userRepository.findByName(name);
        if(password.equals(user.getPassword())){
            return true;
        }
        else return false;
    }

}
