package com.ljb.hhrpc.config.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ljb
 * @since 2018/11/25
 */
public class HhrpcApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        configurableApplicationContext.addApplicationListener(new HhrpcApplicationListener());
    }
}
