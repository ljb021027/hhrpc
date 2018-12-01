package com.ljb.hhrpc.demo.api;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        System.out.println("接收到参数:" + name);
        return "hello" + name;
    }
}
