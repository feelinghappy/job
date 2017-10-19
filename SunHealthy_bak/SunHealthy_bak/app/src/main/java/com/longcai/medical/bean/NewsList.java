package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/5.
 */

import java.util.List;

/**
 * status : 1
 * message : 成功
 * data : {"pages":1,"next":2,"total":"7",
 * "infos":[{"id":"10",
 * "title":"用药膳调辅助理治疗糖尿病",
 * "dict_type":"25",
 * "cover":"/Public",
 * "posttime":"2016-10-31",
 * "typeName":"疾病",
 * "url":"index.php/Api/Other/infomation/type/0/order/time/id/10.html"},
 * ]
 * }
 */
public class NewsList {

    public int status;
    public String message;
    public  NewsListData data;
    public  class NewsListData{
        public int pages;
        public int next;
        public String total;
        public List<DataInfo> infos;

        @Override
        public String toString() {
            return "NewsListData{" +
                    "pages=" + pages +
                    ", next=" + next +
                    ", total='" + total + '\'' +
                    ", infos=" + infos +
                    '}';
        }
    }

    public  class DataInfo{
        public String id;
        public String title;
        public String dict_type;
        public String cover;
        public String posttime;
        public String typeName;
        public String url;
        public String hits;

        @Override
        public String toString() {
            return "DataInfo{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", dict_type='" + dict_type + '\'' +
                    ", cover='" + cover + '\'' +
                    ", posttime='" + posttime + '\'' +
                    ", typeName='" + typeName + '\'' +
                    ", url='" + url + '\'' +
                    ", hits='" + hits + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsList{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
