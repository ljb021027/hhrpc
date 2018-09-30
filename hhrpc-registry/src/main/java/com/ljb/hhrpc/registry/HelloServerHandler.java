package com.ljb.hhrpc.registry;

/**
 * @author liujiabei
 * @since 2018/9/29
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;

/**
 * 用于处理请求数据
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String s = msg.toString();
        try {
            byte[] bytes = s.getBytes("GBK");
            System.out.println(new String(bytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


//        // 如何符合约定，则调用本地方法，返回数据
//        if (msg.toString().startsWith(ClientBootstrap.providerName)) {
//            String result = new HelloServiceImpl()
//                    .hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
//            ctx.writeAndFlush(result);
//        }
    }
}

