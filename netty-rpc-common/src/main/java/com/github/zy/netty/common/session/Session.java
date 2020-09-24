package com.github.zy.netty.common.session;

import com.github.zy.netty.common.constants.Profile;
import io.netty.channel.Channel;
import lombok.Data;

/**
 * @version 1.0 created by zy on 2020/4/26 14:22
 */
@Data
public class Session {

    /**
     * 唯一标识
     */
    private String id;

    /**
     * netty channel引用
     */
    private Channel channel;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private int port;

    /**
     * 环境 开发, 测试, 灰度
     */
    private Profile profile;

    /**
     * 类型,用于区别客户端还是服务端的session
     */
    private SessionType sessionType;


}
