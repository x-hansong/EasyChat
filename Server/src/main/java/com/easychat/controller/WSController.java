package com.easychat.controller;

import com.easychat.model.msg.AcceptInvitationMsg;
import com.easychat.model.msg.CommandMsg;
import com.easychat.model.msg.Message;
import com.easychat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by xhans on 2015/12/19.
 */
@Controller
@RequestMapping("/")
public class WSController {

    private UserService userService;
    private SimpMessagingTemplate simpMessagingTemplate;

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    public WSController(UserService userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    public void chat(Message message){
        logger.debug(message.getContent());
        simpMessagingTemplate.convertAndSend("/queue/channel/" + message.getToUser(),message);
    }

    @MessageMapping("/accept")
    public void accept(AcceptInvitationMsg msg){
        userService.setFriendRelationship(msg.getToUser(), msg.getFromUser());
        simpMessagingTemplate.convertAndSend("/queue/system/" + msg.getFromUser(), new CommandMsg("REFRESH_FRIENDLIST"));
        simpMessagingTemplate.convertAndSend("/queue/system/" + msg.getToUser(), new CommandMsg("REFRESH_FRIENDLIST"));
    }
}
