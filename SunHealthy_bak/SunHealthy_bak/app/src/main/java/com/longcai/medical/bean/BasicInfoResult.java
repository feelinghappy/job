package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */

public class BasicInfoResult implements Serializable {
    private static final long serialVersionUID = 8784836477885624469L;
    private String true_name;
    /**
     * 1:男;2:女
     */
    private String member_sex;
    private String member_age;
    private String member_height;
    private String member_weight;
    /**
     * 0:无;1:国家公务员;2:专业技术人员;3:职员;4:企业管理人员;5:工人;
     * 6:农民;7:学生;8:现役军人;9:自由职业者;10:个体经营者;11:退(离)休人员
     */
    private String profession;
    /**
     * 0:城镇职工医保;1:新农合医保;2:无;3:其他
     */
    private String medical_type;

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getMedical_type() {
        return medical_type;
    }

    public void setMedical_type(String medical_type) {
        this.medical_type = medical_type;
    }

    @Override
    public String toString() {
        return "BasicInfoResult{" +
                "true_name='" + true_name + '\'' +
                ", member_sex='" + member_sex + '\'' +
                ", member_age='" + member_age + '\'' +
                ", member_height='" + member_height + '\'' +
                ", member_weight='" + member_weight + '\'' +
                ", profession='" + profession + '\'' +
                ", medical_type='" + medical_type + '\'' +
                '}';
    }
}
