package com.github.zy.netty.server.service;


import com.github.zy.netty.common.session.Session;

/**
 * @version 1.0 created by zy on 2020/4/29 15:58
 */
public interface ServerSendTemplate {

    <T> T sendToClient(Session session, String urlMapping, Object msg, Class clazz);

    <T> T sendToClient(String systemId, String ip, String urlMapping, Object msg, Class clazz);

}
