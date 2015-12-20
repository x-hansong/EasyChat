package com.easychat.model.msg;

/**
 * Created by xhans on 2015/12/20.
 */
public class AcceptInvitationMsg {
    private String fromUser;
    private String toUser;
    private String content;
    private String type;

    public AcceptInvitationMsg() {
    }

    public AcceptInvitationMsg(String fromUser, String toUser, String content) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
        this.type = "ACCEPT_INVITATION";
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
