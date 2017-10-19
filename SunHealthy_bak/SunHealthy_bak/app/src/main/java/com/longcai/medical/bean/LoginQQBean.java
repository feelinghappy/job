package com.longcai.medical.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class LoginQQBean {
    /**
     * status : 1
     * message : 登录成功！
     * data : {"uid":"5927F51DFC8F76353D077CB1765338A7","is_tj":"0","is_man":"0","password":"74be16979710d4c4e7c6647856088456","avatar":"http://cuitao05081.baidusx.com/Public/Console/images/home/2017/06/1496308543592fdb3f4b76b.jpeg","sex":"2","age":"30","province":"山西省","province_id":"140000","city":"太原市","city_id":"140100","area":"小店区","area_id":"140105","height":"170","weight":"70","nickname":"古董车","physica":""}
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
         * uid : 5927F51DFC8F76353D077CB1765338A7
         * is_tj : 0
         * is_man : 0
         * password : 74be16979710d4c4e7c6647856088456
         * avatar : http://cuitao05081.baidusx.com/Public/Console/images/home/2017/06/1496308543592fdb3f4b76b.jpeg
         * sex : 2
         * age : 30
         * province : 山西省
         * province_id : 140000
         * city : 太原市
         * city_id : 140100
         * area : 小店区
         * area_id : 140105
         * height : 170
         * weight : 70
         * nickname : 古董车
         * physica :
         */

        private String uid;
        private String is_tj;
        private String is_man;
        private String password;
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
        private String nickname;
        private String physica;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIs_tj() {
            return is_tj;
        }

        public void setIs_tj(String is_tj) {
            this.is_tj = is_tj;
        }

        public String getIs_man() {
            return is_man;
        }

        public void setIs_man(String is_man) {
            this.is_man = is_man;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhysica() {
            return physica;
        }

        public void setPhysica(String physica) {
            this.physica = physica;
        }
    }
}

