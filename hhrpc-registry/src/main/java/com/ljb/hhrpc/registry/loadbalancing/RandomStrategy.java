package com.ljb.hhrpc.registry.loadbalancing;

import java.util.List;

/**
 * 随机
 *
 * @author ljb
 * @since 2018/10/13
 */
public class RandomStrategy implements Banlancing {


    @Override
    public <T> T getOne(List<T> list) {
        int index = (int) (Math.random() * list.size());
        return list.get(index);
    }
}
