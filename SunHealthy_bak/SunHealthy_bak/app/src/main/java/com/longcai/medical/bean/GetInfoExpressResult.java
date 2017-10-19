package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/9/3.
 */

public class GetInfoExpressResult {
    private String express_name;
    private String express_code;
    private String shipping_code;
    private long shipping_time;

    public long getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(long shipping_time) {
        this.shipping_time = shipping_time;
    }


    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }
}
