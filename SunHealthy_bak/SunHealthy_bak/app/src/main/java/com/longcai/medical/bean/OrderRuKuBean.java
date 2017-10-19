package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/27.
 * 入库
 */

public class OrderRuKuBean {

    /**
     * goods_time : 2017-09-05 10:23:01
     * goods_commonid : 3
     * goods_sn : HSRasd0170400010
     * company : 常仁总部
     * goods_name :
     */

    private String goods_time;
    private int goods_commonid;
    private String goods_sn;
    private String company;
    private String goods_name;
    private int goods_num;

    public String getGoods_time() {
        return goods_time;
    }

    public void setGoods_time(String goods_time) {
        this.goods_time = goods_time;
    }

    public int getGoods_commonid() {
        return goods_commonid;
    }

    public void setGoods_commonid(int goods_commonid) {
        this.goods_commonid = goods_commonid;
    }

    public String getGoods_sn() {
        return goods_sn;
    }

    public void setGoods_sn(String goods_sn) {
        this.goods_sn = goods_sn;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }
}
