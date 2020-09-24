package com.github.zy.netty.server.strategy;

import com.alibaba.fastjson.JSON;

import com.github.zy.netty.common.domain.RpcMappingBean;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessagePacketBuilder;
import com.github.zy.netty.common.protocol.MessageType;
import com.github.zy.netty.common.protocol.ResponseCode;
import com.github.zy.netty.server.config.RpcServerContextHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @version 1.0 created by zy on 2020/4/28 0:34
 */
@Slf4j
@RequiredArgsConstructor
public class ServerMessageRequestResolver implements ServerMessageResolverStrategy {

    private final RpcServerContextHolder contextHolder;

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
            Optional<RpcMappingBean> optional = contextHolder.getServiceByUrlMapping(requestUrlMapping);
            if (optional.isPresent()) {
                RpcMappingBean rpcMappingBean = optional.get();

                Method targetMethod = rpcMappingBean.getTargetMethod();
                Object targetInstance = rpcMappingBean.getTargetInstance();

                List<RpcMappingBean.RpcMethodParam> methodParams = rpcMappingBean.getMethodParams();
                validateMethodParams(methodParams);

                //todo 这里为了方便 直接取方法的第一个参数,也许这里后边要改为支持类似@RequestParam?
                Object returnValue = null;
                if (CollectionUtils.isEmpty(methodParams)) {
                    //本地方法没有接受参数的情况
                    targetMethod.invoke(targetInstance);
                } else {
                    //本地方法有接受参数的情况
                    RpcMappingBean.RpcMethodParam rpcMethodParam = methodParams.get(0);
                    Class<?> paramType = rpcMethodParam.getParamType();
                    returnValue = targetMethod.invoke(targetInstance, JSON.parseObject(msg.getPayload(), paramType));
                }

                //构建基础的成功返回报文
                MessagePacketBuilder.MessageBuilder successBuilder = MessagePacketBuilder.buildBasicResponse()
                        .responseCode(ResponseCode.SUCCESS).serialNumber(msg.getSerialNumber());
                if (rpcMappingBean.isNeedReturn()) {
                    //如果需要返回值，那么就将数据返回
                    response = successBuilder.payload(JSON.toJSONString(returnValue)).build();
                } else {
                    response = successBuilder.build();
                }
            } else {
                response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.NOT_FOUND).serialNumber(msg.getSerialNumber()).build();
            }
        } catch (Exception e) {
            response = MessagePacketBuilder.buildBasicResponse().responseCode(ResponseCode.ERROR).serialNumber(msg.getSerialNumber()).build();
            log.error("处理客户端请求发生异常", e);
        } finally {
            sendMsg(ctx, response);
        }
    }
}
