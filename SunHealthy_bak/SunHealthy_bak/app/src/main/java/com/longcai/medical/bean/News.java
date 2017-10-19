package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
/*{
    "status": 1,
    "message": "成功",
    "data": [
        {
            "id": "20",
            "text": "婴幼儿"
        }
    ]
}*/
public class News {
    public int status;
    public String message;
    public List<NewsInfo> data;
    public  class NewsInfo{
        public String id;
        public String text;

        @Override
        public String toString() {
            return "NewsInfo{" +
                    "id='" + id + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", result=" + data +
                '}';
    }
}
