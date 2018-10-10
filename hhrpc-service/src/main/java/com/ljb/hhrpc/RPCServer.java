package com.ljb.hhrpc;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.codes.RPCDecoder;
import com.ljb.hhrpc.common.codes.RPCEncoder;
import com.ljb.hhrpc.msg.MessageCollector;
import com.ljb.hhrpc.msg.MessageRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class RPCServer {

    private String ip;
    private int port;
    private int ioThreads; // 用来处理网络流的读写线程
    private int workerThreads; // 用于业务处理的计算线程

    public RPCServer(String ip, int port, int ioThreads, int workerThreads) {
        this.ip = ip;
        this.port = port;
        this.ioThreads = ioThreads;
        this.workerThreads = workerThreads;
    }

    private ServerBootstrap bootstrap;
    private EventLoopGroup group;
    private MessageCollector collector;
    private Channel serverChannel;

    // 注册服务的快捷方式
    public RPCServer service(Class serviceInterface, Class<?> reqClass) {
        MessageRegistry.register(serviceInterface.getName(), reqClass);
        return this;
    }

    // 启动RPC服务
    public void start() throws InterruptedException {
        bootstrap = new ServerBootstrap();
        group = new NioEventLoopGroup(ioThreads);
        bootstrap.group(group);
        collector = new MessageCollector(workerThreads);
        bootstrap.channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipe = ch.pipeline();
                // 如果客户端60秒没有任何请求，就关闭客户端链接
                pipe.addLast(new ReadTimeoutHandler(60));
                pipe.addLast(new RPCEncoder(RPCResponse.class));
                pipe.addLast(new RPCDecoder(RPCRequest.class));
//                // 挂上解码器
//                pipe.addLast(new MessageDecoder());
//                // 挂上编码器
//                pipe.addLast(new MessageEncoder());
                // 将业务处理器放在最后
                pipe.addLast(collector);
            }
        });
//        bootstrap.option(ChannelOption.SO_BACKLOG, 100)  // 客户端套件字接受队列大小
//                .option(ChannelOption.SO_REUSEADDR, true) // reuse addr，避免端口冲突
//                .option(ChannelOption.TCP_NODELAY, true) // 关闭小流合并，保证消息的及时性
//                .childOption(ChannelOption.SO_KEEPALIVE, true); // 长时间没动静的链接自动关闭
        ChannelFuture sync = bootstrap.bind(this.ip, this.port).sync();
        sync.channel().closeFuture().sync();
//        serverChannel = bootstrap.bind(this.ip, this.port).channel();
        System.out.printf("server started @ %s:%d\n", ip, port);
    }

    public void stop() {
        // 先关闭服务端套件字
        serverChannel.close();
        // 再斩断消息来源，停止io线程池
        group.shutdownGracefully();
        // 最后停止业务线程
        collector.closeGracefully();
    }

}
