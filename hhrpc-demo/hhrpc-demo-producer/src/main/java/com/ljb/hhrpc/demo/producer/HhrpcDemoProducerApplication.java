package com.ljb.hhrpc.demo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = { "classpath:bean.xml" })
public class HhrpcDemoProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhrpcDemoProducerApplication.class, args);
    }
}
