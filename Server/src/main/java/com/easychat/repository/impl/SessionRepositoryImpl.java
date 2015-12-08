package com.easychat.repository.impl;

import com.easychat.model.session.Session;
import com.easychat.model.session.Token;
import com.easychat.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by yonah on 15-12-7.
 */
public class SessionRepositoryImpl implements SessionRepository{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void add(Session session) {


    }

    @Override
    public void remove(Token token) {

    }

    @Override
    public void refresh(Token token) {

    }

    @Override
    public Session get(Token token) {
        return null;
    }
}
