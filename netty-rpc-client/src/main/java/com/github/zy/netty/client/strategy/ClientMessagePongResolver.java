package com.github.zy.netty.client.strategy;

import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/28 0:42
 */
@Slf4j
public class ClientMessagePongResolver implements ClientMessageResolverStrategy {
    @Override
    public boolean support(MessageType messageType) {
        return MessageType.PONG.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        //处理回应ping的策略
        log.debug("收到服务端心跳回复...");
    }
}
