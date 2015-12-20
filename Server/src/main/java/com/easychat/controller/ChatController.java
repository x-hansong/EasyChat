package com.easychat.controller;

import com.easychat.model.msg.Message;
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
public class ChatController {

    private SimpMessagingTemplate simpMessagingTemplate;

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);
    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    public Message chat(Message message){
        logger.debug(message.getContent());
        simpMessagingTemplate.convertAndSend("/queue/channel/" + message.getToUser(),message);
        return message;
    }
}
