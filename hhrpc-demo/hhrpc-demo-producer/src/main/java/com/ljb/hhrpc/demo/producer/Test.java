package com.ljb.hhrpc.demo.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ljb
 * @since 2018/12/2
 */
public class Test {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("bean.xml");
        context.start();
        synchronized (Test.class){
            try {
                Test.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
