package com.github.zy.netty.client.config;

import com.github.zy.netty.client.RpcClient;
import com.github.zy.netty.common.async.RpcAsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/6/8 11:09
 */
@RequiredArgsConstructor
public class RpcClientAutoBootstrap implements CommandLineRunner {

    private final List<RpcClient> clients;

    @Override
    public void run(String... args) {
        RpcAsyncService.INSTANCE.execute(() -> clients.forEach(client -> client.startClient(client.getClientHandler())));
    }
}
