package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/5.
 */

public class OrderChuKuDetailBean {

    /**
     * delivery : {"shipping_code":"1234567890","shipping_name":{"express_id":90,"express_name":"宅急送","express_state":1,"express_code":"ZJS\n","express_order":"1"},"order_state":30}
     * reciver : {"reciver_name":"安鹏","mobile":"17635128995","address":"北京 北京市 东城区和平南路","order_sn":9000000000001501}
     */

    private DeliveryBean delivery;
    private ReciverBean reciver;

    public DeliveryBean getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryBean delivery) {
        this.delivery = delivery;
    }

    public ReciverBean getReciver() {
        return reciver;
    }

    public void setReciver(ReciverBean reciver) {
        this.reciver = reciver;
    }

    public static class DeliveryBean {
        /**
         * shipping_code : 1234567890
         * shipping_name : {"express_id":90,"express_name":"宅急送","express_state":1,"express_code":"ZJS\n","express_order":"1"}
         * order_state : 30
         */

        private String shipping_code;
        private ShippingNameBean shipping_name;
        private int order_state;
        private String create_time;
        private String goods_name;

        public String getShipping_code() {
            return shipping_code;
        }

        public void setShipping_code(String shipping_code) {
            this.shipping_code = shipping_code;
        }

        public ShippingNameBean getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(ShippingNameBean shipping_name) {
            this.shipping_name = shipping_name;
        }

        public int getOrder_state() {
            return order_state;
        }

        public void setOrder_state(int order_state) {
            this.order_state = order_state;
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

        public static class ShippingNameBean {
            /**
             * express_id : 90
             * express_name : 宅急送
             * express_state : 1
             * express_code : ZJS

             * express_order : 1
             */

            private int express_id;
            private String express_name;
            private int express_state;
            private String express_code;
            private String express_order;

            public int getExpress_id() {
                return express_id;
            }

            public void setExpress_id(int express_id) {
                this.express_id = express_id;
            }

            public String getExpress_name() {
                return express_name;
            }

            public void setExpress_name(String express_name) {
                this.express_name = express_name;
            }

            public int getExpress_state() {
                return express_state;
            }

            public void setExpress_state(int express_state) {
                this.express_state = express_state;
            }

            public String getExpress_code() {
                return express_code;
            }

            public void setExpress_code(String express_code) {
                this.express_code = express_code;
            }

            public String getExpress_order() {
                return express_order;
            }

            public void setExpress_order(String express_order) {
                this.express_order = express_order;
            }
        }
    }

    public static class ReciverBean {
        /**
         * reciver_name : 安鹏
         * mobile : 17635128995
         * address : 北京 北京市 东城区和平南路
         * order_sn : 9000000000001501
         */

        private String reciver_name;
        private String mobile;
        private String address;
        private long order_sn;

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
    }
}
