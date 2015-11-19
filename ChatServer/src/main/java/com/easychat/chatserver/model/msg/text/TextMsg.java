package com.easychat.chatserver.model.msg.text;

/**
 * Created by yonah on 15-11-9.
 */
public abstract class TextMsg {
    private String msgID;
    private int toID;
    private int fromID;
    private String content;
    private Long time;

    public abstract String toJson();

    public TextMsg(String msgID, int toID, int fromID, String content) {
        this.msgID = msgID;
        this.toID = toID;
        this.fromID = fromID;
        this.content = content;
        this.time = System.currentTimeMillis();
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public int getToID() {
        return toID;
    }

    public void setToID(int toID) {
        this.toID = toID;
    }

    public int getFromID() {
        return fromID;
    }

    public void setFromID(int fromID) {
        this.fromID = fromID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
