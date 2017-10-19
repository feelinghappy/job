package com.longcai.medical.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/31.
 */

public class HelpInfoResult implements Serializable {

    private static final long serialVersionUID = 2183885924785738497L;
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
