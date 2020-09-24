package com.github.zy.netty.common.session;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @version 1.0 created by zy on 2020/5/8 10:07
 */
public class SimpleNativeCacheSessionStorage implements SessionStorage {

    /**
     * key - systemId:ip , value - Session
     */
    private final ConcurrentHashMap<String, Session> cache = new ConcurrentHashMap<>();


    @Override
    public Session findOne(String sessionId) {
        return cache.get(sessionId);
    }

    @Override
    public List<Session> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public List<Session> findByType(SessionType sessionType) {
        return cache.values().stream().filter(session -> sessionType.equals(session.getSessionType())).collect(Collectors.toList());
    }

    @Override
    public void delete(String sessionId) {
        cache.remove(sessionId);
    }

    @Override
    public void delete(List<String> sessionIds) {
        sessionIds.forEach(cache::remove);
    }

    @Override
    public void save(Session session) {
        cache.put(session.getId(), session);
    }

    @Override
    public void save(Iterable<Session> sessions) {
        sessions.forEach(session -> cache.put(session.getId(), session));
    }
}
