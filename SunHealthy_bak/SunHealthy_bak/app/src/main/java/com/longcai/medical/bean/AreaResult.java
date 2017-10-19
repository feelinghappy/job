package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AreaResult {
    private String area_id;
    private String area_name;
    private String parent_id;

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    @Override
    public String toString() {
        return "AreaResult{" +
                "area_id='" + area_id + '\'' +
                ", area_name='" + area_name + '\'' +
                ", parent_id='" + parent_id + '\'' +
                '}';
    }
}
