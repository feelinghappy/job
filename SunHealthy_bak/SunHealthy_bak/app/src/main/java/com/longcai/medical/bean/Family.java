package com.longcai.medical.bean;

import java.io.Serializable;

public class Family implements Serializable{

    private static final long serialVersionUID = -5147932733224002153L;
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
    private String family_id;
    private String family_name;
    private String member_count;
    private String thumb;
    private String is_image;
    private String robot_imei;
    private String family_member_id;

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getMember_count() {
        return member_count;
    }

    public void setMember_count(String member_count) {
        this.member_count = member_count;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getIs_image() {
        return is_image;
    }

    public void setIs_image(String is_image) {
        this.is_image = is_image;
    }

    public String getRobot_imei() {
        return robot_imei;
    }

    public void setRobot_imei(String robot_imei) {
        this.robot_imei = robot_imei;
    }

    public String getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(String family_member_id) {
        this.family_member_id = family_member_id;
    }
}
