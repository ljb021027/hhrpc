package com.ljb.hhrpc.registry;

import com.ljb.hhrpc.registry.loadbalancing.RandomStrategy;
import com.ljb.hhrpc.registry.zk.ZkRegistryFactory;

/**
 * @author ljb
 * @since 2018/10/13
 */
public class RegistryFactory {

    public static Registry getRegistry(String addr){

        return ZkRegistryFactory.getRegistry(addr).setBanlancing(new RandomStrategy());
    }
}
