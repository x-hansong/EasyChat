package com.easychat.webserver.session;


import com.easychat.webserver.repository.SessionRepository;

/**
 * Created by yonah on 15-11-19.
 */
public class SessionManager {

    private SessionRepository sessionRepository;

    public SessionManager(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session createSession(String userName){
        return null;
    }

    public boolean isValid(Session session) {
        return false;
    }

    public boolean refreshSession(Session session) {
        return false;
    }

    public boolean destroySession(Session session) {
        return false;
    }
}
