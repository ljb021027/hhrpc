package com.ljb.hhrpc.client;

import com.ljb.hhrpc.client.handler.NettyClient;
import com.ljb.hhrpc.common.bean.RPCRequest;
import com.ljb.hhrpc.common.bean.RPCResponse;
import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.common.util.RequestId;
import com.ljb.hhrpc.registry.Registry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class RpcClient implements InvocationHandler {

    private Class<?> interfaceClass;

    public static Registry registry;

    public Object bind(Class<?> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setRequestId(RequestId.next());
        request.setClassName(interfaceClass.getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setArgs(args);

        URL discover = registry.discover(new ServiceInfo(interfaceClass.getName()));
        NettyClient nettyClient = new NettyClient(discover.getUniquePath());
        RPCResponse response = nettyClient.send(request);
        return response.getResult();
    }
}
