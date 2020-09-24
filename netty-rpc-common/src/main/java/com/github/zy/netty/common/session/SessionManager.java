package com.github.zy.netty.common.session;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/4/26 14:16
 */
public interface SessionManager {

    /**
     * 根据sessionId得到一个session
     *
     * @param sessionId
     * @return
     */
    Session findOne(String sessionId);


    /**
     * 获取所有的session
     *
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
     *
     * @param sessionId
     * @return
     */
    void delete(String sessionId);

    /**
     * 移除某一批session
     *
     * @param sessionIds
     * @return
     */
    void delete(List<String> sessionIds);

    /**
     * 保存单个session
     *
     * @param session
     */
    void save(Session session);

    /**
     * 保存一批session
     *
     * @param sessions
     */
    void save(Iterable<Session> sessions);

}
