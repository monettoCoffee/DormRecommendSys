package com.bean;

import com.alibaba.fastjson.JSON;

/**
 * @author monetto
 */
public class RequestResult {
    private int serviceCode;
    private String msg;
    private Object result;

    public RequestResult(int serviceCode, String msg, Object object) {
        this.serviceCode = serviceCode;
        this.msg = msg;
        this.result = object;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public int getServiceCode() {
        return serviceCode;
    }

    public RequestResult setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RequestResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RequestResult setResult(Object result) {
        this.result = result;
        return this;
    }

    public static RequestResult success() {
        return new RequestResult(200, "Success", null);
    }
}
