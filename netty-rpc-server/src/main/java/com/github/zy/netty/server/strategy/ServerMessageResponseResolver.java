package com.github.zy.netty.server.strategy;

import com.github.zy.netty.common.domain.MessageResponseModule;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessageType;
import com.github.zy.netty.common.sync.SyncFuture;
import com.github.zy.netty.common.sync.SyncFutureHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/28 0:36
 */
@Slf4j
public class ServerMessageResponseResolver implements ServerMessageResolverStrategy {
    @Override
    public boolean support(MessageType messageType) {
        return MessageType.RESPONSE.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        //包装返回对象
        MessageResponseModule responseModule = new MessageResponseModule();
        responseModule.setCode(msg.getResponseCode());
        responseModule.setInfo(msg.getResponseDesc());
        responseModule.setPayload(msg.getPayload());

        SyncFuture syncFuture = SyncFutureHolder.SYNC_FUTURE_MAP.get(msg.getSerialNumber());
        syncFuture.setResponse(responseModule);
    }
}
