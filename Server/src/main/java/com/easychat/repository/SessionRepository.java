package com.easychat.repository;

import com.easychat.model.session.Session;
import com.easychat.model.session.Token;

/**
 * Created by yonah on 15-11-19.
 */
public interface SessionRepository {
    void add(Session session);
    void remove(Token token);
    void refresh(Token token);
    Session get(Token token);
}
