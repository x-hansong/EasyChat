package com.easychat.repository;

import com.easychat.session.Session;

/**
 * Created by yonah on 15-11-19.
 */
public interface SessionRepository {
    boolean add(Session session);
    boolean remove(Session session);
    boolean refresh(Session session);
    boolean has(Session session);
}
