package com.github.zy.netty.server.annotation;

import com.github.zy.netty.server.config.RpcServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @version 1.0 created by zy on 2020/6/16 10:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Import(RpcServerAutoConfiguration.class)
public @interface EnableRpcServer {
}
