package com.ljb.hhrpc.registry.zk;

/**
 * @author ljb
 * @since 2018/10/13
 */
public class ZkRegistryFactory {

    public static ZkRegistry getRegistry() {
        return new ZkRegistry("47.105.117.164:2181");
    }
}
