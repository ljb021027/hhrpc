package com.ljb.hhrpc.config;

import com.ljb.hhrpc.config.service.ServiceConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class HhrpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {

        registerBeanDefinitionParser("service", new HhrpcCustomBeanDefinitionParser(ServiceConfig.class, true));
    }
}
