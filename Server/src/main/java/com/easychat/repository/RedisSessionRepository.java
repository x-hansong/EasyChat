package com.easychat.repository;

import com.easychat.session.Session;

/**
 * Created by yonah on 15-11-19.
 */
public class RedisSessionRepository implements SessionRepository {
    @Override
    public boolean add(Session session) {
        return false;
    }

    @Override
    public boolean remove(Session session) {
        return false;
    }

    @Override
    public boolean refresh(Session session) {
        return false;
    }

    @Override
    public boolean has(Session session) {
        return false;
    }
}
