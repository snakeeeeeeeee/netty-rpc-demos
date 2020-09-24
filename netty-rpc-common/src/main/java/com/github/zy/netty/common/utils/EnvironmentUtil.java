package com.github.zy.netty.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EnvironmentUtil {
    protected static String hostName = getHostNameCore();

    /**
     * 获取本机名称
     *
     * @return
     */
    private static String getHostNameCore() {
        if (System.getenv("COMPUTERNAME") != null) {
            return System.getenv("COMPUTERNAME");
        } else {
            return getHostNameForLiunx();
        }
    }

    /**
     * 获取本机名称
     *
     * @return
     */
    public static String getHostName() {
        return hostName;
    }

    private static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }
}
