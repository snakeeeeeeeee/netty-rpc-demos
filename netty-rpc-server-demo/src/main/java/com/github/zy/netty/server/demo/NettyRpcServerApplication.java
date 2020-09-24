package com.github.zy.netty.server.demo;

import com.github.zy.netty.server.annotation.EnableRpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRpcServer
@SpringBootApplication
public class NettyRpcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcServerApplication.class, args);
    }

}
