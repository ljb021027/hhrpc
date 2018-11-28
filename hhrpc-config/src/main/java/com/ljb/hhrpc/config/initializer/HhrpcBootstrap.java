package com.ljb.hhrpc.config.initializer;

import com.ljb.hhrpc.RPCServer;
import com.ljb.hhrpc.config.registry.RegistryConfig;
import com.ljb.hhrpc.config.service.ServiceConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ljb
 * @since 2018/11/25
 */
@Component
public class HhrpcBootstrap implements ApplicationContextAware {

    private static ApplicationContext context;

    public void start() {
            RegistryConfig registryConfig = context.getBean(RegistryConfig.class);
            String[] split = registryConfig.getAddress().split(":");

            RPCServer rpcServer = new RPCServer(split[0], Integer.parseInt(split[1]), 10, 10);
            Map<String, ServiceConfig> serviceConfigMap = context.getBeansOfType(ServiceConfig.class);
            serviceConfigMap.forEach((k,v)->{
                try {
                    rpcServer.service(Class.forName(v.getInterfaceName()), Class.forName(v.getImplName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            });
    }

    public void stop() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
