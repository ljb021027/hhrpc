package com.ljb.hhrpc.config;

import com.ljb.hhrpc.config.client.ClientConfig;
import com.ljb.hhrpc.config.registry.RegistryConfig;
import com.ljb.hhrpc.config.service.ServiceConfig;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class HhrpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {

        registerBeanDefinitionParser("registry", new HhrpcCustomBeanDefinitionParser(RegistryConfig.class, true));
        registerBeanDefinitionParser("service", new HhrpcCustomBeanDefinitionParser(ServiceConfig.class, true));
        registerBeanDefinitionParser("client", new HhrpcCustomBeanDefinitionParser(ClientConfig.class, true));

    }
}
