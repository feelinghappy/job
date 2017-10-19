package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/1.
 */

public class GetInfoAddressResult {
    private String address_id;
    private String true_name;
    private String area_info;
    private String address;
    private String mob_phone;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMob_phone() {
        return mob_phone;
    }

    public void setMob_phone(String mob_phone) {
        this.mob_phone = mob_phone;
    }
}
