package com.github.zy.netty.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @version 1.0 created by zy on 2020/4/23 16:30
 */
@Data
@ConfigurationProperties(prefix = "netty-rpc.client", ignoreInvalidFields = true)
public class RpcClientConfigProperties {

//    /**
//     * 默认的调用等待超时时间 单位: s
//     */
//    private int syncTimeout = 30;
//
//    /**
//     * 客户端系统的id标识
//     */
//    private String systemId;
//
//    /**
//     * 客户端配置map
//     */
//    private Map<String, RpcClientProperties> connections;

    private RpcClientProperties params;

}
