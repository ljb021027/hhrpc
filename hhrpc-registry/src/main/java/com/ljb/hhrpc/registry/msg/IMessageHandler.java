package com.ljb.hhrpc.registry.msg;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public interface IMessageHandler<T> {

    void handle(ChannelHandlerContext ctx, String requestId, T message);

}