package com.github.zy.netty.server.strategy;

import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessagePacketBuilder;
import com.github.zy.netty.common.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/28 0:40
 */
@Slf4j
public class ServerMessagePingResolver implements ServerMessageResolverStrategy {
    @Override
    public boolean support(MessageType messageType) {
        return MessageType.PING.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        //ping(心跳)的处理策略
        log.debug("收到客户端心跳请求...");
        DefaultMessagePacket packet = MessagePacketBuilder.buildBasicPong().build();
        sendMsg(ctx, packet);
    }
}
