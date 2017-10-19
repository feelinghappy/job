package com.longcai.medical.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/7.
 */

public class MineCollectListBean {
    /**
     * status : 1
     * message : 成功
     * data : [{"title":"肺火旺怎么调理 食疗最有效","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161031/info_581705888c1d7.png","hits":"32","dict_type":"22","posttime":"2016-10-31 16:45:20","id":"9","dict":"食疗","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/22/order/time/id/9.html"},{"title":"关于美白的美容知识","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161225/info_585f3b3a068b8.jpg","hits":"7","dict_type":"23","posttime":"2016-12-25 11:19:41","id":"13","dict":"美容","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/23/order/time/id/13.html"},{"title":"老年人饮食有讲究 需遵循\u201c八不贪\u201d原则","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161029/info_58145fe7c7a41.jpg","hits":"110","dict_type":"21","posttime":"2016-10-29 16:34:17","id":"6","dict":"老人","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/21/order/time/id/6.html"},{"title":"无情的风趣发给我个不是热固化让他和计划 很热特价活动和温柔一天天外国友人我如果合适的放不下对方比你大发生过","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161216/info_5853b5e565737.jpg","hits":"59","dict_type":"21","posttime":"2016-12-16 17:37:13","id":"12","dict":"老人","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/21/order/time/id/12.html"},{"title":"中医治疗宝宝伤风感冒的办法","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161029/info_58145d549c802.jpg","hits":"73","dict_type":"20","posttime":"2016-10-29 16:25:09","id":"2","dict":"婴幼儿","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/20/order/time/id/2.html"},{"title":"保健养生要从婴幼儿做起","cover":"http://cuitao05081.baidusx.com/Public/Uploads/20161029/info_58145c4b61dd7.png","hits":"8","dict_type":"20","posttime":"2016-10-29 16:15:16","id":"1","dict":"婴幼儿","url":"http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/20/order/time/id/1.html"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 肺火旺怎么调理 食疗最有效
         * cover : http://cuitao05081.baidusx.com/Public/Uploads/20161031/info_581705888c1d7.png
         * hits : 32
         * dict_type : 22
         * posttime : 2016-10-31 16:45:20
         * id : 9
         * dict : 食疗
         * url : http://cuitao05081.baidusx.com/index.php/Api/Other/infomation/type/22/order/time/id/9.html
         */

        private String title;
        private String cover;
        private String hits;
        private String dict_type;
        private String posttime;
        private String id;
        private String dict;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getHits() {
            return hits;
        }

        public void setHits(String hits) {
            this.hits = hits;
        }

        public String getDict_type() {
            return dict_type;
        }

        public void setDict_type(String dict_type) {
            this.dict_type = dict_type;
        }

        public String getPosttime() {
            return posttime;
        }

        public void setPosttime(String posttime) {
            this.posttime = posttime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDict() {
            return dict;
        }

        public void setDict(String dict) {
            this.dict = dict;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
