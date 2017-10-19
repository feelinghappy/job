package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/19.
 */

public class LoginTencentResult {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginTencentResult{" +
                "token='" + token + '\'' +
                '}';
    }
}
