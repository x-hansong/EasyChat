package com.easychat.service;


import com.easychat.model.session.Token;
import com.easychat.repository.SessionRepository;
import com.easychat.model.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yonah on 15-11-19.
 */
public class SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(Long uid){
        return null;
    }

    public boolean isValid(Token token) {
        return false;
    }

    public void refreshSession(Token token) {
    }

    public void destroySession(Token token) {
    }
    public boolean isOnline(Long uid){
        return false;
    }
}
