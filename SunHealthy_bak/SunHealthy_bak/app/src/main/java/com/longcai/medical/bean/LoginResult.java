package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/18.
 */

public class LoginResult {
    private String token;
    private String has_info;
    private String member_role;

    public String getMember_role() {
        return member_role;
    }

    public void setMember_role(String member_role) {
        this.member_role = member_role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHas_info() {
        return has_info;
    }

    public void setHas_info(String has_info) {
        this.has_info = has_info;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "token='" + token + '\'' +
                ", has_info='" + has_info + '\'' +
                '}';
    }
}
