package com.github.zy.netty.client.proxy;

import com.alibaba.fastjson.JSON;
import com.github.zy.netty.client.ClientChannelHolder;
import com.github.zy.netty.client.annotation.RpcRequestClient;
import com.github.zy.netty.client.config.RpcClientConfigProperties;
import com.github.zy.netty.common.annotation.RpcMapping;
import com.github.zy.netty.common.domain.MessageResponseModule;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessagePacketBuilder;
import com.github.zy.netty.common.protocol.ResponseCode;
import com.github.zy.netty.common.sync.SyncFuture;
import com.github.zy.netty.common.sync.SyncFutureHolder;
import com.github.zy.netty.common.utils.SpringContextUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 代理
 *
 * @version 1.0 created by zy on 2020/4/24 0:00
 */
@Slf4j
public class RpcRequestClientProxy<T> implements InvocationHandler {

    private Class<T> clazz;

    private RpcClientConfigProperties configProperties;

    public RpcRequestClientProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RpcMapping rpcMapping = method.getAnnotation(RpcMapping.class);
        if (rpcMapping != null) {
            validateParam(args);
            RpcRequestClient rpcRequestClient = clazz.getAnnotation(RpcRequestClient.class);
            RpcRequestTemplate requestTemplate = RpcRequestTemplate.builder().type(rpcRequestClient.targetType())
                    .urlMapping(rpcMapping.url()).serverId(rpcRequestClient.serverId()).syncTime(getConfigProperties().getParams().getSyncTimeout()).payload(JSON.toJSONString(args[0])).build();

            String result = execute(requestTemplate);
            return JSON.parseObject(result, method.getReturnType());
        }
        return null;
    }

    //todo 日志需要记录详细点, 异常需要自定义异常
    private void validateParam(Object[] param) {
        if (param != null && param.length > 1) {
            throw new RuntimeException("方法参数暂时只支持单个,但是找到了" + param.length + "个");
        }
    }

    private String execute(RpcRequestTemplate template) {
        DefaultMessagePacket messagePacket = MessagePacketBuilder.buildBasicRequest()
                .payload(template.getPayload()).systemId(template.getSystemId()).urlMapping(template.getUrlMapping()).build();
        try {
            //客户端请求服务端
            Channel channel = ClientChannelHolder.CHANNEL_MAP.get(template.getServerId());
            if (channel == null) {
                throw new RuntimeException("未获取到 channel, 或者客户端与服务端已经断开连接......");
            }

            //完成后，在这里进行一个同步等待，等到客户端接收到数据后，解除后获取数据
            SyncFuture<MessageResponseModule> syncFuture = new SyncFuture();
            SyncFutureHolder.SYNC_FUTURE_MAP.put(messagePacket.getSerialNumber(), syncFuture);

            channel.writeAndFlush(messagePacket);

            MessageResponseModule responseModule = syncFuture.get(template.getSyncTime(), TimeUnit.SECONDS);
            if (ResponseCode.SUCCESS.getCode() == responseModule.getCode()) {
                return responseModule.getPayload();
            }
            throw new RuntimeException(String.format("请求发生错误, code: %s , info : %s", responseModule.getCode(), responseModule.getInfo()));
        } finally {
            SyncFutureHolder.SYNC_FUTURE_MAP.remove(messagePacket.getSerialNumber());
        }

    }


    private RpcClientConfigProperties getConfigProperties() {
        if (configProperties == null) {
            this.configProperties = SpringContextUtil.getBean(RpcClientConfigProperties.class);
        }
        return configProperties;
    }
}
