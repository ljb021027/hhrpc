package com.ljb.hhrpc.demo.consumer;

import com.ljb.hhrpc.demo.api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author ljb
 * @since 2018/12/1
 */
@Controller
public class TessController {
    @Autowired
    private HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(String name) {
        return helloService.sayHello(name);
    }

    @ResponseBody
    @RequestMapping("/hello2")
    public String hello2(String s) {
        return "2222";
    }
}
