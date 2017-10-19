package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/15.
 */

public class GetThingWarns implements Serializable {

    private static final long serialVersionUID = -7534511334165267217L;
    private String warns_id;
    private String family_id;
    private String send_time;
    private String warns_state;
    private String warns_content;
    private String family_member_id;
    private String repeat_time;
    private String warner;
    private String accept_member;
    private String send_member;
    private String accept_member_name;

    public String getWarns_id() {
        return warns_id;
    }

    public void setWarns_id(String warns_id) {
        this.warns_id = warns_id;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getWarns_state() {
        return warns_state;
    }

    public void setWarns_state(String warns_state) {
        this.warns_state = warns_state;
    }

    public String getWarns_content() {
        return warns_content;
    }

    public void setWarns_content(String warns_content) {
        this.warns_content = warns_content;
    }

    public String getFamily_member_id() {
        return family_member_id;
    }

    public void setFamily_member_id(String family_member_id) {
        this.family_member_id = family_member_id;
    }

    public String getRepeat_time() {
        return repeat_time;
    }

    public void setRepeat_time(String repeat_time) {
        this.repeat_time = repeat_time;
    }

    public String getWarner() {
        return warner;
    }

    public void setWarner(String warner) {
        this.warner = warner;
    }

    public String getAccept_member() {
        return accept_member;
    }

    public void setAccept_member(String accept_member) {
        this.accept_member = accept_member;
    }

    public String getSend_member() {
        return send_member;
    }

    public void setSend_member(String send_member) {
        this.send_member = send_member;
    }

    public String getAccept_member_name() {
        return accept_member_name;
    }

    public void setAccept_member_name(String accept_member_name) {
        this.accept_member_name = accept_member_name;
    }
}
