package com.ljb.hhrpc.config.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ljb
 * @since 2018/11/25
 */
public class HhrpcApplicationContextInitializer implements ApplicationContextInitializer {

    @Autowired
    private ApplicationContext context;

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        configurableApplicationContext.addApplicationListener(new HhrpcApplicationListener());
    }
}
