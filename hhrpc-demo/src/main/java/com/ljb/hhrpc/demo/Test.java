package com.ljb.hhrpc.demo;

import com.ljb.hhrpc.RPCServer;

import java.io.IOException;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class Test {

    public static void main(String[] args) throws IOException {


        new Thread(
                () -> {
//                    ServiceCenter sc = new ServiceCenter(18888);
//
//                    sc.register(HelloService.class, HelloServiceImpl.class);
//                    try {
//                        sc.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    RPCServer rpcServer = new RPCServer("localhost", 18888, 10, 10);
                    rpcServer.service(HelloService.class, HelloServiceImpl.class);
                    try {
                        rpcServer.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        ).start();


//        HelloService helloService = RpcClient.getRemote(HelloService.class, new InetSocketAddress("localhost", 18888));
//        String ljb = helloService.sayHello("111");
//        System.out.println(ljb);
    }


}
