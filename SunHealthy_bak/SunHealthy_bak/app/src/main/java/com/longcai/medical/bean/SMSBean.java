package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class SMSBean {

    /**
     * code : 200
     * msg : success
     * result : []
     */

    private String code;
    private String msg;
    private List<?> result;

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

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
