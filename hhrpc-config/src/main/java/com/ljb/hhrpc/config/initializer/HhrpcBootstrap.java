package com.ljb.hhrpc.config.initializer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ljb
 * @since 2018/11/25
 */
@Component
public class HhrpcBootstrap implements ApplicationContextAware {

    private static ApplicationContext context;

    public void start() {
//        RegistryConfig registryConfig = context.getBean(RegistryConfig.class);
//        //注册中心
//        Registry registry = RegistryFactory.getRegistry(registryConfig.getAddress());
//        //服务端
//        RPCServer rpcServer = new RPCServer(registry, 10, 10);
//        Map<String, ServiceConfig> serviceConfigMap = context.getBeansOfType(ServiceConfig.class);
//        if (serviceConfigMap != null && !serviceConfigMap.isEmpty()) {
//
//            serviceConfigMap.forEach((k, v) -> {
//                try {
//                    rpcServer.service(Class.forName(v.getInterfaceName()), Class.forName(v.getImplName()));
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        //客户端
//        RpcClient.registry = registry;
//        Map<String, ClientConfig> clientConfigMap = context.getBeansOfType(ClientConfig.class);
//        if (clientConfigMap != null && !clientConfigMap.isEmpty()) {
//            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
//            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) configurableApplicationContext;
//            clientConfigMap.forEach((k, v) -> {
//                try {
//                    Class<?> interfaceClass = Class.forName(v.getInterfaceName());
//                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(interfaceClass);
//                    GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//                    definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
//                    definition.setBeanClass(MyProxyFactory.class);
//                    definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//                    beanDefinitionRegistry.registerBeanDefinition(v.getId(), definition);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
    }

    public void stop() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
