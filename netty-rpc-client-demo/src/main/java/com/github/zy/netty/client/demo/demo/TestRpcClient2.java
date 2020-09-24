package com.github.zy.netty.client.demo.demo;

import com.github.zy.netty.client.annotation.RpcRequestClient;
import com.github.zy.netty.common.annotation.RpcMapping;
/**
 * @version 1.0 created by zy on 2020/6/10 17:09
 */
@RpcRequestClient(serverId = "service1")
public interface TestRpcClient2 {

    @RpcMapping(url = "/server-test")
    HelloResult hello(HelloModule helloModule);
}
