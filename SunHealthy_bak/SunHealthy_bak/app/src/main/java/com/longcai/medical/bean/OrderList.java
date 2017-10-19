package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class OrderList {
    /**
     * order_info : [{"order_sn":9000000000000301,"order_id":3,"payment_time":0,"order_state":40,"order_amount":"4880.00","goods_name":"小火宝"},{"order_sn":9000000000001501,"order_id":15,"payment_time":1504593291,"order_state":40,"order_amount":"4880.00","goods_name":"太阳家族-小火宝"}]
     * order_num : 2
     * order_total : 9760.00
     */

    private int order_num;
    private String order_total;
    private List<OrderInfoBean> order_info;

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }

    public String getOrder_total() {
        return order_total;
    }

    public void setOrder_total(String order_total) {
        this.order_total = order_total;
    }

    public List<OrderInfoBean> getOrder_info() {
        return order_info;
    }

    public void setOrder_info(List<OrderInfoBean> order_info) {
        this.order_info = order_info;
    }

    public static class OrderInfoBean {
        /**
         * order_sn : 9000000000000301
         * order_id : 3
         * payment_time : 0
         * create_time
         * order_state : 40
         * order_amount : 4880.00
         * goods_name : 小火宝
         */

        private long order_sn;
        private int order_id;
        private int payment_time;
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

        public int getPayment_time() {
            return payment_time;
        }

        public void setPayment_time(int payment_time) {
            this.payment_time = payment_time;
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

    /**
     * order_sn : 9000000000000301
     * order_id : 3
     * finnshed_time : 0
     * order_state : 50
     * goods_name : 小火宝
     * goods_price : 4880.00
     *//*

    private long order_sn;
    private int order_id;
    private long payment_time;
    private long create_time;
    private int order_state;
    private String goods_name;
//    private String goods_price;
    private String order_amount;

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

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public long getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(long payment_time) {
        this.payment_time = payment_time;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }*/
}
