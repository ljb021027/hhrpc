package com.ljb.hhrpc.config.client;

import com.ljb.hhrpc.config.AbstractConfig;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class ClientConfig extends AbstractConfig {

    private String interfaceName;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }
}

