package com.ljb.hhrpc.config.initializer;

import com.ljb.hhrpc.RPCServer;
import com.ljb.hhrpc.client.MyProxyFactory;
import com.ljb.hhrpc.client.RpcClient;
import com.ljb.hhrpc.config.client.ClientConfig;
import com.ljb.hhrpc.config.registry.RegistryConfig;
import com.ljb.hhrpc.config.service.ServiceConfig;
import com.ljb.hhrpc.registry.Registry;
import com.ljb.hhrpc.registry.RegistryFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @author ljb
 * @since 2018/11/25
 */
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class HhrpcBootstrap implements ApplicationContextAware {

    private static ApplicationContext context;

    public void start() {
        RegistryConfig registryConfig = context.getBean(RegistryConfig.class);

        //注册中心
        Registry registry = RegistryFactory.getRegistry(registryConfig.getAddress());
        //服务端
        RPCServer rpcServer = new RPCServer(registry, 10, 10);
        Map<String, ServiceConfig> serviceConfigMap = context.getBeansOfType(ServiceConfig.class);
        if (serviceConfigMap != null && !serviceConfigMap.isEmpty()) {

            serviceConfigMap.forEach((k, v) -> {
                try {
                    rpcServer.service(Class.forName(v.getInterfaceName()), Class.forName(v.getImplName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
        //客户端
        RpcClient.registry = registry;
        Map<String, ClientConfig> clientConfigMap = context.getBeansOfType(ClientConfig.class);
        if (clientConfigMap != null && !clientConfigMap.isEmpty()) {
            ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) configurableApplicationContext;
            clientConfigMap.forEach((k, v) -> {
                try {
                    Class<?> interfaceClass = Class.forName(v.getInterfaceName());
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(interfaceClass);
                    GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
                    definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
                    definition.setBeanClass(MyProxyFactory.class);
                    definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                    beanDefinitionRegistry.registerBeanDefinition(v.getId(), definition);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });
        }


    }

    public void stop() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
