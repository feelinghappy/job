package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/18.
 */

public class CustomDetail {

    /**
     * 未注册过
     * customer_name : -123
     * customer_mobile : 18799999999
     * product_intent : 机器人
     * remarks : 2-20个字符
     * is_member:0
     * money_amount : 0
     */

    private String customer_name;
    private String customer_mobile;
    private String product_intent;
    private String remarks;
    private int is_member;
    private int money_amount;


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

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getProduct_intent() {
        return product_intent;
    }

    public void setProduct_intent(String product_intent) {
        this.product_intent = product_intent;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getMoney_amount() {
        return money_amount;
    }

    public void setMoney_amount(int money_amount) {
        this.money_amount = money_amount;
    }
}
