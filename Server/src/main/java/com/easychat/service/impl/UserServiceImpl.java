package com.easychat.service.impl;

import com.easychat.exception.BadRequestException;
import com.easychat.exception.NotFoundException;
import com.easychat.model.dto.input.UserDTO;
import com.easychat.model.dto.output.UserDetailDTO;
import com.easychat.model.entity.FriendRelationship;
import com.easychat.model.entity.User;
import com.easychat.model.entity.UserTemp;
import com.easychat.model.error.ErrorType;
import com.easychat.repository.FriendRelationshipRepository;
import com.easychat.repository.UserRepository;
import com.easychat.service.UserService;
import com.easychat.utils.CommonUtils;
import com.easychat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yonah on 15-10-18.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private FriendRelationshipRepository friendRelationshipRepository;
    private RedisTemplate redisTemplate;

    static Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    public UserServiceImpl(UserRepository userRepository, FriendRelationshipRepository friendRelationshipRepository) {
        this.userRepository = userRepository;
        this.friendRelationshipRepository = friendRelationshipRepository;
    }

    /**
     * 注册处理
     *
     */
    @Override
    public void addUser(UserDTO userDTO) {
        String nameTest = userDTO.getName();
        String passwordTest = userDTO.getPassword();

        //检测是否重复用户名
        User user = userRepository.findByName(nameTest);
        if (user == null) {
            User userTest = new User();
            userTest.setName(nameTest);
            userTest.setPassword(CommonUtils.md5(passwordTest));
            String avatar = "http://img4q.duitang.com/uploads/item/201503/05/20150305192855_iAFTs.thumb.224_0.jpeg";
            userTest.setAvatar(avatar);
            userTest.setNick(nameTest);
            userRepository.save(userTest);
        } else {
            throw new BadRequestException(ErrorType.DUPLICATE_UNIQUE_PROPERTY_EXISTS,
                    "Entity user requires that property named username be unique");
        }
    }



    /**
     * 用户验证
     */
    @Override
    public User authenticate(UserDTO userDTO) {
        User user = userRepository.findByName(userDTO.getName());
        String passwd = CommonUtils.md5(userDTO.getPassword());
        if (user != null) {
            if (passwd.equals(user.getPassword())) {
                return user;
            } else {
                throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT, "invalid username or password");
            }
        } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }
    }


    /**
     * 修改用户信息接口.
     *
     * @param name
     * @param json
     * @throws BadRequestException
     */
    @Override
    public void modifyUserInfo(String name, String json) {
        Map<String, Object> data = JsonUtils.decode(json, Map.class);
        String sex = (String) data.get("sex");
        String nick = (String) data.get("nick");
        String phone = (String) data.get("phone");
        String email = (String) data.get("email");
        String avatar = (String) data.get("avatar");
        String signInfo = (String) data.get("sign_info");

        if (CommonUtils.checkNick(nick)) {
            if (CommonUtils.checkEmail(email)) {
                if (CommonUtils.checkSex(sex)) {
                    if (CommonUtils.checkPhone(phone)) {
                        if (CommonUtils.checkSignInfo(signInfo)) {

                            User user = userRepository.findByName(name);
                            if (user == null)
                                throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
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
    public UserDetailDTO getUser(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            return convertToUserDetailDTO(user);
       } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }

    }

    private UserDetailDTO convertToUserDetailDTO(User user){
        UserDetailDTO userDetailDTO = new UserDetailDTO();
        userDetailDTO.setName(user.getName());
        userDetailDTO.setId(user.getId());
        userDetailDTO.setAvatar(user.getAvatar());
        userDetailDTO.setEmail(user.getEmail());
        userDetailDTO.setNick(user.getNick());
        userDetailDTO.setPhone(user.getPhone());
        userDetailDTO.setSex(user.getSex());
        userDetailDTO.setSignInfo(user.getSignInfo());

        return userDetailDTO;
    }

    /**
     * 获取所有好友
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    @Override
    public String getFriends(String name) {
        User user = userRepository.findByName(name);
        //判断用户是否存在
        if (user != null) {
            long uid = user.getId();
            logger.debug(uid + "");
            List<FriendRelationship> friendRelationshipList = friendRelationshipRepository.findByAid(uid);
            //判断好友人数是否等于零
            if (friendRelationshipList.size() > 0 && friendRelationshipList != null) {
                UserTemp[] friends = new UserTemp[friendRelationshipList.size()];
                for (int i = 0; i < friendRelationshipList.size(); i++) {
                    long fid = friendRelationshipList.get(i).getBid();
                    User friend = userRepository.findOne(fid);
                    friends[i] = new UserTemp(friend);
                }
                Map<String, UserTemp[]> map = new HashMap<>();
                map.put("friends", friends);
                String json = JsonUtils.encode(map);
                return json;
            } else {
                return null;
            }
        } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }
    }


    /**
     * 删除好友
     *
     * @param userName
     * @param friendName
     * @throws NotFoundException
     */
    @Override
    public void deleteFriend(String userName, String friendName) {
        User user = userRepository.findByName(userName);
        //判断用户是否存在
        if (user != null) {
            User friend = userRepository.findByName(friendName);
            //判断好友是否存在
            if (friend != null) {
                long uid = user.getId();
                long fid = friend.getId();
                FriendRelationship uTofFriendRelationship = friendRelationshipRepository.findByAidAndBid(uid, fid);
                FriendRelationship fTouFriendRelationship = friendRelationshipRepository.findByAidAndBid(fid, uid);
                //判断双方的好友关系是否存在
                if ((uTofFriendRelationship != null) && (fTouFriendRelationship != null)) {
                    friendRelationshipRepository.delete(uTofFriendRelationship);
                    friendRelationshipRepository.delete(fTouFriendRelationship);
                } else {
                    throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "they are not friends");
                }
            } else {
                throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the friend is not exists");
            }
        } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }
    }

    /**
     * 获取好友信息
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    public String getFriendInfo(String name, String friend_name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            User friend = userRepository.findByName(friend_name);
            //判断好友是否存在
            if (friend != null) {
                long uid = user.getId();
                long fid = friend.getId();
                FriendRelationship uTofFriendRelationship = friendRelationshipRepository.findByAidAndBid(uid, fid);
                FriendRelationship fTouFriendRelationship = friendRelationshipRepository.findByAidAndBid(fid, uid);
                //判断双方的好友关系是否存在
                if ((uTofFriendRelationship != null) && (fTouFriendRelationship != null)) {
                    UserTemp friendTemp = new UserTemp(friend);
                    String json = JsonUtils.encode(friendTemp);
                    return json;
                } else
                    throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "they are not friends");
            } else
                throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the friend is not exists");
        } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }

    }

    /**
     * 获取陌生人信息
     *
     * @param name
     * @return
     * @throws NotFoundException
     */
    public String getStrangerInfo(String name, String stranger_name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            User person = userRepository.findByName(stranger_name);
            //判断好友是否存在
            if (person != null) {
                long uid = user.getId();
                long fid = person.getId();
                FriendRelationship uTofFriendRelationship = friendRelationshipRepository.findByAidAndBid(uid, fid);
                FriendRelationship fTouFriendRelationship = friendRelationshipRepository.findByAidAndBid(fid, uid);
                //判断双方的好友关系是否存在
                //如果两个人是好友,返回基于好友身份的信息
                if ((uTofFriendRelationship != null) && (fTouFriendRelationship != null)) {
                    UserTemp friendTemp = new UserTemp(person);
                    String json = JsonUtils.encode(friendTemp);
                    return json;
                }
                //如果两个人不是好友，返回基于陌生人身份的信息
                else {
                    Map<String, Object> data = new HashMap<String, Object>();

                    data.put("name", stranger_name);
                    data.put("nick", person.getNick());
                    data.put("sex", person.getSex());
                    data.put("avatar", person.getAvatar());
                    data.put("sign_info", person.getSignInfo());

                    String json = JsonUtils.encode(data);
                    return json;
                }
            } else
                throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the stranger is not exists");
        } else {
            throw new NotFoundException(ErrorType.SERVICE_RESOURCE_NOT_FOUND, "the user is not exists");
        }
    }

    /**
     * 添加好友关系
     */
    @Override
    public boolean setFriendRelationship(String aName, String bName) {
        User aUser = userRepository.findByName(aName);
        User bUser = userRepository.findByName(bName);
        FriendRelationship relationship = new FriendRelationship(aUser.getId(), bUser.getId());
        friendRelationshipRepository.save(relationship);
        relationship.setAid(bUser.getId());
        relationship.setBid(aUser.getId());
        friendRelationshipRepository.save(relationship);
        return true;
    }

}
