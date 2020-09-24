package com.github.zy.netty.server.demo.demo;

import com.github.zy.netty.common.session.Session;
import com.github.zy.netty.common.session.SessionManager;
import com.github.zy.netty.server.service.ServerSendTemplate;
import com.github.zy.netty.server.strategy.ServerMessageResolverStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/6/10 16:58
 */
@RestController
public class ServerDemoController {

//    @Autowired
//    private SessionManager sessionManager;
//    @Autowired
//    private ServerSendTemplate serverSendTemplate;
//
//
//    @RequestMapping("/sessions")
//    public List<Session> sessions(){
//        return sessionManager.findAll();
//    }
//
//    @RequestMapping("/send-client")
//    public HelloModule send(){
//        HelloModule helloModule = new HelloModule();
//        helloModule.setDesc("asddasd");
//        HelloModule result = serverSendTemplate.sendToClient("ttxb", "2.0.1.85", "/test/send-client", helloModule, HelloModule.class);
//
//        return result;
//    }
}
