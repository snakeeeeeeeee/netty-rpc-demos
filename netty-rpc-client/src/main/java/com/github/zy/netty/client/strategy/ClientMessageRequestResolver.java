package com.github.zy.netty.client.strategy;

import com.alibaba.fastjson.JSON;
import com.github.zy.netty.client.RpcClientContextHolder;
import com.github.zy.netty.common.domain.RpcMappingBean;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessagePacketBuilder;
import com.github.zy.netty.common.protocol.MessageType;
import com.github.zy.netty.common.protocol.ResponseCode;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0 created by zy on 2020/4/29 0:03
 */
@Slf4j
@RequiredArgsConstructor
public class ClientMessageRequestResolver implements ClientMessageResolverStrategy {

    private final RpcClientContextHolder contextHolder;

    @Override
    public boolean support(MessageType messageType) {
        return MessageType.REQUEST.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        //处理请求消息，分发到对应的映射中去
        DefaultMessagePacket response = null;
        try {
            String requestUrlMapping = msg.getMappingUrl();
            Optional<RpcMappingBean> optional = contextHolder.getClientByUrlMapping(requestUrlMapping);
            if (optional.isPresent()) {
                RpcMappingBean rpcMappingBean = optional.get();

                Method targetMethod = rpcMappingBean.getTargetMethod();
                Object targetInstance = rpcMappingBean.getTargetInstance();

                List<RpcMappingBean.RpcMethodParam> methodParams = rpcMappingBean.getMethodParams();
                validateMethodParams(methodParams);

                RpcMappingBean.RpcMethodParam rpcMethodParam = methodParams.get(0);
                Class<?> paramType = rpcMethodParam.getParamType();

                //反射调用目标方法 todo 这里后边应该改为根据参数名称，类型去进行封装
                Object returnValue = targetMethod.invoke(targetInstance, JSON.parseObject(msg.getPayload(), paramType));
                if (rpcMappingBean.isNeedReturn()) {
                    //如果需要返回值，那么就将数据返回
                    response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.SUCCESS).serialNumber(msg.getSerialNumber())
                            .payload(JSON.toJSONString(returnValue)).build();
                } else {
                    response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.SUCCESS).serialNumber(msg.getSerialNumber()).build();
                }
            } else {
                response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.NOT_FOUND).serialNumber(msg.getSerialNumber()).build();
                log.warn("请求服务端未匹配到 映射urlMapping ： {}", requestUrlMapping);
            }
        } catch (Exception e) {
            log.error("处理服务端请求发生错误", e);
            response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.ERROR).serialNumber(msg.getSerialNumber()).build();
        } finally {
            ctx.channel().writeAndFlush(response);
        }
    }
}
