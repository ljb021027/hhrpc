package com.ljb.hhrpc.config;

import java.io.Serializable;

/**
 * @author ljb
 * @since 2018/11/20
 */
public abstract class AbstractConfig implements Serializable {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
