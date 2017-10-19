package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoResult {
    private GetInfoOrderResult order;
    private GetInfoAddressResult address;
    private GetInfoGoodsResult goods;
    private GetInfoAdviserResult adviser;

    public GetInfoOrderResult getOrder() {
        return order;
    }

    public void setOrder(GetInfoOrderResult order) {
        this.order = order;
    }

    public GetInfoAddressResult getAddress() {
        return address;
    }

    public void setAddress(GetInfoAddressResult address) {
        this.address = address;
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
