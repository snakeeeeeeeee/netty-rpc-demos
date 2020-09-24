package com.github.zy.netty.client.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @version 1.0 created by zy on 2020/4/23 16:17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RpcClient {

    /**
     * 客户端的id标识,用于匹配服务端
     * @return
     */
    String clientId() default "";
}
