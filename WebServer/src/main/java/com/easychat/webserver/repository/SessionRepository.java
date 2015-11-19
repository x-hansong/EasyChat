package com.easychat.webserver.repository;

import com.easychat.webserver.session.Session;

/**
 * Created by yonah on 15-11-19.
 */
public interface SessionRepository {
    boolean add(Session session);
    boolean remove(Session session);
    boolean refresh(Session session);
    boolean has(Session session);
}
