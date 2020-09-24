package com.github.zy.netty.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {
    private IPUtil() {
    }

    public static String getAddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException var1) {
            return "0.0.0.0";
        }
    }
}
