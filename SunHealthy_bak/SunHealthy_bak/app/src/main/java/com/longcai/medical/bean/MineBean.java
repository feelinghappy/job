package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MineBean {

    /**
     * status : 1
     * message : 成功
     * data : {"tel":"13111168085","ask":"http://cuitao05081.baidusx.com/index.php/api/Activity/ask.html","lian":"http://cuitao05081.baidusx.com/index.php/api/Activity/lian.html","about":"http://cuitao05081.baidusx.com/index.php/api/Activity/about.html"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * tel : 13111168085
         * ask : http://cuitao05081.baidusx.com/index.php/api/Activity/ask.html
         * lian : http://cuitao05081.baidusx.com/index.php/api/Activity/lian.html
         * about : http://cuitao05081.baidusx.com/index.php/api/Activity/about.html
         */

        private String tel;
        private String ask;
        private String lian;
        private String about;

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }

        public String getLian() {
            return lian;
        }

        public void setLian(String lian) {
            this.lian = lian;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }
    }
}
