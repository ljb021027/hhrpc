package com.ljb.hhrpc.client.handler;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.codes.RPCDecoder;
import com.ljb.hhrpc.common.codes.RPCEncoder;
import com.ljb.hhrpc.common.util.StringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liujiabei
 * @since 2018/10/8
 */
public class NettyHandler extends ChannelInboundHandlerAdapter {

    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private String addr;

    private Channel channel;

    private RPCResponse response;

    public static Object lock = new Object();

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("client channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client read msg");
        this.response = (RPCResponse) msg;
        synchronized (lock) {
            lock.notify();
        }
    }


    public NettyHandler(String addr) {

        Channel channel = channelMap.get(addr);
        if (channel == null) {
            this.addr = addr;
//            new Thread(() -> {
            try {
                initChannl();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            }).start();
        } else {

            this.channel = channel;
        }
    }

    private void initChannl() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建并初始化 Netty 客户端 Bootstrap 对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast(new RPCEncoder(RPCRequest.class)); // 编码 RPC 请求
                    pipeline.addLast(new RPCDecoder(RPCResponse.class)); // 解码 RPC 响应
                    pipeline.addLast(NettyHandler.this); // 处理 RPC 响应
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 连接 RPC 服务器
            ChannelFuture future = bootstrap.connect(StringUtil.getAddrByString(addr)).sync();
            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();
            this.channel = channel;
            channelMap.putIfAbsent(addr, channel);
//            channel.writeAndFlush(request).sync();
            channel.closeFuture();
            // 返回 RPC 响应对象
//            synchronized (lock){
//                lock.wait();
//            }

        } finally {
            group.shutdownGracefully();
        }
    }

    public RPCResponse send(RPCRequest request) throws Exception {
        System.out.println("send:++");
        channel.writeAndFlush(request);
        synchronized (lock) {
            lock.wait();
        }
        return response;

    }

}
