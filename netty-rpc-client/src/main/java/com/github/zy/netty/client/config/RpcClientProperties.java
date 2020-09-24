package com.github.zy.netty.client.config;

import lombok.Data;

/**
 * @version 1.0 created by zy on 2020/6/8 17:26
 */
@Data
public class RpcClientProperties {

    /**
     * 连接端口
     */
    private int targetPort = 7777;

    /**
     * 连接IP
     */
    private String targetIp = "127.0.0.1";

    /**
     * 模块Id
     */
    private String systemId = "";

    /**
     * 远程连接的server端的唯一标识
     */
    private String targetServerId;


    /**
     * 是否开启重连
     */
    private boolean disconnectRetry = true;

    /**
     * 与服务端断开连接后重连间隔 单位: s
     */
    private int disconnectRetryInterval = 20;

    /**
     * 向客户端发送心跳的间隔 单位: s
     */
    private int heartInterval = 10;

    /**
     * 多久未收到服务端响应就断开连接的时间
     */
    private int disconnect = 30;

    /**
     * 启动最大等待时间 单位: ms
     */
    private long startWaitTime = 30000;

    /**
     * 启动等待间隔检查时间 单位:ms
     */
    private long startWaitIntervalTime = 500;

    /**
     *
     */
    private int syncTimeout = 30;

}
