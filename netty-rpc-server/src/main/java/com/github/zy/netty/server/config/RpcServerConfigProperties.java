package com.github.zy.netty.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version 1.0 created by zy on 2020/4/23 16:30
 */
@Data
@ConfigurationProperties(prefix = "netty-rpc.server", ignoreInvalidFields = true)
public class RpcServerConfigProperties {

    /**
     * 消息同步等待时间
     */
    private int syncTimeout = 30;

    /**
     * 启动监听端口
     */
    private int port = 7777;
    /**
     * 链接处理线程数量
     */
    private int bossThreadGroup = 1;

    /**
     * 工作线程
     */
    private int workThreadGroup;

    /**
     * 多久未收到客户端心跳就断开连接
     */
    private int disconnectInterval = 30;

}
