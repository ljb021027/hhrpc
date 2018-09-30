package com.ljb.hhrpc.registry.msg;

import com.alibaba.fastjson.JSON;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class MessageInput {
    private String type;
    private String requestId;
    private String payload;

    public MessageInput(String type, String requestId, String payload) {
        this.type = type;
        this.requestId = requestId;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String getRequestId() {
        return requestId;
    }

    // 因为我们想直接拿到对象，所以要提供对象的类型参数
    public <T> T getPayload(Class<T> clazz) {
        if (payload == null) {
            return null;
        }
        return JSON.parseObject(payload, clazz);
    }

}


