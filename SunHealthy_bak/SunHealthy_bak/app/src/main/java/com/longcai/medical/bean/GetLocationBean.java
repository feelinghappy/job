package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/5/27.
 */

public class GetLocationBean {

    /**
     * code : 1
     * msg :
     * result : {"lng":"113.9389092","lat":"22.5523364"}
     /**
     * lng : 113.9389092
     * lat : 22.5523364
     */

    private String lng;
    private String lat;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
