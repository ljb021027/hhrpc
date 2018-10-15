package com.ljb.hhrpc.config.service;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class ServiceConfig {

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
