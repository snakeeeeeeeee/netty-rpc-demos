package com.github.zy.netty.client.annotation;

import com.github.zy.netty.client.config.RpcClientAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @version 1.0 created by zy on 2020/6/12 15:34
 */
@Import(RpcClientAutoConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRpcClient {
}
