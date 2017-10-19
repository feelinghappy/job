package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/31.
 */

public class GetMemberInfoBean implements Serializable{

    /**
     * code : 1
     * msg : 获取成功
     * result : {"mobile":null,"nickname":"hahah","avatar":"","member_sex":"0","member_age":"1","member_height":null,"member_weight":null,"watch_imei":"","physique_type":"0","family_member_id":13}
     * mobile : null
     * nickname : hahah
     * avatar :
     * member_sex : 0
     * member_age : 1
     * member_height : null
     * member_weight : null
     * watch_imei :
     * physique_type : 0
     * family_member_id : 13
     */

    private String mobile;
    private String nickname;
    private String avatar;
    private String member_sex;
    private String member_age;
    private String member_height;
    private String member_weight;
    private String watch_imei;
    private String physique_type;
    private int family_member_id;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getWatch_imei() {
        return watch_imei;
    }

    public void setWatch_imei(String watch_imei) {
        this.watch_imei = watch_imei;
    }

    public String getPhysique_type() {
        return physique_type;
    }

    public void setPhysique_type(String physique_type) {
        this.physique_type = physique_type;
    }

    public int getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(int family_member_id) {
        this.family_member_id = family_member_id;
    }
}
