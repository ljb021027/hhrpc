package com.ljb.hhrpc.client;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiabei
 * @since 2018/9/30
 */
public class ResponseRegistry {
    private static Map<String, Class<?>> clazzes = new HashMap<>();

    public static void register(String type, Class<?> clazz) {
        clazzes.put(type, clazz);
    }

    public static Class<?> get(String type) {
        return clazzes.get(type);
    }
}
