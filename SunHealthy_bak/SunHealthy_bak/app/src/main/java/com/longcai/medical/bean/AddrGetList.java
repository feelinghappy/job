package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AddrGetList implements Serializable{
    /**
     * address_id : 1
     * member_id : 9
     * true_name : 薛全德
     * province_id : 4
     * area_id : 1356
     * city_id : 90
     * area_info : 山西 晋中市 榆次区
     * address : 和田商务3层
     * mob_phone : 13509715455
     * is_default : 0
     */

    private int address_id;
    private int member_id;
    private String true_name;
    private int province_id;
    private int area_id;
    private int city_id;
    private String area_info;
    private String address;
    private String mob_phone;
    private int is_default;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
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

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
