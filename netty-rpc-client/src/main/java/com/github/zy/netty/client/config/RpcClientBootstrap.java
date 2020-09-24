package com.github.zy.netty.client.config;

import com.github.zy.netty.client.RpcClient;
import com.github.zy.netty.common.async.RpcAsyncService;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @version 1.0 created by zy on 2020/4/24 11:11
 */
@Slf4j
@RequiredArgsConstructor
public class RpcClientBootstrap {

    private final List<RpcClient> clients;

    /**
     * 异步启动,会后台启动线程去尝试启动,将一直尝试到超时结束
     */
    public void startAsync() {
        RpcAsyncService.INSTANCE.execute(() -> {
            clients.forEach(client -> {
                client.startClient(client.getClientHandler());
            });
        });

    }


    /**
     * 同步启动,线程在没有启动完全时,将一直等待至超时
     */
    public void startSync(String systemId) {
        clients.forEach(client -> {
            RpcClientProperties clientProperties = client.getRpcClientConfigProperties();
            if (!StringUtils.isEmpty(systemId)) {
                clientProperties.setSystemId(systemId);
            }
            ChannelFuture future = client.startClient(client.getClientHandler());
            if (!future.isSuccess()) {
                waitStart(future, client);
                startSync(clientProperties.getSystemId());
                //异步监听关闭
                syncClose(future);
            }
        });

    }

    public void startSync(){
        startSync(null);
    }

    private void syncClose(ChannelFuture future) {
        //异步监听关闭
        RpcAsyncService.INSTANCE.execute(() -> {
            try {
                if (future != null) {
                    future.channel().closeFuture().sync();
                }
            } catch (InterruptedException e) {
                log.error("客户端链接发生异常", e);
            }
        });
    }

    private void waitStart(ChannelFuture future, RpcClient client) {
        //最大等待阻塞时间
        long startWaitTime = client.getRpcClientConfigProperties().getStartWaitTime();

        //开始计数时间
        long startTime = System.currentTimeMillis();
        for (; ; ) {
            long currentTime = System.currentTimeMillis() - startTime;

            //如果当前时间已经大于开始计数的时间,那么就跳出本次等待
            if (currentTime > startWaitTime) {
                break;
            }

            boolean startSuccess = false;
            if (future != null) {
                startSuccess = future.isSuccess();
            }

            if (startSuccess) {
                log.debug("连接netty服务端成功,退出等待.");
                break;
            } else {
                log.debug("连接还未准备就绪,线程阻塞.");
            }
            try {
                Thread.sleep(client.getRpcClientConfigProperties().getStartWaitIntervalTime());
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
