package com.ljb.hhrpc.client;

import com.ljb.hhrpc.client.handler.NettyHandler;
import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.registry.RegistryFactory;

import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class RpcClient {

    public static <T> T getRemote(final Class<T> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                (proxy, method, args) -> {
                    RPCRequest request = new RPCRequest();
                    request.setRequestId(UUID.randomUUID().toString());
                    request.setClassName(clazz.getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setArgs(args);

                    URL discover = RegistryFactory.getRegistry().discover(new ServiceInfo(clazz.getName()));
                    NettyHandler nettyHandler = new NettyHandler(discover.getUniquePath());
                    RPCResponse response = nettyHandler.send(request);
                    return response.getResult();
                });

    }
}
