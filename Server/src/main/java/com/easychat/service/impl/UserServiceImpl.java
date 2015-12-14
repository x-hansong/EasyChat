package com.easychat.service.impl;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private SessionService sessionService;
    private RedisTemplate redisTemplate;


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
                throw new BadRequestException(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS,
                        "Entity user requires that property named username be unique, value of dddd exists");
            }
        }
        else {
            throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
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
     * @return 用户名和密码正确，创建session并返回.
     */
    @Override
    public Session authenticate(String json) throws BadRequestException{
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String name=(String) data.get("name");
        String password=CommonUtils.md5((String) data.get("password"));
        if(isUserValid(name, password)){
            //因为SessionService没有完善所以暂时使用一个这一点的session
            User user=getUserByName(name);
            Token token=new Token();
            Session session=new Session(token,user.getId());
            return session;
            //User user=getUserByName(name);
            //Long uid=user.getId();
            // return sessionService.createSession(uid);
        }
        else{
            throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
        }

    }

    /**
     * 验证账号密码是否正确
     * @param name
     * @param password
     * @return
     */
    @Override
    public boolean isUserValid(String name,String password){
        User user=userRepository.findByName(name);
        if(password.equals(user.getPassword())){
            return true;
        }
        else return false;
    }

    /**
     * 用户注销
     * @param token
     * @return true or false
     * @throws BadRequestException
     */
    @Override
    public boolean logOff(Token token) throws BadRequestException{
        if(sessionService.isValid(token)){
            sessionService.destroySession(token);
            return true;
        }

        else throw new BadRequestException(ErrorType.AUTH_BAD_ACCESS_TOKEN,
                "Unable to authenticate due to expired access token");
    }

    /**
     * 修改用户信息接口
     * @param token
     * @param name
     * @param json
     * @throws BadRequestException
     */
    @Override
    public void modifyUserInfo(Token token,String name,String json) throws BadRequestException {
        //if (sessionService.isValid(token)) {
        if(true){
            Map<String, Object> data = JsonUtils.decode(json, Map.class);
            int sex=Integer.parseInt((String)data.get("sex"));
            String nick=(String)data.get("nick");
            String phone=(String)data.get("phone");
            String email=(String)data.get("phone");
            String avatar=(String)data.get("avatar");
            String signInfo=(String)data.get("sign_info");

            if(CommonUtils.checkNick(nick)) {
                if (CommonUtils.checkEmail(email)) {
                    if (CommonUtils.checkSex(sex)) {
                        if (CommonUtils.checkPhone(phone)) {
                            if(CommonUtils.checkSignInfo(signInfo)) {

                                User user = getUserByName(name);
                                user.setSex(sex);
                                user.setNick(nick);
                                user.setPhone(phone);
                                user.setEmail(email);
                                user.setAvatar(avatar);
                                user.setSignInfo(signInfo);
                                userRepository.save(user);
                            }
                            else throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong signInfo input");
                        }
                        else throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong phone input");
                    }
                    else throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong sex input");
                }
                else throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong email input");
            }
            else throw new  BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "Wrong nick input");
        }
        else throw new BadRequestException(ErrorType.AUTH_BAD_ACCESS_TOKEN,
                "Unable to authenticate due to expired access token");
    }

    /**
     * 获取用户信息
     * @param name
     * @return
     * @throws NotFoundException
     */
    public String getUser(String name) throws NotFoundException{
//        redisTemplate.opsForValue().increment("visit",1);

        User user = getUserByName(name);
        if(user!=null) {
            Long id = user.getId();
            String nick = user.getNick();
            int sex = user.getSex();
            String phone = user.getPhone();
            String email = user.getEmail();
            String avatar = user.getAvatar();
            String signInfo = user.getSignInfo();

            Map<String, Object> data = new HashMap<String, Object > ();

            data.put("id", id);
            data.put("name", name);
            data.put("nick", nick);
            data.put("sex", sex);
            data.put("phone", phone);
            data.put("email", email);
            data.put("avatar", avatar);
            data.put("sign_info", signInfo);

            String json=JsonUtils.encode(data);
            return json;
        }
        else throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND,"the user is not exists");


    }

}
