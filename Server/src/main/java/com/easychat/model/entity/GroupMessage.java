package com.easychat.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by king on 2015/12/3.
 */

@Entity
@Table(name = "GroupMessage")
public class GroupMessage implements Serializable{

    public GroupMessage() {
        this.groupId = 0;
        this.userId = 0;
        this.msgId = 0;
        this.content = "";
        this.type = 0;
        this.created = null;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "groupId")
    private long groupId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "msgId")
    private long msgId;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private int type;

    @Column(name = "created")
    private Timestamp created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
