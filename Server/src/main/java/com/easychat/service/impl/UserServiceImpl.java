package com.easychat.service.impl;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.UserRepository;
import com.easychat.service.UserService;
import com.easychat.utils.CommonUtils;
import com.easychat.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RedisTemplate redisTemplate;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 处理UserController提交过来的数据
     * @param json
     */
    @Override
    public void addUser(String json) throws BadRequestException {
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String nameTest = (String) data.get("name");
        String passwordTest = (String) data.get("password");

        if (CommonUtils.checkName(nameTest)
                && CommonUtils.checkPassword(passwordTest)) {
            User user = userRepository.findByName(nameTest);
            if (user == null) {
                User userTest = new User();
                userTest.setName(nameTest);
                userTest.setPassword(CommonUtils.md5(passwordTest));
                userRepository.save(userTest);
            } else {
                throw new BadRequestException(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS,
                        "Entity user requires that property named username be unique");
            }
        } else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
        }
    }



    /**
     * 用户登录
     *
     * @return 用户名和密码正确，创建session并返回.
     */
    @Override
    public User authenticate(String json) throws BadRequestException {
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String name = (String) data.get("name");
        String password = CommonUtils.md5((String) data.get("password"));
        User user = isUserValid(name, password);
        if (user != null) {
            return user;
        } else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
        }

    }

    /**
     * 验证账号密码是否正确
     *
     * @param name
     * @param password
     * @return User
     */
    private User isUserValid(String name, String password) {
        User user = userRepository.findByName(name);
        if (password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }


    /**
     * 修改用户信息接口
     *
     * @param name
     * @param json
     * @throws BadRequestException
     */
    @Override
    public void modifyUserInfo(String name, String json) throws BadRequestException ,NotFoundException{
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        int sex = Integer.parseInt((String) data.get("sex"));
        String nick = (String) data.get("nick");
        String phone = (String) data.get("phone");
        String email = (String) data.get("phone");
        String avatar = (String) data.get("avatar");
        String signInfo = (String) data.get("sign_info");

        if (CommonUtils.checkNick(nick)) {
            if (CommonUtils.checkEmail(email)) {
                if (CommonUtils.checkSex(sex)) {
                    if (CommonUtils.checkPhone(phone)) {
                        if (CommonUtils.checkSignInfo(signInfo)) {

                            User user = userRepository.findByName(name);
                            if (user == null) throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
                            user.setSex(sex);
                            user.setNick(nick);
                            user.setPhone(phone);
                            user.setEmail(email);
                            user.setAvatar(avatar);
                            user.setSignInfo(signInfo);
                            userRepository.save(user);
                        } else throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong signInfo input");
                    } else throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong phone input");
                } else throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong sex input");
            } else throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong email input");
        } else throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong nick input");
    }

    /**
     * 获取用户信息
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    public String getUser(String name) throws NotFoundException {
        User user = userRepository.findByName(name);
        if (user != null) {

            Map<String, Object> data = new HashMap<String, Object>();

            data.put("id", user.getId());
            data.put("name", name);
            data.put("nick", user.getNick());
            data.put("sex", user.getSex());
            data.put("phone", user.getPhone());
            data.put("email", user.getEmail());
            data.put("avatar", user.getAvatar());
            data.put("sign_info", user.getSignInfo());

            String json = JsonUtils.encode(data);
            return json;
        } else throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");

    }

}
