package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoGoodsResult implements Serializable {

    private static final long serialVersionUID = 8962640639629679572L;
    private String goods_commonid;
    private String goods_name;
    private String goods_image;
    private String goods_price;
    private int goods_storage;
    private int goods_num;
    private int gc_id;

//    private String sell_company;
//    private String business_type;
//    private String pay_type;
//    private long remaining_time;

    public String getGoods_commonid() {
        return goods_commonid;
    }

    public void setGoods_commonid(String goods_commonid) {
        this.goods_commonid = goods_commonid;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getGc_id() {
        return gc_id;
    }

    public void setGc_id(int gc_id) {
        this.gc_id = gc_id;
    }

    public int getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }
}
