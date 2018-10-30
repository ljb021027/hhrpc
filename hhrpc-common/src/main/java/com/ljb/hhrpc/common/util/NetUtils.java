package com.ljb.hhrpc.common.util;

import java.net.InetSocketAddress;

/**
 * @author liujiabei
 * @since 2018/10/25
 */
public class NetUtils {

    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }
}
