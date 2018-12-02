package com.ljb.hhrpc.config.service;

import com.ljb.hhrpc.config.AbstractConfig;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class ServiceConfig extends AbstractConfig {

    private String interfaceName;

    private String implName;

    private int port;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getImplName() {
        return implName;
    }

    public void setImplName(String implName) {
        this.implName = implName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
