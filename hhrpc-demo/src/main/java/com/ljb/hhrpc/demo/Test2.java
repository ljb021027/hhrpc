package com.ljb.hhrpc.demo;

import com.ljb.hhrpc.client.RpcClient;

import java.net.InetSocketAddress;

/**
 * @author liujiabei
 * @since 2018/9/30
 */
public class Test2 {

    public static void main(String[] args){
        HelloService helloService = RpcClient.getRemote(HelloService.class, new InetSocketAddress("localhost", 18888));
        String ljb = helloService.sayHello("111");
        System.out.println(ljb);
    }
}
