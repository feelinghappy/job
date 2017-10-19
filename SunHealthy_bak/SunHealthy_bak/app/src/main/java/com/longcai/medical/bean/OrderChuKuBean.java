package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/27.
 * 出库
 */

public class OrderChuKuBean {
/*{"code":"200","msg":"success","result":[{"order_amount":"9760.00","order_sn":9000000000001901,"order_id":19,
"payment_time":1504604142,"goods_name":"\u592a\u9633\u5bb6\u65cf-\u5c0f\u706b\u5b9d","goods_commonid":3}]}*/
    /**
     * order_amount : 4880.00
     * order_sn : 9000000000001501
     * order_id : 15
     * goods_name : 太阳家族-小火宝
     * goods_commonid
     * payment_time
     * goods_num
     *
     * private String shipping_time;
     private String express_name;
     */

    private String order_amount;
    private long order_sn;
    private int order_id;
    private String goods_name;
    private int goods_commonid;
    private long payment_time;
    private int goods_num;
    //已发货列表
    private String shipping_time;
    private String express_name;
    private String shipping_code;
    private String create_time;

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public long getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(long payment_time) {
        this.payment_time = payment_time;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public int getGoods_commonid() {
        return goods_commonid;
    }

    public void setGoods_commonid(int goods_commonid) {
        this.goods_commonid = goods_commonid;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

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

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}
