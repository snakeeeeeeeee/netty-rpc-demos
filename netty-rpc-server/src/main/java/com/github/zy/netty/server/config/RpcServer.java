package com.github.zy.netty.server.config;

import com.github.zy.netty.common.protocol.MessagePacketDecoder;
import com.github.zy.netty.common.protocol.MessagePacketEncoder;
import com.github.zy.netty.common.protocol.ValidatePacketHandler;
import com.github.zy.netty.common.serialize.FastJsonSerializer;
import com.github.zy.netty.server.service.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/6/18 13:49
 */
@Slf4j
@RequiredArgsConstructor
public class RpcServer {

    private final RpcServerConfigProperties configProperties;
    private final ServerHandler serverHandler;

    public void startAsync() {
        log.info("开始启动NettyServer...");
        int port = configProperties.getPort();
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(2048, 0, 4, 0, 4));
                            ch.pipeline().addLast(new LengthFieldPrepender(4));
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new MessagePacketDecoder());
                            ch.pipeline().addLast(new MessagePacketEncoder(FastJsonSerializer.INSTANCE));
                            ch.pipeline().addLast(new IdleStateHandler(0, 0, configProperties.getDisconnectInterval()));
                            ch.pipeline().addLast(new ValidatePacketHandler());
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("启动NettyServer[{}]成功...", port);
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("启动NettyServer[" + port + "]失败...", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
