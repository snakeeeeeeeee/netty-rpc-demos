package com.github.zy.netty.common.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 消息编码
 */
@RequiredArgsConstructor
public class MessagePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> list) {
        DefaultMessagePacket packet = new DefaultMessagePacket();
        // 基础包decode
        packet.decode(in);

        // payload - ByteBuf实例
        //if (!StringUtils.isEmpty(packet.getPayload())) {
        int payloadLength = in.readInt();
        if(payloadLength != 0){
            packet.setPayload(in.readCharSequence(payloadLength, ProtocolConstant.UTF_8).toString());
        }
        //}

        list.add(packet);
    }
}
