package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/18.
 */

public class FamilyOwn implements Serializable {
    private static final long serialVersionUID = -6826537746139299526L;
    private String family_id;
    private String family_name;
    private String member_count;
    private String family_number;
    private String thumb;
    private String is_image;

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

    public String getFamily_number() {
        return family_number;
    }

    public void setFamily_number(String family_number) {
        this.family_number = family_number;
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
}
