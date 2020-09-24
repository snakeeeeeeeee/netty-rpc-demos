package com.github.zy.netty.common.protocol;

import com.github.zy.netty.common.utils.ChannelHandlerContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/25 23:42
 */
@Slf4j
public class ValidatePacketHandler extends SimpleChannelInboundHandler<BaseMessagePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMessagePacket msg) throws Exception {

        //请求标记一样才往后放行
        if (ProtocolConstant.MAGIC_NUMBER == msg.getMagicNumber()) {
            ctx.fireChannelRead(msg);
        } else {
            ChannelHandlerContextUtil contextUtil = ChannelHandlerContextUtil.INSTANCE;
            log.warn("请求标识不匹配 ip: {}, port:{}, magicNumber: {}, 该请求被舍弃", contextUtil.getIp(ctx), contextUtil.getPort(ctx), msg.getSerialNumber());
        }
    }
}
