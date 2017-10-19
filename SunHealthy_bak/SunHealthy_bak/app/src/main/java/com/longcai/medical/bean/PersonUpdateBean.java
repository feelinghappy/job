package com.longcai.medical.bean;

/**
 * Created by Administrator on 2017/7/7.
 */

public class PersonUpdateBean {
    /**
     * status : 1
     * message : 修改成功！
     * data : {"is_tj":"0","avatar":"http://cuitao05081.baidusx.com/Public/Console/images/home/2017/05/1494915493591a99a569eec.jpeg","sex":"1","age":"27","province":"山西省","province_id":"140000","city":"太原市","city_id":"140100","area":"小店区","area_id":"140105","height":"173","weight":"68","physica":"","nickname":"四班"}
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
         * is_tj : 0
         * avatar : http://cuitao05081.baidusx.com/Public/Console/images/home/2017/05/1494915493591a99a569eec.jpeg
         * sex : 1
         * age : 27
         * province : 山西省
         * province_id : 140000
         * city : 太原市
         * city_id : 140100
         * area : 小店区
         * area_id : 140105
         * height : 173
         * weight : 68
         * physica :
         * nickname : 四班
         */

        private String is_tj;
        private String avatar;
        private String sex;
        private String age;
        private String province;
        private String province_id;
        private String city;
        private String city_id;
        private String area;
        private String area_id;
        private String height;
        private String weight;
        private String physica;
        private String nickname;

        public String getIs_tj() {
            return is_tj;
        }

        public void setIs_tj(String is_tj) {
            this.is_tj = is_tj;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPhysica() {
            return physica;
        }

        public void setPhysica(String physica) {
            this.physica = physica;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
