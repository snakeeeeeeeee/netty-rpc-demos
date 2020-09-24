package com.github.zy.netty.common.utils;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * @version 1.0 created by zy on 2020/4/25 23:49
 */
public enum  ChannelHandlerContextUtil {
    INSTANCE;

    public String getIp(ChannelHandlerContext context){
        InetSocketAddress socketAddress = (InetSocketAddress) context.channel().remoteAddress();
        return socketAddress.getHostName();
    }

    public int getPort(ChannelHandlerContext context){
        InetSocketAddress socketAddress = (InetSocketAddress) context.channel().remoteAddress();
        return socketAddress.getPort();
    }
}
