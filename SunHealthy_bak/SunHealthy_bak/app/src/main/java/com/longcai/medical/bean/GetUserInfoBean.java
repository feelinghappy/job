package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/17.
 */

public class GetUserInfoBean {
    /**
     * code : 200
     * msg : success
     * result : {"member_id":3,"member_mobile":"18810639952","member_name":"11","member_avatar":"http://sun.healthywo.com/uploads/avatar/59718ff008a86.jpg","member_state":1,"member_sex":1,"member_height":158,"member_age":33,"member_weight":55,"area_name":"未知"}
     * <p>
     * member_id : 3
     * member_mobile : 18810639952
     * member_name : 11
     * member_avatar : http://sun.healthywo.com/uploads/avatar/59718ff008a86.jpg
     * member_state : 1
     * member_sex : 1
     * member_height : 158
     * member_age : 33
     * member_weight : 55
     * area_name : 未知
     */
    private int member_id;
    private String member_mobile;
    private String member_name;
    private String member_avatar;
    private int member_state;
    private int member_sex;
    private int member_height;
    private int member_age;
    private int member_weight;
    private String area_name;

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public int getMember_state() {
        return member_state;
    }

    public void setMember_state(int member_state) {
        this.member_state = member_state;
    }

    public int getMember_sex() {
        return member_sex;
    }

    public void setMember_sex(int member_sex) {
        this.member_sex = member_sex;
    }

    public int getMember_height() {
        return member_height;
    }

    public void setMember_height(int member_height) {
        this.member_height = member_height;
    }

    public int getMember_age() {
        return member_age;
    }

    public void setMember_age(int member_age) {
        this.member_age = member_age;
    }

    public int getMember_weight() {
        return member_weight;
    }

    public void setMember_weight(int member_weight) {
        this.member_weight = member_weight;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

}
