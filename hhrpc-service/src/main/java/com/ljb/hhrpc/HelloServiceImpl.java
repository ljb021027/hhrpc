package com.ljb.hhrpc;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
