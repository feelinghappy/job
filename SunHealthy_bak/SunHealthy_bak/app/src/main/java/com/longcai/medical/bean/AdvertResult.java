package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/10.
 */

public class AdvertResult implements Serializable {
    private static final long serialVersionUID = -2827938660174382084L;
    private String title;
    private String adv_pic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdv_pic() {
        return adv_pic;
    }

    public void setAdv_pic(String adv_pic) {
        this.adv_pic = adv_pic;
    }

    @Override
    public String toString() {
        return "AdvertResult{" +
                "title='" + title + '\'' +
                ", adv_pic='" + adv_pic + '\'' +
                '}';
    }
}
