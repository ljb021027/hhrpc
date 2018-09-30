package com.ljb.hhrpc.registry;

import com.ljb.hhrpc.registry.msg.IMessageHandler;
import com.ljb.hhrpc.registry.msg.MessageOutput;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class HelloHandler implements IMessageHandler<String> {
    @Override
    public void handle(ChannelHandlerContext ctx, String requestId, String message) {

        ctx.writeAndFlush(new MessageOutput(requestId, "hello", message));
    }
}
