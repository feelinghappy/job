package com.longcai.medical.bean;

import java.util.List;

/*
         status : 1
         message : 成功
         data : {
                "pages":1,
                "next":2,
                "total":"4",
                "activis":[
                        {"actvityId":"2",
                        "title":"民航小区居民参与旅游",
                        "recommend":"1",
                        "cover":"http://app28.app.longcai.net/Public/Uploads/20161210/ac_584bc1b636f2d.jpg",
                        "time":"2016-12-15",
                        "province":"山西省,江苏省,海南省,西藏自治区",
                        "city":"太原市,晋城市,吕梁市,南京市,无锡市,徐州市,常州市,苏州市,南通市,连云港市,淮安市,盐城市,扬州市,镇江市,泰州市,宿迁市,海口市,三亚市,省直辖县级行政单位,那曲地区,阿里地区,林芝地区",
                        "area":"",
                        "addr":"太航小区广场",
                        "partName":"",
                        "phone":"",
                        "partId":"2",
                        "url":"http://app28.app.longcai.net/index.php/Api/Activity/index?id=2"}]}
        */
public class MyCenter {
	public int status;
	public String message;
	public MyCenterData data;

	public  class MyCenterData {
		public int pages;
		public int next;
		public String total;
		public List<ActivisBean> activis;

		@Override
		public String toString() {
			return "MyCenterData{" +
					"pages=" + pages +
					", next=" + next +
					", total='" + total + '\'' +
					", activis=" + activis +
					'}';
		}
	}
	public  class ActivisBean {
		public String actvityId;
		public String title;
		public String recommend;
		public String cover;
		public String time;
		public String province;
		public String city;
		public String area;
		public String addr;
		public String partName;
		public String phone;
		public String partId;
		public String url;

		@Override
		public String toString() {
			return "ActivisBean{" +
					"actvityId='" + actvityId + '\'' +
					", title='" + title + '\'' +
					", recommend='" + recommend + '\'' +
					", cover='" + cover + '\'' +
					", time='" + time + '\'' +
					", province='" + province + '\'' +
					", city='" + city + '\'' +
					", area='" + area + '\'' +
					", addr='" + addr + '\'' +
					", partName='" + partName + '\'' +
					", phone='" + phone + '\'' +
					", partId='" + partId + '\'' +
					", url='" + url + '\'' +
					'}';
		}
	}

	@Override
	public String toString() {
		return "MyCenter{" +
				"status=" + status +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}
