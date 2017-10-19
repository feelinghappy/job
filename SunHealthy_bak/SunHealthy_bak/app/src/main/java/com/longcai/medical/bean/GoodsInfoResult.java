package com.longcai.medical.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/31.
 */

public class GoodsInfoResult implements Parcelable {
    private String order_id;
    private String goods_commonid;
    private String goods_name;
    private String goods_image;
    private String goods_price;
    private String goods_num;
    private int gc_id;// 1:押金

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

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

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public int getGc_id() {
        return gc_id;
    }

    public void setGc_id(int gc_id) {
        this.gc_id = gc_id;
    }

    public GoodsInfoResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_id);
        dest.writeString(this.goods_commonid);
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_image);
        dest.writeString(this.goods_price);
        dest.writeString(this.goods_num);
        dest.writeInt(this.gc_id);
    }

    protected GoodsInfoResult(Parcel in) {
        this.order_id = in.readString();
        this.goods_commonid = in.readString();
        this.goods_name = in.readString();
        this.goods_image = in.readString();
        this.goods_price = in.readString();
        this.goods_num = in.readString();
        this.gc_id = in.readInt();
    }

    public static final Creator<GoodsInfoResult> CREATOR = new Creator<GoodsInfoResult>() {
        @Override
        public GoodsInfoResult createFromParcel(Parcel source) {
            return new GoodsInfoResult(source);
        }

        @Override
        public GoodsInfoResult[] newArray(int size) {
            return new GoodsInfoResult[size];
        }
    };
}
