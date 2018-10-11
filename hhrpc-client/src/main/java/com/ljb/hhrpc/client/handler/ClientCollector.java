package com.ljb.hhrpc.client.handler;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
@ChannelHandler.Sharable
public class ClientCollector extends ChannelInboundHandlerAdapter implements Callable {

    private RPCRequest request;
    private RPCResponse response;

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
        // 客户端来了一个新链接
        System.out.println("connection comes");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端走了一个
        System.out.println("connection leaves");
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read a message");
        response = (RPCResponse) msg;
        request.notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 此处可能因为客户端机器突发重启
        // 也可能是客户端链接闲置时间超时，后面的ReadTimeoutHandler抛出来的异常
        // 也可能是消息协议错误，序列化异常
        // etc.
        // 不管它，链接统统关闭，反正客户端具备重连机制
        System.out.println("connection error");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public Object call() throws Exception {
        context.writeAndFlush(request);
        request.wait();
        return response;
    }

    void setRequest(RPCRequest request) {
        this.request = request;
    }
}
