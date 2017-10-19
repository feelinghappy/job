package com.longcai.medical.bean;

public class Search {
    /*{"code":"200","msg":"success","result":[{"family_id":5,"member_id":"3","family_name":"\u4e00\u4e00\u4e00\u4e00","member_name":"1122","thumb":"2"}]}*/
    private String family_id;   //家庭ID
    private String member_id;   //会员ID
    private String family_name;   //家庭名称
    private String member_name;  //会员昵称
    private String thumb;   //会员头像
//        public String is_image;  //会员昵称


    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }
}
