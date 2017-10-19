package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/6.
 * 编辑会员资料
 */

public class EditInfoBean {
    /*
    {"member_name":"xiao","member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/5982c92a581e8.png","mobile":"18810639952"}
*/
    /**
     * member_name : xiao
     * member_avatar : http://sun.healthywo.com/uploads/avatar/5982c92a581e8.png
     * mobile : 18810639952
     */

    private String member_name;
    private String member_avatar;
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
