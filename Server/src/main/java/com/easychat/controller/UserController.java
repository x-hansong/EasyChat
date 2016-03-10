package com.easychat.controller;

import com.easychat.exception.BadRequestException;
import com.easychat.model.dto.input.UserDTO;
import com.easychat.model.dto.output.UserDetailDTO;
import com.easychat.model.entity.User;
import com.easychat.model.error.ErrorType;
import com.easychat.model.msg.FriendInviteMsg;
import com.easychat.service.UserService;
import com.easychat.utils.JsonUtils;
import com.easychat.validator.UserDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Created by yonah on 15-10-18.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private UserService userService;

    private SimpMessagingTemplate simpMessagingTemplate;
    @InitBinder("userDTO")//这里要填需要校验的变量名
    protected void initBinder(WebDataBinder binder){
        binder.setValidator(new UserDTOValidator());
    }

    static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 用户注册接口
     * @param userDTO 从RequestBody中json解析出来的UserDTO
     * @return UserDTO 返回注册成功的用户名密码
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO)  {
        userService.addUser(userDTO);
        return userDTO;
    }

    /**获取当前登陆用户信息接口
     *
     * @param name 用户名
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public UserDetailDTO getUser(@PathVariable String name, HttpSession httpSession)  {
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getUser(name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**
     * 用户登录接口
     */
    @RequestMapping(value="/authorization", method = RequestMethod.POST)
    @ResponseBody
    public void authenticate(@RequestBody @Valid UserDTO userDTO, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        User user = userService.authenticate(userDTO);
        httpSession.setAttribute("id", user.getId());
        httpSession.setAttribute("name",user.getName());
        httpSession.setAttribute("ip", httpServletRequest.getRemoteAddr());
    }

    /**
     * 用户注销接口
     * @param httpSession
     */
    @ResponseBody
    @RequestMapping(value="/authentication",method=RequestMethod.DELETE)
    public void logOff(HttpSession httpSession) {
        httpSession.invalidate();
    }

    /**
     * 修改用户信息接口.
     */
    @ResponseBody
    @RequestMapping(value="/{name}",method = RequestMethod.PUT)
    public UserDetailDTO modifyUserInfo(@PathVariable String name,
                                  @RequestBody UserDetailDTO userDetailDTO, HttpSession httpSession)  {
        if (name.equals(httpSession.getAttribute("name"))){

        }else {
            throw new BadRequestException(ErrorType.INVALID_GRANT, "you can't modify other's information");
        }
        return userDetailDTO;
//        userService.modifyUserInfo(name, json);
//        return json;
    }

    /**
     * 获取用户所有好友
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/friends",method = RequestMethod.GET)
    public String getFriends(@PathVariable String name ,  HttpSession httpSession) {
        String uname = (String)httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getFriends(name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }


    /**
     * 删除好友
     * @param name
     * @param friendname
     * @param httpSession
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/contacts/users/{friendname}",method = RequestMethod.DELETE)
    public void deleteFriend(@PathVariable String name,@PathVariable String friendname ,HttpSession httpSession){
        String uname = (String)httpSession.getAttribute("name");
        if (uname.equals(name)) {
            userService.deleteFriend(name,friendname);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**获取好友信息接口
     *
     * @param name
     * @return
     */
    @ResponseBody
         @RequestMapping(value = "/{name}/friend/{friend_name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
         public String getFriendInfo(@PathVariable String name,@PathVariable String friend_name , HttpSession httpSession) {
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getFriendInfo(name,friend_name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }
    /**获取陌生人信息接口
     *
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/stranger/{stranger_name}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public String getStrangerInfo(@PathVariable String name,@PathVariable String stranger_name , HttpSession httpSession) {
        String uname = (String) httpSession.getAttribute("name");
        if (uname.equals(name)) {
            return userService.getStrangerInfo(name,stranger_name);
        }else {
            throw new BadRequestException(ErrorType.ILLEGAL_ARGUMENT,"invalid argument");
        }
    }

    /**
     *请求添加好友
     */
    @ResponseBody
    @RequestMapping(value = "/{name}/contacts/users/{friendName}", method = RequestMethod.POST,produces = APPLICATION_JSON_VALUE)
    public void sendFriendInvite(@PathVariable String name, @PathVariable String friendName, @RequestBody String json){
        Map data = JsonUtils.decode(json, Map.class);
        FriendInviteMsg friendInviteMsg = new FriendInviteMsg(name, friendName, (String) data.get("content"));
        simpMessagingTemplate.convertAndSend("/queue/system/" + friendName, friendInviteMsg);
    }

}
