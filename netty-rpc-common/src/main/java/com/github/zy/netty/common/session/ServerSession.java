package com.github.zy.netty.common.session;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务端session,存储了客户端相关的会话信息
 * @version 1.0 created by zy on 2020/6/10 10:26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerSession extends Session {


    /**
     * 所属系统id
     */
    private String systemId;
}
