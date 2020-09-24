package com.github.zy.netty.common.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@Data
public abstract class BaseMessagePacket implements Serializable {

    /**
     * 魔数
     */
    private int magicNumber;

    /**
     * 版本号
     */
    private int version;

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 消息类型
     */
    private MessageType messageType;

    /**
     * 附件 - K-V形式
     */
    private Map<String, String> attachments = new HashMap<>();

    /**
     * 所属系统id
     */
    private String systemId;

    /**
     * 映射路径
     */
    private String mappingUrl;

    /**
     * 响应码
     */
    private Integer responseCode;

    /**
     * 响应描述
     */
    private String responseDesc;

    /**
     * 目标ip
     */
    private String targetIp;

    /**
     * 添加附件
     */
    public void addAttachment(String key, String value) {
        attachments.put(key, value);
    }

    /**
     * 基础包encode
     *
     * @param out out
     */
    public void encode(ByteBuf out) {
        // 魔数
        int magicNumber = getMagicNumber();
        out.writeInt(magicNumber);
        // 版本
        int version = getVersion();
        out.writeInt(version);
        // 流水号
        int serialNumberLength = getSerialNumber().length();
        out.writeInt(serialNumberLength);
        String serialNumber = getSerialNumber();
        out.writeCharSequence(serialNumber, ProtocolConstant.UTF_8);
        // 消息类型
        Byte messageTypee = getMessageType().getType();
        out.writeByte(messageTypee);
        // 附件size
        Map<String, String> attachments = getAttachments();
        out.writeInt(attachments.size());
        // 附件内容
        attachments.forEach((k, v) -> {
            out.writeInt(k.length());
            out.writeCharSequence(k, ProtocolConstant.UTF_8);
            out.writeInt(v.length());
            out.writeCharSequence(v, ProtocolConstant.UTF_8);
        });

        //只有请求类型的才去写入模块id以及映射
        if (MessageType.REQUEST.equals(getMessageType())) {
            if (!StringUtils.isEmpty(getMappingUrl())) {
                //url映射
                int urlMappingLength = getMappingUrl().length();
                out.writeInt(urlMappingLength);
                String urlMapping = getMappingUrl();
                out.writeCharSequence(urlMapping, ProtocolConstant.UTF_8);
            } else {
                out.writeInt(0);
            }
        } else if (MessageType.REPORT_MODULE_ID.equals(getMessageType())) {
            //系统ID
            if (!StringUtils.isEmpty(getSystemId())) {
                int moduleIdLength = getSystemId().length();
                out.writeInt(moduleIdLength);
                String moduleId = getSystemId();
                out.writeCharSequence(moduleId, ProtocolConstant.UTF_8);
            } else {
                out.writeInt(0);
            }

            //目标的ip
            if (!StringUtils.isEmpty(getTargetIp())) {
                int targetIpLength = getTargetIp().length();
                out.writeInt(targetIpLength);
                String targetIp = getTargetIp();
                out.writeCharSequence(targetIp, ProtocolConstant.UTF_8);
            } else {
                out.writeInt(0);
            }
        } else if (MessageType.RESPONSE.equals(getMessageType())) {
            Integer responseCode = getResponseCode();
            if (responseCode != null) {
                out.writeInt(responseCode);
            } else {
                out.writeInt(0);
            }

            //response message
            String responseDesc = getResponseDesc();
            if (!StringUtils.isEmpty(responseDesc)) {
                int responseDescLength = responseDesc.length();
                out.writeInt(responseDescLength);
                out.writeCharSequence(responseDesc, ProtocolConstant.UTF_8);
            } else {
                out.writeInt(0);
            }

        }
    }

    /**
     * 基础包decode
     *
     * @param in in
     */
    public void decode(ByteBuf in) {
        // 魔数
        int magicNumber = in.readInt();
        setMagicNumber(magicNumber);
        // 版本
        int version = in.readInt();
        setVersion(version);
        // 流水号
        int serialNumberLength = in.readInt();
        String serialNumber = in.readCharSequence(serialNumberLength, ProtocolConstant.UTF_8).toString();
        setSerialNumber(serialNumber);
        // 消息类型
        byte messageTypeByte = in.readByte();
        setMessageType(MessageType.fromValue(messageTypeByte));
        // 附件
        Map<String, String> attachments = new HashMap<>();
        setAttachments(attachments);
        int attachmentSize = in.readInt();
        if (attachmentSize > 0) {
            for (int i = 0; i < attachmentSize; i++) {
                int keyLength = in.readInt();
                String key = in.readCharSequence(keyLength, ProtocolConstant.UTF_8).toString();
                int valueLength = in.readInt();
                String value = in.readCharSequence(valueLength, ProtocolConstant.UTF_8).toString();
                attachments.put(key, value);
            }
        }

        //只有请求类型的才去写入模块id以及映射
        if (MessageType.REQUEST.equals(getMessageType())) {
            //映射
            int mappingUrlLength = in.readInt();

            if(mappingUrlLength != 0){
                String urlMapping = in.readCharSequence(mappingUrlLength, ProtocolConstant.UTF_8).toString();
                setMappingUrl(urlMapping);
            }

        } else if (MessageType.REPORT_MODULE_ID.equals(getMessageType())) {
            //系统ID
            int moduleIdLength = in.readInt();
            if(moduleIdLength != 0){
                String moduleId = in.readCharSequence(moduleIdLength, ProtocolConstant.UTF_8).toString();
                setSystemId(moduleId);
            }

            //目标ip
            int targetIpLength = in.readInt();
            if(targetIpLength != 0){
                String targetIp = in.readCharSequence(targetIpLength, ProtocolConstant.UTF_8).toString();
                setTargetIp(targetIp);
            }

        } else if (MessageType.RESPONSE.equals(getMessageType())) {
            int respCode = in.readInt();
            if(respCode != 0){
                setResponseCode(respCode);
            }
            //描述
            int responseDescLength = in.readInt();
            if(responseDescLength != 0){
                String responseDesc = in.readCharSequence(responseDescLength, ProtocolConstant.UTF_8).toString();
                setResponseDesc(responseDesc);
            }
        }
    }
}
