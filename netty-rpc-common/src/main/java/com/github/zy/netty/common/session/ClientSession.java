package com.github.zy.netty.common.session;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0 created by zy on 2020/6/10 11:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientSession extends Session {

    /**
     * 服务id,在配置文件中所展现的目标服务的id
     */
    private String serverId;
}
