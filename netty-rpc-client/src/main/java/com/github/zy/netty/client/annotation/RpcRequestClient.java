package com.github.zy.netty.client.annotation;


import com.github.zy.netty.common.constants.RpcRequestClientType;

import java.lang.annotation.*;

/**
 * @version 1.0 created by zy on 2020/4/26 9:45
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcRequestClient {

    /**
     * 服务端的id标识,声明要请求哪一个服务端
     * @return
     */
    String serverId();

    RpcRequestClientType targetType() default RpcRequestClientType.SERVER;

}
