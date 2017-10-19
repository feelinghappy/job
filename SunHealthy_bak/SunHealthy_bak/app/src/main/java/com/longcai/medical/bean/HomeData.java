package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
 /*{
    "status": 1,
    "message": "成功",
    "data": {
        "banners": [
            {
                "title": "APP首页Banner",
                "event": "0",
                "url": "",
                "imgpath": "http://code.healthywo.com/Public/Uploads/20170616/ad_5943396b50ada.png"
            }
        ],
        "infos": {
            "id": "6",
            "cover": "http://code.healthywo.com/Public/Uploads/20161215/diet_5851fee8363cc.jpg",
            "name": "大米红枣粥",
            "details": "1. 将大米淘洗干净用水浸泡2个小时，红枣洗净一起浸泡。 　　提示：泡水可使米粒充分吸收水分，这样才会煮出又软又稠的粥，也便于消化吸收。 \r\n\r\n　　2. 将大米、红枣、核桃仁一起下锅用旺火煮，沸腾后调至小火慢煮10-15分钟。 　　3. 出锅前放入冰糖搅拌均匀，红枣大米粥-红枣核桃粥可以出锅了。",
            "url": "http://code.healthywo.com/index.php/api/Activity/recipe_show.html?id=6",
            "tiz": ""
        },
        "sports": [
            "4000"
        ]
    }
}*/
public class HomeData {
    public int status;
    public String message;
    public HomePageData data;
    public class HomePageData {
        public List<DataBanners> banners;
        public DataInfos infos;
        public List<String> sports;

        @Override
        public String toString() {
            return "HomePageData{" +
                    "banners=" + banners +
                    ", infos=" + infos +
                    ", sports=" + sports +
                    '}';
        }
    }
    public class DataBanners {
        public String title;
        public String event;
        public String url;
        public String imgpath;

        @Override
        public String toString() {
            return "DataBanners{" +
                    "title='" + title + '\'' +
                    ", event='" + event + '\'' +
                    ", url='" + url + '\'' +
                    ", imgpath='" + imgpath + '\'' +
                    '}';
        }
    }
    public class DataInfos {
        public String id;
        public String cover;
        public String name;
        public String details;
        public String url;
        public String tiz;

        @Override
        public String toString() {
            return "DataInfos{" +
                    "id='" + id + '\'' +
                    ", cover='" + cover + '\'' +
                    ", name='" + name + '\'' +
                    ", details='" + details + '\'' +
                    ", url='" + url + '\'' +
                    ", tiz='" + tiz + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

