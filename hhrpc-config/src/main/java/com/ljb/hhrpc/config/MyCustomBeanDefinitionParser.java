package com.ljb.hhrpc.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 自定义schema标签的解析类
 *
 * @author Administrator
 *
 */
public class MyCustomBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;
    private final boolean required;

    public MyCustomBeanDefinitionParser(Class<?> beanClass, boolean required) {
        this.beanClass = beanClass;
        this.required = required;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass, required);
    }

    /**
     * 解析自定义schema文件，并出注册到spring容器中
     *
     * @param element
     *            xml配置文件中的配置项
     * @param parserContext
     *            spring上下文
     * @param beanClass
     *            自定义schema标签对应的java bean文件
     * @param required
     *            是否必须（dubbo中有些配置不是必须的）
     * @return
     */
    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {
        String id = element.getAttribute("id");
        String timeout = element.getAttribute("timeout");
        String retries = element.getAttribute("retries");
        String actives = element.getAttribute("actives");

        if (!StringUtils.isEmpty(id)) {
            // 重复spring bean校验
            if (parserContext.getRegistry().containsBeanDefinition(id)) {
                throw new IllegalStateException("Duplicate spring bean id " + id);
            }
        } else {
            throw new IllegalStateException("spring bean id can not be null");
        }

        // 把bean封装成RootBeanDefinition对象，RootBeanDefinition继承了BeanDefinition
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);// 设置这个bean是否延迟初始化，false-启动时就初始化
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        if (!StringUtils.isEmpty(timeout)) {
            beanDefinition.getPropertyValues().addPropertyValue("timeout", timeout);
        }
        if (!StringUtils.isEmpty(retries)) {
            beanDefinition.getPropertyValues().addPropertyValue("retries", retries);
        }
        if (!StringUtils.isEmpty(actives)) {
            beanDefinition.getPropertyValues().addPropertyValue("actives", actives);
        }

        // 把RootBeanDefinition bean对象注册到spring容器中
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }
}
