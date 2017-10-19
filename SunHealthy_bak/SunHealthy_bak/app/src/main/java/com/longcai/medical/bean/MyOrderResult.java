package com.longcai.medical.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyOrderResult implements Parcelable {
    private String order_id;
    private String order_sn;
    private String pay_sn;
    private String goods_amount;
    private String order_amount;
    private String order_state;
    private GoodsInfoResult goods_info;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
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

    public GoodsInfoResult getGoods_info() {
        return goods_info;
    }

    public void setGoods_info(GoodsInfoResult goods_info) {
        this.goods_info = goods_info;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.order_sn);
        dest.writeString(this.pay_sn);
        dest.writeString(this.goods_amount);
        dest.writeString(this.order_amount);
        dest.writeString(this.order_state);
        dest.writeParcelable(this.goods_info, flags);
    }

    public MyOrderResult() {
    }

    protected MyOrderResult(Parcel in) {
        this.order_id = in.readString();
        this.order_sn = in.readString();
        this.pay_sn = in.readString();
        this.goods_amount = in.readString();
        this.order_amount = in.readString();
        this.order_state = in.readString();
        this.goods_info = in.readParcelable(GoodsInfoResult.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyOrderResult> CREATOR = new Parcelable.Creator<MyOrderResult>() {
        @Override
        public MyOrderResult createFromParcel(Parcel source) {
            return new MyOrderResult(source);
        }

        @Override
        public MyOrderResult[] newArray(int size) {
            return new MyOrderResult[size];
        }
    };
}
