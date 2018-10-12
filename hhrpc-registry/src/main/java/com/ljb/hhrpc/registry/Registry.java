package com.ljb.hhrpc.registry;


import com.ljb.hhrpc.common.bean.URL;

/**
 * @author liujiabei
 * @since 2018/10/12
 */
public interface Registry {

    void register(URL url);

    void unRegister(URL url);

    URL discover(URL url);
}
