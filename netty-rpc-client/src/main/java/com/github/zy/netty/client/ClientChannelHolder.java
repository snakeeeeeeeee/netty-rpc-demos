package com.github.zy.netty.client;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ClientChannelHolder {

    private ClientChannelHolder() {
    }

    /**
     * 存储客户端与服务端的会话, key为yml文件定义的key 格式, value为channel
     */
    public static final ConcurrentHashMap<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();
}
