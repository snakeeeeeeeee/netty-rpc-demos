package com.github.zy.netty.common.protocol;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum MessageType {

    /**
     * 请求
     */
    REQUEST((byte) 1),

    /**
     * 响应
     */
    RESPONSE((byte) 2),

    /**
     * PING
     */
    PING((byte) 3),

    /**
     * PONG
     */
    PONG((byte) 4),

    /**
     * NULL
     */
    NULL((byte) 5),

    /**
     * 上报moduleId
     */
    REPORT_MODULE_ID((byte) 6);

    @Getter
    private final Byte type;

    public static MessageType fromValue(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.getType() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("value = %s", value));
    }
}
