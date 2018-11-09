package com.ljb.hhrpc.common.util;

import java.net.InetSocketAddress;

/**
 * @author ljb
 * @since 2018/10/15
 */
public class StringUtil {

    public static InetSocketAddress getAddrByString(String addr) {
        String[] split = addr.split(":");
        return new InetSocketAddress(split[0], Integer.parseInt(split[1]));

    }

}
