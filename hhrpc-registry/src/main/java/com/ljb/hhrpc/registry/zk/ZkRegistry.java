package com.ljb.hhrpc.registry.zk;

import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.registry.AbsRegistry;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public class ZkRegistry extends AbsRegistry {

    private static volatile ZkClient zkClient;

    private String createConsumersPath(String serviceName) {
        return ZkConstant.ROOT_PATH + ZkConstant.SEQ + serviceName + ZkConstant.CONSUMERS_PATH;
    }

    private String createProvidersPath(String serviceName) {
        return ZkConstant.ROOT_PATH + ZkConstant.SEQ + serviceName + ZkConstant.CONSUMERS_PATH;
    }

    public ZkRegistry(String zkAddress) {

        if (zkAddress == "") {
            addr = "localhost:2181";
        } else {
            addr = zkAddress;
        }
        if (zkClient == null) {
            synchronized (ZkRegistry.class) {
                if (zkClient == null) {
                    zkClient = new ZkClient(addr, 5000, 1000);
                }
            }
        }
        boolean exists = zkClient.exists(ZkConstant.ROOT_PATH);
        if (!exists) {
            zkClient.createPersistent(ZkConstant.ROOT_PATH);
        }
    }

    @Override
    public void register(ServiceInfo info, URL url) {
        String consumerPath = createProvidersPath(info.getUniqueName());
        this.zkClient.createPersistent(consumerPath, true);
        this.zkClient.createEphemeral(consumerPath + ZkConstant.SEQ + addr);
        this.zkClient.subscribeChildChanges(consumerPath, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> children) throws Exception {
                cleanUrlCache(info);
                putUrlCache(info, children.stream().map(s -> {
                    return new URL(s);
                }).collect(Collectors.toList()));
            }
        });
    }

    @Override
    public void unRegister(ServiceInfo info) {
//        this.zkClient.delete(ZkConstant.ROOT_PATH + ZkConstant.SEQ + info.getUniqueName());
    }

    @Override
    protected List<URL> initUrlCache(ServiceInfo info) {
        String consumerPath = createProvidersPath(info.getUniqueName());
        List<String> children = this.zkClient.getChildren(consumerPath);
        return children.stream().map(s -> {
            return new URL(s);
        }).collect(Collectors.toList());
    }

}
