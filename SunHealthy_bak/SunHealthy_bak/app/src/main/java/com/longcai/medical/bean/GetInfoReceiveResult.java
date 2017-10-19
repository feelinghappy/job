package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoReceiveResult {
    private GetInfoOrderResult order;
    private GetInfoExpressResult express;
    private GetInfoGoodsResult goods;
    private GetInfoAdviserResult adviser;
    //
    private GetInfoAddressResult address;

    public GetInfoAddressResult getAddress() {
        return address;
    }

    public void setAddress(GetInfoAddressResult address) {
        this.address = address;
    }

    public GetInfoOrderResult getOrder() {
        return order;
    }

    public void setOrder(GetInfoOrderResult order) {
        this.order = order;
    }

    public GetInfoExpressResult getExpress() {
        return express;
    }

    public void setExpress(GetInfoExpressResult express) {
        this.express = express;
    }

    public GetInfoGoodsResult getGoods() {
        return goods;
    }

    public void setGoods(GetInfoGoodsResult goods) {
        this.goods = goods;
    }

    public GetInfoAdviserResult getAdviser() {
        return adviser;
    }

    public void setAdviser(GetInfoAdviserResult adviser) {
        this.adviser = adviser;
    }
}
