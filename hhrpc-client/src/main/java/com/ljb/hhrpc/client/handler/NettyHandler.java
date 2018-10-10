package com.ljb.hhrpc.client.handler;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.codes.RPCDecoder;
import com.ljb.hhrpc.common.codes.RPCEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liujiabei
 * @since 2018/10/8
 */
public class NettyHandler<T> implements InvocationHandler {

    private static ExecutorService executor = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static ClientCollector client;

    private final InetSocketAddress addr;

    private final Class<T> clazz;

    public NettyHandler(Class<T> clazzP, final InetSocketAddress inetSocketAddress) {
        addr = inetSocketAddress;
        clazz = clazzP;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (client == null) {
            initClient();
        }
        return executor.submit(client).get();
    }

    /**
     * 初始化客户端
     */
    private void initClient() {
        client = new ClientCollector();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new RPCEncoder(RPCRequest.class));
                        p.addLast(new RPCDecoder(RPCResponse.class));
                        p.addLast(client);
                    }
                });
        try {
            b.connect(addr).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
