package com.ljb.hhrpc.demo;

import com.ljb.hhrpc.client.RpcClient;

/**
 * @author liujiabei
 * @since 2018/9/30
 */
public class Test2 {

    public static void main(String[] args){
        HelloService helloService = RpcClient.getRemote(HelloService.class);
        String ljb = helloService.sayHello("111");
        System.out.println(ljb);
        String ljb2 = helloService.sayHello("222");
        System.out.println(ljb2);
    }
}
