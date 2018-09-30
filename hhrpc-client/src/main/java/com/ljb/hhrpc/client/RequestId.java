package com.ljb.hhrpc.client;

import java.util.UUID;

/**
 * @author liujiabei
 * @since 2018/9/30
 */
public class RequestId {

    public static String next() {
        return UUID.randomUUID().toString();
    }

}
