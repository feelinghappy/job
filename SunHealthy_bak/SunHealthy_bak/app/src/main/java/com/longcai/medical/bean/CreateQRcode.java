package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/14.
 */

public class CreateQRcode {

    /**
     * seller_name : 舒婷
     * qrcode : http://sun.healthywo.com/qrcode/59ba47814e2a4.png
     * sell_amount : null
     */

    private String seller_name;
    private String qrcode;
    private Object sell_amount;
    private String income;

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Object getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(Object sell_amount) {
        this.sell_amount = sell_amount;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
