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

    public void setServiceCode(int serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static RequestResult success() {
        return new RequestResult(200, "Success", null);
    }
}
