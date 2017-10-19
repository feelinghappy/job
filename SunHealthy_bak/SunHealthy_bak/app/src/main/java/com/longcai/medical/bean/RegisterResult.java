package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/6.
 * 编辑会员资料
 */

public class RegisterResult {


    /**
     * code : 200
     * msg : success
     * result : {"member_name":"11","member_sex":"1","member_age":"33","member_height":158,"member_weight":55,"area_name":null}
     * member_name : 11
     * member_sex : 1
     * member_age : 33
     * member_height : 158
     * member_weight : 55
     * area_name : null
     */

    private String member_name;
    private String member_sex;
    private String member_age;
    private int member_height;
    private int member_weight;
    private Object area_name;

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(String member_sex) {
        this.member_sex = member_sex;
    }

    public String getMember_age() {
        return member_age;
    }

    public void setMember_age(String member_age) {
        this.member_age = member_age;
    }

    public int getMember_height() {
        return member_height;
    }

    public void setMember_height(int member_height) {
        this.member_height = member_height;
    }

    public int getMember_weight() {
        return member_weight;
    }

    public void setMember_weight(int member_weight) {
        this.member_weight = member_weight;
    }

    public Object getArea_name() {
        return area_name;
    }

    public void setArea_name(Object area_name) {
        this.area_name = area_name;
    }
}
