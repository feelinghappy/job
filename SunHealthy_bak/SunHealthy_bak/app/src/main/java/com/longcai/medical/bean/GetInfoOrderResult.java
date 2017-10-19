package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoOrderResult {
    private long order_sn;
    private String order_amount;
    private String order_state;
    private String pay_sn;
    private String stroe_name;
    private long create_time;
    private String sell_company;
    private int business_type;
    private String pay_type;
    private long remaining_time;

    public long getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(long order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public String getStroe_name() {
        return stroe_name;
    }

    public void setStroe_name(String stroe_name) {
        this.stroe_name = stroe_name;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getSell_company() {
        return sell_company;
    }

    public void setSell_company(String sell_company) {
        this.sell_company = sell_company;
    }

    public int getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(int business_type) {
        this.business_type = business_type;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public long getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(long remaining_time) {
        this.remaining_time = remaining_time;
    }
}
