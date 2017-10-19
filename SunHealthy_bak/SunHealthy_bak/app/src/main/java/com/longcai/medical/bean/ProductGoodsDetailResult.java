package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ProductGoodsDetailResult {
    private String goods_commonid;
    private String goods_name;
    private String goods_image;
    private String gc_id;
    private String goods_version;
    private String goods_price;
    private List<ProductGoodsSpecResult> goods_spec;
    private String goods_description;
    private List<String> goods_body;
    private List<String> goods_service;

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

    public String getGc_id() {
        return gc_id;
    }

    public void setGc_id(String gc_id) {
        this.gc_id = gc_id;
    }

    public String getGoods_version() {
        return goods_version;
    }

    public void setGoods_version(String goods_version) {
        this.goods_version = goods_version;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public List<ProductGoodsSpecResult> getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(List<ProductGoodsSpecResult> goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_description() {
        return goods_description;
    }

    public void setGoods_description(String goods_description) {
        this.goods_description = goods_description;
    }

    public List<String> getGoods_body() {
        return goods_body;
    }

    public void setGoods_body(List<String> goods_body) {
        this.goods_body = goods_body;
    }

    public List<String> getGoods_service() {
        return goods_service;
    }

    public void setGoods_service(List<String> goods_service) {
        this.goods_service = goods_service;
    }
}
