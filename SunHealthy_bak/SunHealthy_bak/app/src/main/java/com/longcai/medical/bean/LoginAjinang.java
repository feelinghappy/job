package com.longcai.medical.bean;

public class LoginAjinang {
    /*{
      "status": 1,
      "message": "\u767b\u5f55\u6210\u529f\uff01",
      "data": {
        "uid": "DA8F4DACD41713A2BB8126FF3FA5048E",
        "is_tj": "0",
        "avatar": "http:\/\/v2.healthywo.com\/Public",
        "sex": "1",
        "age": "30",
        "province": "",
        "province_id": "",
        "city": "",
        "city_id": "",
        "area": "",
        "area_id": "",
        "height": "0",
        "weight": "0",
        "nickname": "jiang152",
        "physica": ""
      }
    }*/
    public int status;
    public String message;
    public LoginResult data;

    public class LoginResult {
        public String uid;  //消息id
        public String is_tj;  //消息id
        public String avatar;  //消息id
        public String sex;  //消息id
        public String age;  //消息id
        public String province;  //消息id
        public String province_id;  //消息id
        public String city;
        public String city_id;
        public String area;
        public String area_id;
        public String height;
        public String weight;
        public String nickname;
        public String physica;

        @Override
        public String toString() {
            return "uid:" + uid + "，nickname:" + nickname;
        }
    }

    @Override
    public String toString() {
        return "status:" + status + "" + data.toString();
    }
}
