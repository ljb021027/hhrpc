package com.ljb.hhrpc;

import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.util.NetUtils;
import com.ljb.hhrpc.registry.MessageRegistry;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
@ChannelHandler.Sharable
public class NettyServer extends ChannelInboundHandlerAdapter {
    // 业务线程池
    private ThreadPoolExecutor executor;

    public NettyServer(int workerThreads) {
        // 业务队列最大1000，避免堆积
        // 如果子线程处理不过来，io线程也会加入处理业务逻辑(callerRunsPolicy)
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1000);
        // 给业务线程命名
        ThreadFactory factory = new ThreadFactory() {

            AtomicInteger seq = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("rpc-" + seq.getAndIncrement());
                return t;
            }

        };
        // 闲置时间超过30秒的线程自动销毁
        this.executor = new ThreadPoolExecutor(1, workerThreads, 30, TimeUnit.SECONDS, queue, factory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void closeGracefully() {
        // 优雅一点关闭，先通知，再等待，最后强制关闭
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        this.executor.shutdownNow();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 客户端来了一个新链接
        System.out.println("connection comes");
        NettyChannelCache.addChannel(NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()), ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 客户端走了一个
        System.out.println("connection leaves");
        NettyChannelCache.removeChannel(NetUtils.toAddressString((InetSocketAddress) ctx.channel().remoteAddress()));
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("read a message"+ctx.toString());
        // 用业务线程池处理消息
        this.executor.execute(() -> {
            try {
                this.handleMessage(ctx, msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleMessage(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCRequest request = (RPCRequest) msg;
        String serviceName = request.getClassName();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] arguments = request.getArgs();
        Class serviceClass = MessageRegistry.get(serviceName);
        if (serviceClass == null) {
            throw new ClassNotFoundException(serviceName + " not found");
        }
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        Object result = method.invoke(serviceClass.newInstance(), arguments);
        RPCResponse response = new RPCResponse();
        response.setResult(result);
        response.setRequestId(request.getRequestId());
        ctx.writeAndFlush(response);
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
//        ctx.close();
    }

}
