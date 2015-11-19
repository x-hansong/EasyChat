package com.easychat.chatserver.model.msg.text;

/**
 * Created by yonah on 15-11-9.
 */
public class SystemTextMsg extends TextMsg {
    public SystemTextMsg(String msgID, int toID, int fromID, String content) {
        super(msgID, toID, fromID, content);
    }

    @Override
    public String toJson() {
        return null;
    }
}
