package com.ljb.hhrpc.registry;

import com.ljb.hhrpc.common.bean.ServiceInfo;
import com.ljb.hhrpc.common.bean.URL;
import com.ljb.hhrpc.registry.loadbalancing.Banlancing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ljb
 * @since 2018/10/13
 */
public abstract class AbsRegistry implements Registry {

    protected String addr;

    protected Map<ServiceInfo, List<URL>> urlCache = new ConcurrentHashMap<>();

    protected abstract List<URL> initUrlCache(ServiceInfo info);

    public void putUrlCache(ServiceInfo info, List<URL> urls) {
        this.urlCache.putIfAbsent(info, urls);
    }

    public void cleanUrlCache(ServiceInfo info) {
        urlCache.remove(info);
    }

    @Override
    public URL discover(ServiceInfo info) {
        List<URL> urls = this.urlCache.get(info);
        if (urls == null || urls.isEmpty()) {
            urls = initUrlCache(info);
            putUrlCache(info, urls);
        }
        return this.banlancing.getOne(urls);
    }

    @Override
    public String getAddr() {
        return addr;
    }

    private Banlancing banlancing;

    public Registry setBanlancing(Banlancing banlancing) {
        this.banlancing = banlancing;
        return this;
    }


}
