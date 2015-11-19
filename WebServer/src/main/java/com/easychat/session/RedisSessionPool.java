package com.easychat.session;

/**
 * Created by yonah on 15-11-19.
 */
public class RedisSessionPool implements SessionPool{
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
