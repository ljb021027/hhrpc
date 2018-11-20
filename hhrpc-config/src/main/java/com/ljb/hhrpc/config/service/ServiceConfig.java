package com.ljb.hhrpc.config.service;

import com.ljb.hhrpc.config.AbstractConfig;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class ServiceConfig extends AbstractConfig {

    private String interfaceName;

    private String implName;

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
}
