package com.ljb.hhrpc.config;

import com.ljb.hhrpc.config.service.ServiceConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class Test {
    
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("hhrpc.xml");
        ServiceConfig bean = (ServiceConfig)context.getBean("com.11.11");
        System.out.println(bean.getImplName());
        System.out.println(bean.getInterfaceName());
    }
}
