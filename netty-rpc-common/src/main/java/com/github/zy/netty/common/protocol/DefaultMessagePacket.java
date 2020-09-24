package com.github.zy.netty.common.protocol;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0 created by zy on 2020/4/25 20:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultMessagePacket extends BaseMessagePacket {

    /**
     * 消息载荷 - 暂定为字符串
     */
    private String payload;

}
