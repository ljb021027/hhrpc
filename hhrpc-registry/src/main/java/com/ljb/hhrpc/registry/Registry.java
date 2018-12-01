package com.ljb.hhrpc.registry;


import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public interface Registry {

    void register(ServiceInfo info, URL url);

    void unRegister(ServiceInfo url);

    URL discover(ServiceInfo info);

    String getAddr();
}
