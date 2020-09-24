package com.github.zy.netty.common.protocol;

import com.github.zy.netty.common.serialize.Serializer;
import com.github.zy.netty.common.utils.ByteBufferUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
public class MessagePacketEncoder extends MessageToByteEncoder<DefaultMessagePacket> {

    private final Serializer serializer;

    @Override
    protected void encode(ChannelHandlerContext context, DefaultMessagePacket packet, ByteBuf out) {
        // 基础包encode
        packet.encode(out);
        // payload
        String payload = packet.getPayload();
        if (!StringUtils.isEmpty(payload)) {
            ByteBufferUtils.INSTANCE.encodeUtf8CharSequence(out, payload);
        } else {
            out.writeInt(0);
        }
    }
}
