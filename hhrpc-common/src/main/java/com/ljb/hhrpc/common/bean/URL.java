package com.ljb.hhrpc.common.bean;

import java.io.Serializable;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public class URL implements Serializable {

    private String host;

    private int port;

    public URL(String address) {
        String[] split = address.split(":");
        this.host = split[0];
        this.port = Integer.parseInt(split[1]);
    }

    public URL(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getUniquePath() {
        return this.host + ":" + this.port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
