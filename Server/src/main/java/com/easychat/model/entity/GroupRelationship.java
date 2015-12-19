package com.easychat.model.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yonah on 15-11-23.
 */
@Entity
@Table(name="GroupRelationship")
@IdClass(GroupRelationship.class)
public class GroupRelationship implements Serializable{


    @Id
    @Column(name = "uid")
    private Long uid;

    @Id
    @Column(name = "gid")
    private Long gid;

    public GroupRelationship(Long gid, Long uid) {
        this.uid = uid;
        this.gid = gid;
    }

    public GroupRelationship() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }
}
