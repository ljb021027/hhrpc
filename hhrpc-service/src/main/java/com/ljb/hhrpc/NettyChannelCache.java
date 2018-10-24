package com.ljb.hhrpc;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujiabei
 * @since 2018/10/24
 */
public class NettyChannelCache {


    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();


    public static Channel getChannel(String inetHost, int inetPort) {

        return channelMap.get(inetHost + ":" + inetPort);
    }

    public static Channel addChannel(String inetHost, int inetPort, Channel channel) {

        return channelMap.put(inetHost + ":" + inetPort, channel);
    }

}
