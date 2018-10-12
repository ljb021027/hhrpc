package com.ljb.hhrpc.common.bean;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public class URL {

    private String serviceName;

    private String host;

    private int port;

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
