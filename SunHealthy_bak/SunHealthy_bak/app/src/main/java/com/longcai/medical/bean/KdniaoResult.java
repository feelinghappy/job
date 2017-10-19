package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class KdniaoResult {
    private String EBusinessID;
    private String OrderCode;
    private String ShipperCode;
    private String LogisticCode;
    private boolean Success;
    private int State;
    private String Reason;
    private List<KdniaoTraceResult> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public List<KdniaoTraceResult> getTraces() {
        return Traces;
    }

    public void setTraces(List<KdniaoTraceResult> traces) {
        Traces = traces;
    }
}
