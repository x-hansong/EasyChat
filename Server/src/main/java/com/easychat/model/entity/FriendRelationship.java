package com.easychat.model.entity;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yonah on 15-11-23.
 */
@Entity
@Table(name="FriendRelationship")
public class FriendRelationship implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "aid")
    private Long aid;

    @Column(name = "bid")
    private Long bid;

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public FriendRelationship(Long aid, Long bid) {
        this.aid = aid;
        this.bid = bid;
    }

    public FriendRelationship() {
    }
}
