package com.easychat.session;

/**
 * Created by yonah on 15-11-19.
 */
public interface SessionPool {
    boolean add(Session session);
    boolean remove(Session session);
    boolean refresh(Session session);
    boolean has(Session session);
}
