package com.github.zy.netty.common.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0 created by zy on 2020/4/27 11:03
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {

    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 未找到
     */
    NOT_FOUND(404, "resource not found"),

    /**
     * 错误
     */
    ERROR(500, "service error"),

    /**
     * 超时
     */
    TIMEOUT(505, "red time out");


    private int code;

    private String desc;

    public static ResponseCode fromValue(int value) {
        for (ResponseCode code : ResponseCode.values()) {
            if (code.getCode() == value) {
                return code;
            }
        }
        throw new IllegalArgumentException(String.format("value = %s", value));
    }
}
