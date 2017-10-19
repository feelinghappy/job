package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BuilderFood {
    /**
     * status : 1
     * message : 成功
     * data : [{"id":"7","name":"测试素菜","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161216/diet_5853a1149aa28.jpg"}]
     */

    public int status;
    public String message;
    public List<DataBean> data;
    public static class DataBean {
        public String id;
        public String name;
        public String cover;
        public String url;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BuilderFood{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
