package com.ljb.hhrpc.demo.consumer;

import com.ljb.hhrpc.demo.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ljb
 * @since 2018/12/1
 */
@Controller
@RequestMapping("/test")
public class TessController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(String s) {
        return helloService.sayHello(s);
    }
}
