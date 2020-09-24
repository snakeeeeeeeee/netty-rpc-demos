package com.github.zy.netty.client.demo;

import com.github.zy.netty.client.annotation.EnableRpcClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRpcClient
public class NettyRpcClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcClientApplication.class, args);
    }

}
