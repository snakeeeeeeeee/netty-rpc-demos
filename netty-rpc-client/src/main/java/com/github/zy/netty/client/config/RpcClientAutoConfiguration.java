package com.github.zy.netty.client.config;

import com.github.zy.netty.client.ClientHandler;
import com.github.zy.netty.client.RpcClient;
import com.github.zy.netty.client.RpcClientContextHolder;
import com.github.zy.netty.client.proxy.RpcRequestClientRegister;
import com.github.zy.netty.client.strategy.*;
import com.github.zy.netty.common.session.DefaultSessionManager;
import com.github.zy.netty.common.session.SessionManager;
import com.github.zy.netty.common.session.SessionStorage;
import com.github.zy.netty.common.session.SimpleNativeCacheSessionStorage;
import com.github.zy.netty.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0 created by zy on 2020/4/23 16:29
 */
@Slf4j
@Configuration
@Import(RpcRequestClientRegister.class)
@EnableConfigurationProperties(value = RpcClientConfigProperties.class)
public class RpcClientAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean(RpcClientAutoBootstrap.class)
//    public RpcClientBootstrap clientBootstrap(List<RpcClient> clients) {
//        return new RpcClientBootstrap(clients);
//    }

    @Bean
    public RpcClientAutoBootstrap rpcClientAutoBootstrap(List<RpcClient> clients){
        return new RpcClientAutoBootstrap(clients);
    }


    /**
     * 解析注解 @RpcClient的处理类
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public RpcClientContextHolder rpcClientContextHolder(ApplicationContext applicationContext) {
        return new RpcClientContextHolder(applicationContext);
    }


    /**
     * rpc客户端
     *
     * @param clientsConfigProperties 配置类
     * @return
     */
//    @Bean
//    public List<RpcClient> rpcClients(RpcClientConfigProperties clientsConfigProperties, ClientMessageHandle clientMessageHandle) {
//        Map<String, RpcClientProperties> connectionsMap = clientsConfigProperties.getConnections();
//        if(CollectionUtils.isEmpty(connectionsMap)){
//            return Collections.emptyList();
//        }
//        return filterClient(connectionsMap).stream().map(clientProperties -> {
//            //将配置的key设置为 serverId
//            clientProperties.setTargetServerId(clientProperties.getTargetServerId());
//            //设置客户端的系统ID
//            clientProperties.setSystemId(clientsConfigProperties.getSystemId());
//
//            RpcClient rpcClient = new RpcClient(clientProperties);
//            ClientHandler clientHandler = new ClientHandler(clientMessageHandle, clientProperties, rpcClient);
//            rpcClient.setClientHandler(clientHandler);
//
//            return rpcClient;
//        }).collect(Collectors.toList());
//    }

    @Bean
    public RpcClient rpcClient(RpcClientConfigProperties clientsConfigProperties, ClientMessageHandle clientMessageHandle) {
        RpcClient rpcClient = new RpcClient(clientsConfigProperties.getParams());
        ClientHandler clientHandler = new ClientHandler(clientMessageHandle, clientsConfigProperties.getParams(), rpcClient);
        rpcClient.setClientHandler(clientHandler);

        return rpcClient;
    }

    /**
     * session的会话管理
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
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionStorage.class)
    public SessionStorage sessionStorage() {
        return new SimpleNativeCacheSessionStorage();
    }


    /**
     * 客户端消息处理类
     *
     * @param strategies 客户端消息处理策略
     * @return
     */
    @Bean
    public ClientMessageHandle clientMessageHandle(List<ClientMessageResolverStrategy> strategies) {
        return new ClientMessageHandle(strategies);
    }


    /**
     * 注册被 @RpcRequestClient 标记的bean的接口的代理类到spring容器
     *
     * @return
     */
    @Bean
    public RpcRequestClientRegister rpcRequestClientRegister() {
        return new RpcRequestClientRegister();
    }


    @Bean
    public SpringContextUtil springContextUtil(){
        return new SpringContextUtil();
    }


    /************************************* client 消息处理策略bean *******************************************************/

    @Bean
    List<ClientMessageResolverStrategy> clientMessageResolvers(RpcClientContextHolder contextHolder){
        List<ClientMessageResolverStrategy> result = new ArrayList<>();
        result.add(new ClientMessageRequestResolver(contextHolder));
        result.add(new ClientMessageResponseResolver());
        result.add(new ClientMessagePongResolver());
        return result;
    }


    /**
     *
     * 过滤ip与端口一样的client配置
     * @param clientMap
     * @return
     */
//    private List<RpcClientProperties> filterClient(Map<String, RpcClientProperties> clientMap){
//        Map<String, RpcClientProperties> result = new HashMap<>();
//        clientMap.entrySet().forEach(entry -> {
//            RpcClientProperties properties = entry.getValue();
//            properties.setTargetServerId(entry.getKey());
//            result.put(properties.getTargetIp() + ":" + properties.getTargetPort(), properties);
//        });
//        return new ArrayList<>(result.values());
//    }
}
