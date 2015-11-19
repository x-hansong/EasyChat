package com.easychat.session;


/**
 * Created by yonah on 15-11-19.
 */
public class SessionManager {

    private SessionPool sessionPool;

    public SessionManager(SessionPool sessionPool) {
        this.sessionPool = sessionPool;
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
