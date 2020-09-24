package com.github.zy.netty.server.config;

import com.github.zy.netty.common.service.CleanSessionService;
import com.github.zy.netty.common.session.DefaultSessionManager;
import com.github.zy.netty.common.session.SessionManager;
import com.github.zy.netty.common.session.SessionStorage;
import com.github.zy.netty.common.session.SimpleNativeCacheSessionStorage;
import com.github.zy.netty.server.service.DefaultServerSendTemplate;
import com.github.zy.netty.server.service.ServerHandler;
import com.github.zy.netty.server.service.ServerSendTemplate;
import com.github.zy.netty.server.strategy.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0 created by zy on 2020/4/23 16:29
 */
@Data
@Slf4j
@Configuration
@EnableConfigurationProperties(value = RpcServerConfigProperties.class)
public class RpcServerAutoConfiguration {

    /**
     * 服务端自动启动类
     * @return
     */
    @Bean
    public RpcServerAutoBootstrap serverAutoBootstrap(RpcServerConfigProperties configProperties, ServerHandler serverHandler) {
        RpcServer rpcServer = new RpcServer(configProperties, serverHandler);
        return new RpcServerAutoBootstrap(rpcServer);
    }

    /**
     * 服务端启动类,如果不开启自动启动的话,会注入这个bean,直接在用的地方 start即可
     * @return
     */
//    @Bean
//    @ConditionalOnMissingBean(RpcServerAutoBootstrap.class)
//    public RpcServerBootstrap serverBootstrap(RpcServer rpcServer){
//        return new RpcServerBootstrap(rpcServer);
//    }

    /**
     * 服务端封装
     * @param configProperties
     * @param serverHandler
     * @return
     */
//    @Bean
//    public RpcServer rpcServer(RpcServerConfigProperties configProperties, ServerHandler serverHandler){
//        return new RpcServer(configProperties, serverHandler);
//    }


    /**
     * session的会话管理
     *
     * @param sessionStorage
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionManager.class)
    public SessionManager sessionManager(SessionStorage sessionStorage) {
        return new DefaultSessionManager(sessionStorage);
    }

    /**
     * session的存储
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionStorage.class)
    public SessionStorage sessionStorage() {
        return new SimpleNativeCacheSessionStorage();
    }


    /**
     * 解析注解 @RpcService的处理类,并且持有解析后的数据
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public RpcServerContextHolder rpcServerContextHolder(ApplicationContext applicationContext) {
        return new RpcServerContextHolder(applicationContext);
    }

    /**
     * 与netty api结合的服务端处理类
     *
     * @param serverMessageHandle 服务端消息处理类
     * @return
     */
    @Bean
    public ServerHandler serverHandler(ServerMessageHandle serverMessageHandle) {
        return new ServerHandler(serverMessageHandle);
    }

    /**
     * 服务端消息处理类
     *
     * @return
     */
    @Bean
    public ServerMessageHandle serverMessageHandle(ApplicationContext applicationContext, SessionManager sessionManager, RpcServerContextHolder contextHolder) {
        List<ServerMessageResolverStrategy> strategies = new ArrayList<>();
        strategies.add(new ServerMessagePingResolver());
        strategies.add(new ServerMessageReportModuleIdResolver(sessionManager, applicationContext));
        strategies.add(new ServerMessageRequestResolver(contextHolder));
        strategies.add(new ServerMessageResponseResolver());
        return new ServerMessageHandle(strategies);
    }


    /**
     * 清理session会话的处理类
     *
     * @param sessionManager     session管理
     * @param applicationContext applicationContext
     * @return
     */
    @Bean
    public CleanSessionService syncTaskExecutor(SessionManager sessionManager, ApplicationContext applicationContext) {
        return new CleanSessionService(sessionManager, applicationContext);
    }

    /**
     * 服务端向客户端发送消息的模板类
     *
     * @param sessionManager            session会话管理类
     * @param rpcServerConfigProperties 配置类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ServerSendTemplate.class)
    public ServerSendTemplate serverSendTemplate(SessionManager sessionManager, RpcServerConfigProperties rpcServerConfigProperties) {
        return new DefaultServerSendTemplate(sessionManager, rpcServerConfigProperties);
    }


//    /**
//     * server 消息处理策略beans
//     * @param applicationContext
//     * @param sessionManager
//     * @param contextHolder
//     * @return
//     */
//    @Bean
//    public List<ServerMessageResolverStrategy> serverMessageStrategys(ApplicationContext applicationContext, SessionManager sessionManager, RpcServerContextHolder contextHolder) {
//        List<ServerMessageResolverStrategy> result = new ArrayList<>();
//        result.add(new ServerMessagePingResolver());
//        result.add(new ServerMessageReportModuleIdResolver(sessionManager, applicationContext));
//        result.add(new ServerMessageRequestResolver(contextHolder));
//        result.add(new ServerMessageResponseResolver());
//        return result;
//    }
}
