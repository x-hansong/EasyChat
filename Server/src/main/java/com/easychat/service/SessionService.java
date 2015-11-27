package com.easychat.service;


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

    public Session createSession(String userName){
        return null;
    }

    public boolean isValid(Session session) {
        return false;
    }

    public void refreshSession(Session session) {
    }

    public void destroySession(Session session) {
    }
}
