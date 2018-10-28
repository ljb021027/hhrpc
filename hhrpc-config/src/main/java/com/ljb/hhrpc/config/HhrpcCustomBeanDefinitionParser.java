package com.ljb.hhrpc.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Field;

/**
 * @author liujiabei
 * @since 2018/10/16
 */
public class HhrpcCustomBeanDefinitionParser implements BeanDefinitionParser {
    private final Class<?> beanClass;
    private final boolean required;

    public HhrpcCustomBeanDefinitionParser(Class<?> beanClass, boolean required) {
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
     * @param element       xml配置文件中的配置项
     * @param parserContext spring上下文
     * @param beanClass     自定义schema标签对应的java bean文件
     * @param required      是否必须（dubbo中有些配置不是必须的）
     * @return
     */
    private static BeanDefinition parse(Element element, ParserContext parserContext, Class<?> beanClass, boolean required) {

        // 把bean封装成RootBeanDefinition对象，RootBeanDefinition继承了BeanDefinition
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);// 设置这个bean是否延迟初始化，false-启动时就初始化

        String id = element.getAttribute("id");
        if (id.isEmpty()) {
            String interfaceName = element.getAttribute("interfaceName");
            id = interfaceName;
        }

        for (Field field : beanClass.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            String value = element.getAttribute(name);
            beanDefinition.getPropertyValues().addPropertyValue(name, value);
        }


//        if (!StringUtils.isEmpty(id)) {
//            // 重复spring bean校验
//            if (parserContext.getRegistry().containsBeanDefinition(id)) {
//                throw new IllegalStateException("Duplicate spring bean id " + id);
//            }
//        } else {
//            throw new IllegalStateException("spring bean id can not be null");
//        }

        // 把RootBeanDefinition bean对象注册到spring容器中
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }
}
