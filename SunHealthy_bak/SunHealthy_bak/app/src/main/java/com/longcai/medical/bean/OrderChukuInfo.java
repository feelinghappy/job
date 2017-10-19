package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/5.
 */

public class OrderChukuInfo {

    /**
     * reciver : {"reciver_name":"安鹏","mobile":"17635128995","address":"北京 北京市 东城区和平南路","order_sn":9000000000002001,"create_time":"2017-09-05 18:51","goods_name":"太阳家族-小火宝"}
     */

    private ReciverBean reciver;

    public ReciverBean getReciver() {
        return reciver;
    }

    public void setReciver(ReciverBean reciver) {
        this.reciver = reciver;
    }

    public static class ReciverBean {
        /**
         * reciver_name : 安鹏
         * mobile : 17635128995
         * address : 北京 北京市 东城区和平南路
         * order_sn : 9000000000002001
         * create_time : 2017-09-05 18:51
         * goods_name : 太阳家族-小火宝
         */

        private String reciver_name;
        private String mobile;
        private String address;
        private long order_sn;
        private String create_time;
        private String goods_name;

        public String getReciver_name() {
            return reciver_name;
        }

        public void setReciver_name(String reciver_name) {
            this.reciver_name = reciver_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(long order_sn) {
            this.order_sn = order_sn;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
    }
}
