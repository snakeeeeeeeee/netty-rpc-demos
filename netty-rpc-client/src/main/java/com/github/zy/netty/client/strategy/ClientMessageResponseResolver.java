package com.github.zy.netty.client.strategy;

import com.alibaba.fastjson.JSON;
import com.github.zy.netty.common.domain.MessageResponseModule;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessageType;
import com.github.zy.netty.common.sync.SyncFuture;
import com.github.zy.netty.common.sync.SyncFutureHolder;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/29 0:03
 */
@Slf4j
@RequiredArgsConstructor
public class ClientMessageResponseResolver implements ClientMessageResolverStrategy {
    @Override
    public boolean support(MessageType messageType) {
        return MessageType.RESPONSE.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        log.debug("接收到响应包,内容:{}", JSON.toJSONString(msg));
        MessageResponseModule responseModule = new MessageResponseModule();

        try {
            SyncFuture syncFuture = SyncFutureHolder.SYNC_FUTURE_MAP.get(msg.getSerialNumber());
            if(syncFuture == null){
                responseModule.setCode(500);
                responseModule.setInfo("future 无返回");
                syncFuture.setResponse(responseModule);
            }else{
                responseModule.setCode(msg.getResponseCode());
                responseModule.setInfo(msg.getResponseDesc());
                responseModule.setPayload(msg.getPayload());
                syncFuture.setResponse(responseModule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
