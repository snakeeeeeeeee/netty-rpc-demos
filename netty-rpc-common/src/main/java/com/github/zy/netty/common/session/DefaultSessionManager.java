package com.github.zy.netty.common.session;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/4/26 14:49
 */
public class DefaultSessionManager implements SessionManager {

    private final SessionStorage sessionStorage;

    public DefaultSessionManager(SessionStorage sessionStorage){
        this.sessionStorage = sessionStorage;
    }

    @Override
    public Session findOne(String sessionId) {
        return sessionStorage.findOne(sessionId);
    }

    @Override
    public List<Session> findAll() {
        return sessionStorage.findAll();
    }

    @Override
    public List<Session> findByType(SessionType sessionType) {
        return sessionStorage.findByType(sessionType);
    }


    @Override
    public void delete(String sessionId) {
        sessionStorage.delete(sessionId);
    }

    @Override
    public void delete(List<String> sessionIds) {
        sessionStorage.delete(sessionIds);
    }

    @Override
    public void save(Session session) {
        sessionStorage.save(session);
    }

    @Override
    public void save(Iterable<Session> sessions) {
        sessionStorage.save(sessions);
    }
}
