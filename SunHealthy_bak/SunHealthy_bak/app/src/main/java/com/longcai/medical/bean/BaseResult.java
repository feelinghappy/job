package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18.
 */

public class BaseResult<T> implements Serializable{
    private static final long serialVersionUID = 278887260788216164L;
    private String code;
    private String msg;
    private T result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
