package com.ljb.hhrpc.client;

import com.ljb.hhrpc.client.handler.NettyHandler;
import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class RpcClient {

    public static <T> T getRemote(Class<T> clazz, final InetSocketAddress addr) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        RPCRequest request = new RPCRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setClassName(clazz.getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setArgs(args);
                        NettyHandler nettyHandler = new NettyHandler(addr);
                        RPCResponse response = nettyHandler.send(request);
                        return response.getResult();
                    }
                });

    }
}
