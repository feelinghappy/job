package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ReadMessageBean {

    /**
     * code : 200
     * msg : success
     * result : {"message_title":"会员邀请消息","message_body":"{\"member_name\":\"Alert\",\"member_avatar\":\"597077e55ca60.jpeg\",\"messge_type\":2,\"family_name\":\"\\u65b0\\u540d\\u79f0\",\"family_id\":\"1\",\"is_invite\":1}","message_time":"1502761236","notice":"1"}
     */
    /**
     * message_title : 会员邀请消息
     * message_body : {"member_name":"Alert","member_avatar":"597077e55ca60.jpeg","messge_type":2,"family_name":"\u65b0\u540d\u79f0","family_id":"1","is_invite":1}
     * message_time : 1502761236
     * notice : 1
     */

    private String message_title;
    private String message_body;
    private String message_time;
    private String notice;

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

}
