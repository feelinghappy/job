package com.longcai.medical.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ProductGoodsResult implements Parcelable {
    private String goods_name;
    private String goods_commonid;
    private String goods_image;
    private String goods_price;
    private String goods_version;
    private String gc_id;
    private int goods_storage;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_commonid() {
        return goods_commonid;
    }

    public void setGoods_commonid(String goods_commonid) {
        this.goods_commonid = goods_commonid;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
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

    public String getGc_id() {
        return gc_id;
    }

    public void setGc_id(String gc_id) {
        this.gc_id = gc_id;
    }

    public int getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(int goods_storage) {
        this.goods_storage = goods_storage;
    }

    public ProductGoodsResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_name);
        dest.writeString(this.goods_commonid);
        dest.writeString(this.goods_image);
        dest.writeString(this.goods_price);
        dest.writeString(this.goods_version);
        dest.writeString(this.gc_id);
        dest.writeInt(this.goods_storage);
    }

    protected ProductGoodsResult(Parcel in) {
        this.goods_name = in.readString();
        this.goods_commonid = in.readString();
        this.goods_image = in.readString();
        this.goods_price = in.readString();
        this.goods_version = in.readString();
        this.gc_id = in.readString();
        this.goods_storage = in.readInt();
    }

    public static final Creator<ProductGoodsResult> CREATOR = new Creator<ProductGoodsResult>() {
        @Override
        public ProductGoodsResult createFromParcel(Parcel source) {
            return new ProductGoodsResult(source);
        }

        @Override
        public ProductGoodsResult[] newArray(int size) {
            return new ProductGoodsResult[size];
        }
    };
}
