package com.github.zy.netty.client.strategy;

import com.github.zy.netty.common.domain.RpcMappingBean;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessageType;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/4/28 23:45
 */
public interface ClientMessageResolverStrategy {

    boolean support(MessageType messageType);

    void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg);

    default void validateMethodParams(List<RpcMappingBean.RpcMethodParam> methodParams) {
        if (!CollectionUtils.isEmpty(methodParams) && methodParams.size() > 1) {
            throw new RuntimeException("方法参数暂时只能支持单个");
        }
    }
}
