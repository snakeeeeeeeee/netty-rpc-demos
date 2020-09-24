package com.github.zy.netty.common.event;


import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0 created by zy on 2020/5/21 10:11
 */
@Data
@Builder
public class CleanSessionEventMessage {

    /**
     * 系统id
     */
    private String systemId;

    /**
     * ip
     */
    private String ip;

    /**
     * 会话id
     */
    private String sessionId;

}
