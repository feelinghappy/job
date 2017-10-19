package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ProductCategoryResult implements Serializable {
    private static final long serialVersionUID = 8490509180790439796L;
    private String gc_id;
    private String gc_name;

    public String getGc_id() {
        return gc_id;
    }

    public void setGc_id(String gc_id) {
        this.gc_id = gc_id;
    }

    public String getGc_name() {
        return gc_name;
    }

    public void setGc_name(String gc_name) {
        this.gc_name = gc_name;
    }
}
