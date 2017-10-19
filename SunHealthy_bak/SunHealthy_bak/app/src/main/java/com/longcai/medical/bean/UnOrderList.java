package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/12.
 */

public class UnOrderList {

    /**
     * order_sn : 9000000000001901
     * order_id : 19
     * create_time : 1504604026
     * order_state : 30
     * order_amount : 9760.00
     * goods_name : 太阳家族-小火宝
     */

    private long order_sn;
    private int order_id;
    private int create_time;
    private int order_state;
    private String order_amount;
    private String goods_name;

    public long getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(long order_sn) {
        this.order_sn = order_sn;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
