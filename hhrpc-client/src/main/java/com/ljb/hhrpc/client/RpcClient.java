package com.ljb.hhrpc.client;

import com.ljb.hhrpc.client.handler.NettyClient;
import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.common.util.RequestId;
import com.ljb.hhrpc.registry.RegistryFactory;

import java.lang.reflect.Proxy;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class RpcClient {

    public static <T> T getRemote(final Class<T> clazz) {

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                (proxy, method, args) -> {
                    RPCRequest request = new RPCRequest();
                    request.setRequestId(RequestId.next());
                    request.setClassName(clazz.getName());
                    request.setMethodName(method.getName());
                    request.setParameterTypes(method.getParameterTypes());
                    request.setArgs(args);

                    URL discover = RegistryFactory.getRegistry().discover(new ServiceInfo(clazz.getName()));
                    NettyClient nettyClient = new NettyClient(discover.getUniquePath());
                    RPCResponse response = nettyClient.send(request);
                    return response.getResult();
                });

    }
}
