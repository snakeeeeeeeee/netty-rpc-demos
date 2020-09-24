package com.github.zy.netty.client;

import com.github.zy.netty.client.config.RpcClientProperties;
import com.github.zy.netty.common.protocol.MessagePacketDecoder;
import com.github.zy.netty.common.protocol.MessagePacketEncoder;
import com.github.zy.netty.common.protocol.ValidatePacketHandler;
import com.github.zy.netty.common.serialize.FastJsonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0 created by zy on 2020/4/30 14:37
 */
@Slf4j
@Data
@RequiredArgsConstructor
public class RpcClient {

    private final RpcClientProperties rpcClientConfigProperties;
    private ClientHandler clientHandler;

    public ChannelFuture startClient(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        int port = rpcClientConfigProperties.getTargetPort();
        String ip = rpcClientConfigProperties.getTargetIp();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
            bootstrap.option(ChannelOption.TCP_NODELAY, Boolean.TRUE);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(new IdleStateHandler(rpcClientConfigProperties.getDisconnect()
                            , rpcClientConfigProperties.getDisconnect(), rpcClientConfigProperties.getHeartInterval()));
                    ch.pipeline().addLast(new ValidatePacketHandler());
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(2048, 0, 4, 0, 4));
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new MessagePacketDecoder());
                    ch.pipeline().addLast(new MessagePacketEncoder(FastJsonSerializer.INSTANCE));
                    ch.pipeline().addLast(clientHandler);
                }
            });
            return bootstrap.connect(ip, port).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (!future.isSuccess()) {
                        future.channel().eventLoop().schedule(() -> {
                            log.debug("正在重新连接服务端 目标 ip：{}， port：{}", ip, port);
                            startClient(clientHandler);
                        }, rpcClientConfigProperties.getDisconnectRetryInterval(), TimeUnit.SECONDS);
                    }
                }
            }).sync();
        } catch (Exception e) {
            log.error("连接服务端 失败： ip：{}， port：{}", ip, port, e);
        }
        return null;
    }


}
