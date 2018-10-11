package com.ljb.hhrpc.client.handler;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.codes.RPCDecoder;
import com.ljb.hhrpc.common.codes.RPCEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author liujiabei
 * @since 2018/10/8
 */
public class NettyHandler extends SimpleChannelInboundHandler<RPCResponse> {

    private final InetSocketAddress addr;

    private RPCResponse response;


    public Object lock = new Object();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, RPCResponse response) throws Exception {
        this.response = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public NettyHandler(final InetSocketAddress inetSocketAddress) {
        addr = inetSocketAddress;
    }

    public RPCResponse send(RPCRequest request) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建并初始化 Netty 客户端 Bootstrap 对象
            Bootstrap bootstrap = new Bootstrap();
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
            ChannelFuture future = bootstrap.connect(addr).sync();
            // 写入 RPC 请求数据并关闭连接
            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            // 返回 RPC 响应对象
//            synchronized (lock){
//                lock.wait();
//            }
            return response;
        } finally {
            group.shutdownGracefully();
        }
    }

}
