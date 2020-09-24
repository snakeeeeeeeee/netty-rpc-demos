package com.github.zy.netty.server.config;

import com.github.zy.netty.common.async.RpcAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;


/**
 * @version 1.0 created by zy on 2020/4/23 16:29
 */
@Slf4j
@RequiredArgsConstructor
public class RpcServerAutoBootstrap implements CommandLineRunner {

    private final RpcServer rpcServer;

    public void start() {
        rpcServer.startAsync();
    }


    @Override
    public void run(String... args) {
        RpcAsyncService.INSTANCE.execute(this::start);
    }
}
