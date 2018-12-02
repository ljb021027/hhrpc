package com.ljb.hhrpc.config.initializer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author ljb
 * @since 2018/11/25
 */
public class HhrpcApplicationListener implements ApplicationListener<ApplicationEvent> {

    private HhrpcBootstrap hhrpcBootstrap;

    public HhrpcApplicationListener() {
        hhrpcBootstrap = new HhrpcBootstrap();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            hhrpcBootstrap.start();
        } else if (applicationEvent instanceof ContextClosedEvent) {
            hhrpcBootstrap.stop();
        }
    }
}
