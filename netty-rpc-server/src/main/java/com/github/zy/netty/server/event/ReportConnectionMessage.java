package com.github.zy.netty.server.event;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @version 1.0 created by zy on 2020/5/28 12:33
 */
@Data
@Builder
public class ReportConnectionMessage {

    /**
     * 系统id
     */
    private String systemId;

    /**
     * ip
     */
    private String ip;

    /**
     * 当前环境
     */
    private String profile;
}
