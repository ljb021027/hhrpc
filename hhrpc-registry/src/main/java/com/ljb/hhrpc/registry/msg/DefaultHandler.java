package com.ljb.hhrpc.registry.msg;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
// 找不到类型的消息统一使用默认处理器处理
public class DefaultHandler implements IMessageHandler<MessageInput> {

    @Override
    public void handle(ChannelHandlerContext ctx, String requesetId, MessageInput input) {
        System.out.println("unrecognized message type=" + input.getType() + " comes");
    }

}
