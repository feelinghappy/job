package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/17.
 */

public class FamilyRemind {
    /*"result":[{"send_member_avatar":"http:\/\/sun.healthywo.com\/uploads\/avatar\/59891fc265dbd.png","warns_id":3,"warns_content":"\u6dfb\u52a0\u5bb6\u5ead\u63d0\u9192+1","warner":3,"send_member":16,"accept_member":0}]*/
    /**
     * send_member_avatar : http://sun.healthywo.com/uploads/avatar/59891fc265dbd.png
     * warns_id : 3
     * warns_content : 添加家庭提醒+1
     * warner : 3
     * send_member : 16
     * accept_member : 0
     */

    private String send_member_avatar;
    private int warns_id;
    private String warns_content;
    private int warner;
    private int send_member;
    private int accept_member;

    public String getSend_member_avatar() {
        return send_member_avatar;
    }

    public void setSend_member_avatar(String send_member_avatar) {
        this.send_member_avatar = send_member_avatar;
    }

    public int getWarns_id() {
        return warns_id;
    }

    public void setWarns_id(int warns_id) {
        this.warns_id = warns_id;
    }

    public String getWarns_content() {
        return warns_content;
    }

    public void setWarns_content(String warns_content) {
        this.warns_content = warns_content;
    }

    public int getWarner() {
        return warner;
    }

    public void setWarner(int warner) {
        this.warner = warner;
    }

    public int getSend_member() {
        return send_member;
    }

    public void setSend_member(int send_member) {
        this.send_member = send_member;
    }

    public int getAccept_member() {
        return accept_member;
    }

    public void setAccept_member(int accept_member) {
        this.accept_member = accept_member;
    }
}
