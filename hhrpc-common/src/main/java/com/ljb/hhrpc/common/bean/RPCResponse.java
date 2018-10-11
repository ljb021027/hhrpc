package com.ljb.hhrpc.common.bean;

import java.io.Serializable;

/**
 * @author liujiabei
 * @since 2018/10/9
 */
public class RPCResponse implements Serializable {

    private String requestId;
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
