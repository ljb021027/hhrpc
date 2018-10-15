package com.ljb.hhrpc.registry;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author ljb
 * @since 2018/10/13
 */
public class Test {

    public static void main(String[] args){
        ZkClient zkClient = new ZkClient("localhost:2181", 5000, 1000);
        zkClient.createEphemeral("/hhrpc");

    }
}
