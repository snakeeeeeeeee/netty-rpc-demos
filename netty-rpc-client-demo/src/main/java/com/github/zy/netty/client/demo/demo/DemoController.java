package com.github.zy.netty.client.demo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0 created by zy on 2020/6/10 16:46
 */
@RestController
public class DemoController {

    @Autowired
    private TestRpcClient2 rpcClient;

    @RequestMapping("/client/test")
    public HelloResult test() {
        HelloModule helloModule = new HelloModule();
        helloModule.setDesc("hello server~~~");
        HelloResult hello = rpcClient.hello(helloModule);
        return hello;
    }
}
