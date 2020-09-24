package com.github.zy.netty.common.session;

import java.util.List;

/**
 * session 存储策略
 * @version 1.0 created by zy on 2020/4/26 14:50
 */
public interface SessionStorage {

    /**
     * 获取某个session
     * @param sessionId systemId + ip
     * @return
     */
    Session findOne(String sessionId);

    /**
     * 获取到所有的session
     * @return
     */
    List<Session> findAll();

    /**
     * 根据sessionType查找一批session
     * @param sessionType
     * @return
     */
    List<Session> findByType(SessionType sessionType);


    /**
     * 移除某一个session
     * @param sessionId systemId + ip
     * @return
     */
    void delete(String sessionId);

    /**
     * 移除某一批session
     * @param sessionIds systemId + ip
     * @return
     */
    void delete(List<String> sessionIds);

    /**
     * 保存单个session
     * @param session
     */
    void save(Session session);

    /**
     * 保存一批session
     * @param sessions
     */
    void save(Iterable<Session> sessions);
}
