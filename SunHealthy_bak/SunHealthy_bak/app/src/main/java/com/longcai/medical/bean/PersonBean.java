package com.longcai.medical.bean;

import com.longcai.medical.ui.view.customer.PinyinUtils;

public class PersonBean {
   /* *
     * is_member : 0
     * customer_name : 哆啦A梦
     * sm_id : 7
     * "customer_state": 2*/

    private int is_member;
    private String customer_name;
    private int sm_id;
    private int customer_state;
    private String PinYin;
    private String FirstPinYin;

    public int getCustomer_state() {
        return customer_state;
    }

    public void setCustomer_state(int customer_state) {
        this.customer_state = customer_state;
    }

    public int getIs_member() {
        return is_member;
    }

    public void setIs_member(int is_member) {
        this.is_member = is_member;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }

    public String getPinYin() {
        PinYin = PinyinUtils.getPingYin(customer_name);
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getFirstPinYin() {
        String Fpinyin = getPinYin().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (Fpinyin.matches("[A-Z]")) {
            FirstPinYin = Fpinyin;
        } else {
            FirstPinYin = "#";
        }
        return FirstPinYin;
    }

    public void setFirstPinYin(String firstPinYin) {
        FirstPinYin = firstPinYin;
    }

    /*public String toString() {
        return "姓名：" + getCustomer_name() + "   拼音：" + getPinYin() + "    首字母："
                + getFirstPinYin() + "sm_id：" + getSm_id() + "is_member：" + getIs_member();
    }*/
}