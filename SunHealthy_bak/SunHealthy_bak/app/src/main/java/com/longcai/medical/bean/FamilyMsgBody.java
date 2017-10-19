package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/17.
 */

public class FamilyMsgBody {
    /*{"member_name":"Alert","member_avatar":"597077e55ca60.jpeg","messge_type":2,"family_name":"\u65b0\u540d\u79f0","family_id":"1","is_invite":1}*/
    /**
     * member_name : Alert
     * member_avatar : 597077e55ca60.jpeg
     * messge_type : 2
     * family_name : 新名称
     * family_id : 1
     * is_invite : 1
     * is_join : 1是否同意
     */

    private String member_name;
    private String member_avatar;
    private int messge_type;
    private String family_name;
    private String family_id;
    private int is_invite;
    private int is_join;

    public int getIs_join() {
        return is_join;
    }

    public void setIs_join(int is_join) {
        this.is_join = is_join;
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

    public int getMessge_type() {
        return messge_type;
    }

    public void setMessge_type(int messge_type) {
        this.messge_type = messge_type;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public int getIs_invite() {
        return is_invite;
    }

    public void setIs_invite(int is_invite) {
        this.is_invite = is_invite;
    }
}
