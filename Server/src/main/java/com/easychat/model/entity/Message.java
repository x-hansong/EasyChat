package com.easychat.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by king on 2015/12/3.
 */
@Entity
@Table(name = "Message")
public class Message implements Serializable{

    public Message() {
        this.fromId = 0;
        this.toId = 0;
        this.msgId = 0;
        this.content = "";
        this.type = 0;
        this.created = null;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "fromId")
    private long fromId;

    @Column(name = "toId")
    private long toId;

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

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
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
