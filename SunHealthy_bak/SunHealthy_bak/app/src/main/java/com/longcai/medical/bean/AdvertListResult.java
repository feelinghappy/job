package com.longcai.medical.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class AdvertListResult implements Serializable {
    private static final long serialVersionUID = 4207962342979917598L;
    private String position_name;
    private String position_intro;
    private List<AdvertResult> advert;

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getPosition_intro() {
        return position_intro;
    }

    public void setPosition_intro(String position_intro) {
        this.position_intro = position_intro;
    }

    public List<AdvertResult> getAdvert() {
        return advert;
    }

    public void setAdvert(List<AdvertResult> advert) {
        this.advert = advert;
    }

    @Override
    public String toString() {
        return "AdvertListResult{" +
                "position_name='" + position_name + '\'' +
                ", position_intro='" + position_intro + '\'' +
                ", advert=" + advert +
                '}';
    }
}
