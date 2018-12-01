package com.ljb.hhrpc.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ljb
 * @since 2018/12/1
 */
public class Test {


    public static void main(String[] args) {
        //测试常规服务
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("bean.xml");
        context.start();
        System.out.println("consumer start");
//        HelloService demoService = context.getBean(HelloService.class);
        System.out.println("consumer");
//        System.out.println(demoService.sayHello("ljb"));
    }
}
