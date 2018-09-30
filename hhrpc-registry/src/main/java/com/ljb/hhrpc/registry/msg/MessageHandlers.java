package com.ljb.hhrpc.registry.msg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class MessageHandlers {

    private static Map<String, IMessageHandler<?>> handlers = new HashMap<>();
    public static DefaultHandler defaultHandler = new DefaultHandler();

    public static void register(String type, IMessageHandler<?> handler) {
        handlers.put(type, handler);
    }

    public static IMessageHandler<?> get(String type) {
        IMessageHandler<?> handler = handlers.get(type);
        return handler;
    }

}