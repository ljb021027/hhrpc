package com.ljb.hhrpc.registry.msg;

/**
 * @author liujiabei
 * @since 2018/9/29
 */
public class MessageOutput {

    private String requestId;
    private String type;
    private Object payload;

    public MessageOutput(String requestId, String type, Object payload) {
        this.requestId = requestId;
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return this.type;
    }

    public String getRequestId() {
        return requestId;
    }

    public Object getPayload() {
        return payload;
    }

}
