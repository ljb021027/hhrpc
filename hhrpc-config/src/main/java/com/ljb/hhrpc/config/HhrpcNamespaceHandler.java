package com.ljb.hhrpc.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class HhrpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("dateformat", new SimpleDateFormatBeanDefinitionParser());

        registerBeanDefinitionParser("dateformat", new SimpleDateFormatBeanDefinitionParser());

    }
}
