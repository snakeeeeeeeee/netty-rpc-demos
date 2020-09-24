package com.github.zy.netty.server.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/6/16 10:07
 */
@Slf4j
@RequiredArgsConstructor
public class RpcServerBootstrap {

    private final RpcServer rpcServer;

    public void start() {
        rpcServer.startAsync();
    }
}
