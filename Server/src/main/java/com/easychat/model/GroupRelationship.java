package com.easychat.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by yonah on 15-11-23.
 */
@Entity
@Table(name="GroupRelationship")
public class GroupRelationship implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "gid")
    private Long gid;

    public GroupRelationship(Long gid, Long uid) {
        this.uid = uid;
        this.gid = gid;
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
