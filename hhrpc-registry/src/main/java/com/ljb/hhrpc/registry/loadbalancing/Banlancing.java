package com.ljb.hhrpc.registry.loadbalancing;

import java.util.List;

/**
 * @author ljb
 * @since 2018/10/13
 */
public interface Banlancing {

    <T> T getOne(List<T> list);
}
