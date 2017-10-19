package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoUnpayResult {
    private GetInfoOrderResult order;
    //    private List<?> address;
    private GetInfoAddressResult address;
    private GetInfoGoodsResult goods;
    private GetInfoAdviserResult adviser;

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

    /*public List<GetInfoAddressResult> getAddress() {
        return address;
    }

    public void setAddress(List<GetInfoAddressResult> address) {
        this.address = address;
    }*/

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
