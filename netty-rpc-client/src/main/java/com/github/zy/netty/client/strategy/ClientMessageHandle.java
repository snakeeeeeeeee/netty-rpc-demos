package com.github.zy.netty.client.strategy;

import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * @version 1.0 created by zy on 2020/4/28 23:42
 */
@AllArgsConstructor
public class ClientMessageHandle {

    private final List<ClientMessageResolverStrategy> strategies;

    public void handle(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        Optional<ClientMessageResolverStrategy> optional = strategies.stream().filter(strategie -> strategie.support(msg.getMessageType())).findFirst();
        optional.ifPresent(clientMessageResolverStrategy -> clientMessageResolverStrategy.resolver(ctx, msg));
    }
}
