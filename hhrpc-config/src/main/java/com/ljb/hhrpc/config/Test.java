package com.ljb.hhrpc.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class Test {
    
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("hhrpc.xml");
    }
}
