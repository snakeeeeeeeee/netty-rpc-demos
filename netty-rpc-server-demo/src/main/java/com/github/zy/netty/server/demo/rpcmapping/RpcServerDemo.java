package com.github.zy.netty.server.demo.rpcmapping;


import com.github.zy.netty.common.annotation.RpcMapping;
import com.github.zy.netty.server.annotation.RpcService;
import com.github.zy.netty.server.demo.demo.HelloModule;
import com.github.zy.netty.server.demo.demo.HelloResult;

/**
 * @version 1.0 created by zy on 2020/6/9 18:09
 */
@RpcService
public class RpcServerDemo {

    @RpcMapping(url = "/server-test")
    public HelloResult serverRpcTest(HelloModule helloModule){
        System.out.println(helloModule);
        return HelloResult.builder().desc("来自服务端的返回信息").build();
    }
}
