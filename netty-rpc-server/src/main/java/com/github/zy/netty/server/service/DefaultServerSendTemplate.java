package com.github.zy.netty.server.service;

import com.alibaba.fastjson.JSON;
import com.github.zy.netty.common.domain.MessageResponseModule;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessagePacketBuilder;
import com.github.zy.netty.common.protocol.ResponseCode;
import com.github.zy.netty.common.session.Session;
import com.github.zy.netty.common.session.SessionHelper;
import com.github.zy.netty.common.session.SessionManager;
import com.github.zy.netty.common.sync.SyncFuture;
import com.github.zy.netty.common.sync.SyncFutureHolder;
import com.github.zy.netty.server.config.RpcServerConfigProperties;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0 created by zy on 2020/4/29 15:59
 */
@RequiredArgsConstructor
public class DefaultServerSendTemplate implements ServerSendTemplate {

    private final SessionManager sessionManager;
    private final RpcServerConfigProperties rpcServerConfigProperties;


    @Override
    public <T> T sendToClient(Session session, String urlMapping, Object msg, Class clazz) {
        return (T)doSend(session, msg, urlMapping, clazz);
    }

    @Override
    public <T> T sendToClient(String systemId, String ip, String urlMapping, Object msg, Class clazz) {
        String sessionId = SessionHelper.getSessionId(systemId, ip);
        Session session = sessionManager.findOne(sessionId);
        if(session == null){
            throw new RuntimeException(String.format("未找到session ：sessionId -> %s ", sessionId));
        }
        return (T)doSend(session, msg, urlMapping, clazz);
    }


    private <T> T doSend(Session session, Object msg, String urlMapping, Class clazz){
        DefaultMessagePacket messagePacket = MessagePacketBuilder.buildBasicRequest()
                .payload(JSON.toJSONString(msg)).urlMapping(urlMapping).build();
        try {
            session.getChannel().writeAndFlush(messagePacket);

            //完成后，在这里进行一个同步等待，等到客户端接收到数据后，解除后获取数据
            SyncFuture<MessageResponseModule> syncFuture = new SyncFuture<>();
            SyncFutureHolder.SYNC_FUTURE_MAP.put(messagePacket.getSerialNumber(), syncFuture);

            MessageResponseModule responseModule = syncFuture.get(rpcServerConfigProperties.getSyncTimeout(), TimeUnit.SECONDS);
            if (ResponseCode.SUCCESS.getCode() == responseModule.getCode()) {
                return (T) JSON.parseObject(responseModule.getPayload(), clazz);
            }
            throw new RuntimeException(String.format("请求发生错误, code: %s , info : %s", responseModule.getCode(), responseModule.getInfo()));
        } finally {
            // 清除全局缓存中的SyncFuture
            SyncFutureHolder.SYNC_FUTURE_MAP.remove(messagePacket.getSerialNumber());
        }
    }
}
