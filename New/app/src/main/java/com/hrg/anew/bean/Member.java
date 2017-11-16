package com.hrg.anew.bean;

import java.io.Serializable;

public class Member implements Serializable {

    /*{"code":"200","msg":"success","result":[{"family_id":2,"family_name":"\u4e00\u4e2a\u5bb6\u5ead","member_count":1,"thumb":"2","is_image":0},{"family_id":3,"family_name":"\u6728\u9a6c\u6728\u9a6c","member_count":1,"thumb":"2","is_image":0},{"family_id":4,"family_name":"v\u521a\u521a\u5475\u5475","member_count":1,"thumb":"3","is_image":0},{"family_id":5,"family_name":"\u4e00\u4e00\u4e00\u4e00","member_count":1,"thumb":"2","is_image":0}]}*/
    /*	{
    "code": 1,
    "msg": "获取成功",
    "result": {
        "has_message": 0,   //是否有未读消息 1.是 0.否
        "list": [
            {
                "family_id": "1",    //家庭id
                "family_name": "幸福一家人",  //家庭名称
                "member_count": "1",    //会员总数
                "thumb": "2",      //封面图
                "is_image": "0"   //是否图片
            }
        ]
    }
}
    */
    public String member_id;
    public String family_member_id;
    public int is_member;
    public String member_name;
    public String member_avatar;
    public Health heath;

    public Member(String member_id,String family_member_id,int is_member,String member_name,String member_avatar,Health health)
    {
        this.member_id = member_id;
        this.family_member_id = family_member_id;
        this.is_member = is_member;
        this.member_name = member_name;
        this.member_avatar = member_avatar;
        this.heath = health;
    }


    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getIs_member() {
        return is_member;
    }

    public void setIs_member(int is_member) {
        this.is_member = is_member;
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




    public String getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(String family_member_id) {
        this.family_member_id = family_member_id;
    }
}