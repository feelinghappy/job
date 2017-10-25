package com.hrg.robotproject.bean;

/**
 * Created by liutao on 2017/9/29.
 */

public class WildTokenResult {
    private String custom_token;

    public String getCustom_token() {
        return custom_token;
    }

    public void setCustom_token(String custom_token) {
        this.custom_token = custom_token;
    }

    @Override
    public String toString() {
        return "WildTokenResult{" +
                "custom_token='" + custom_token + '\'' +
                '}';
    }
}
