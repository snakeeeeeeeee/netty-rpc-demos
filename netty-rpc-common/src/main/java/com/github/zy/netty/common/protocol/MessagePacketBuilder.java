package com.github.zy.netty.common.protocol;

import com.github.zy.netty.common.utils.SerialNumberUtils;
import lombok.Data;

/**
 * @version 1.0 created by zy on 2020/4/25 21:56
 */
@Data
public class MessagePacketBuilder {

    public static MessageBuilder buildBasicResponse(){
        return MessageBuilder.newInstance(buildBasic(MessageType.RESPONSE));
    }

    public static MessageBuilder buildBasicRequest(){
        return MessageBuilder.newInstance(buildBasic(MessageType.REQUEST));
    }

    public static MessageBuilder buildBasicPing(){
        return MessageBuilder.newInstance(buildBasic(MessageType.PING));
    }

    public static MessageBuilder buildBasicPong(){
        return MessageBuilder.newInstance(buildBasic(MessageType.PONG));
    }

    public static MessageBuilder buildBasicReportModuleId(){
        return MessageBuilder.newInstance(buildBasic(MessageType.REPORT_MODULE_ID));
    }

    private static DefaultMessagePacket buildBasic(MessageType messageType){
        DefaultMessagePacket packet = new DefaultMessagePacket();
        packet.setMagicNumber(ProtocolConstant.MAGIC_NUMBER);
        packet.setVersion(ProtocolConstant.VERSION);
        packet.setSerialNumber(SerialNumberUtils.INSTANCE.generateSerialNumber());
        packet.setMessageType(messageType);
        return packet;
    }

    public static class MessageBuilder {

        private DefaultMessagePacket packet;


        public MessageBuilder(DefaultMessagePacket packet) {
            this.packet = packet;
        }

        public MessageBuilder systemId(String moduleId){
            this.packet.setSystemId(moduleId);
            return this;
        }

        public MessageBuilder urlMapping(String mappingUrl){
            this.packet.setMappingUrl(mappingUrl);
            return this;
        }

        public MessageBuilder payload(String payload){
            this.packet.setPayload(payload);
            return this;
        }

        public MessageBuilder responseCode(ResponseCode responseCode){
            this.packet.setResponseCode(responseCode.getCode());
            this.packet.setResponseDesc(responseCode.getDesc());
            return this;
        }

        public MessageBuilder serialNumber(String serialNumber){
            this.packet.setSerialNumber(serialNumber);
            return this;
        }

        public MessageBuilder targetIp(String targetIp){
            this.packet.setTargetIp(targetIp);
            return this;
        }

        public static MessageBuilder newInstance(DefaultMessagePacket packet){
            return new MessageBuilder(packet);
        }

        public DefaultMessagePacket build(){
            return this.packet;
        }
    }
}
