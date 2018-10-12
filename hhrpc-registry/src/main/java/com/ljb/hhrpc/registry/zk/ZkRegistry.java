package com.ljb.hhrpc.registry.zk;

import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.registry.Registry;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public class ZkRegistry implements Registry {

    private final ZkClient zkClient;

    public ZkRegistry(String zkAddress) {
        zkClient = new ZkClient(zkAddress, 5000, 1000);
    }

    @Override
    public void register(URL url) {

    }

    @Override
    public void unRegister(URL url) {

    }

    @Override
    public URL discover(URL url) {
        return null;
    }
}
