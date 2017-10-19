package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/4.
 */

public class KdniaoOrderResult {
    private String EBusinessID;
    private String UpdateTime;
    private boolean Success;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }
}
