package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/17.
 */

public class GetUserInfoResult {
    /*{"member_id":3,"member_mobile":"18810639952","member_name":"1122",
   "member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/59891fc265dbd.png","member_state":1}*/
    /**
     * member_id : 3
     * member_mobile : 18810639952
     * member_name : 1122
     * member_avatar : http://sun.healthywo.com/uploads/avatar/59891fc265dbd.png
     * member_state : 1
     */

    private int member_id;
    private String member_mobile;
    private String member_name;
    private String member_avatar;
    private int member_state;
    private String member_height;
    private String member_weight;
    private String birthday;

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

    public String getMember_height() {
        return member_height;
    }

    public void setMember_height(String member_height) {
        this.member_height = member_height;
    }

    public String getMember_weight() {
        return member_weight;
    }

    public void setMember_weight(String member_weight) {
        this.member_weight = member_weight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
