package com.longcai.medical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class BaseListResult<T> implements Serializable {
    private static final long serialVersionUID = -6289726222213305936L;
    private String code;
    private String msg;
    private List<T> result;

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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
