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

    private final ZkClient zkClient;

    public ZkRegistry(String zkAddress) {
        if (zkAddress == "") {
            zkAddress = "localhost:2181";
        }
        zkClient = new ZkClient(zkAddress, 5000, 1000);
        boolean exists = zkClient.exists(ZkConstant.ROOT_PATH);
        if (!exists) {
            zkClient.createPersistent(ZkConstant.ROOT_PATH);
        }
    }

    @Override
    public void register(ServiceInfo info, URL url) {
        String consumerPath = ZkConstant.ROOT_PATH + ZkConstant.SEQ + info.getUniqueName() + ZkConstant.CONSUMERS_PATH;
        this.zkClient.createPersistent(consumerPath, true);
        this.zkClient.createEphemeral(consumerPath + ZkConstant.SEQ + url.getUniquePath(), url);
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
        String path = ZkConstant.ROOT_PATH + ZkConstant.SEQ + info.getUniqueName() + ZkConstant.CONSUMERS_PATH;
        List<String> children = this.zkClient.getChildren(path);
        return children.stream().map(s -> {
            return new URL(s);
        }).collect(Collectors.toList());
    }

}
