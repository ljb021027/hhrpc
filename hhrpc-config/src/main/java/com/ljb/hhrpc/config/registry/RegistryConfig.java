package com.ljb.hhrpc.config.registry;

import com.ljb.hhrpc.config.AbstractConfig;

/**
 * @author liujiabei
 * @since 2018/10/15
 */
public class RegistryConfig extends AbstractConfig {

    private String type;

    private String address;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
