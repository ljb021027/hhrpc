package com.ljb.hhrpc.common.bean;

import java.util.Objects;

/**
 * @author ljb
 * @since 2018/10/15
 */
public class ServiceInfo {
    private String serviceName;

    public ServiceInfo(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUniqueName() {
        return serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInfo that = (ServiceInfo) o;
        return Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName);
    }
}
