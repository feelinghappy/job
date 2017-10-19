package com.longcai.medical.bean;
      /*
          {status : 1
          message : 成功
          data : {"pages":1,
                  "next":2,
                  "total":"0",
                  "activis":[
                            {"id":"7",
                            "title":"太原市青年路小学开展班级文化建设活动",
                            "recommend":"1",
                             "cover":"http://app28.app.longcai.net/Public/Uploads/20161215/ac_5851f7dacae09.jpg",
                            "time":"2016-12-15",
                             "province":"",
                             "city":"天津市,石家庄市,太原市",
                             "area":"",
                              "addr":"青年路小学",
                               "url":"",
                               "status":0}]}}*/

import java.util.List;

public class Center {
	public int status;
	public String message;
	public CenterData data;



	public  class CenterData{
		public int pages;
		public int next;
		public String total;
		public List<ActivisBean> activis;

		@Override
		public String toString() {
			return "CenterData{" +
					"pages=" + pages +
					", next=" + next +
					", total='" + total + '\'' +
					", activis=" + activis +
					'}';
		}
	}
	public  class ActivisBean{
		public String id;
		public String title;
		public String recommend;
		public String cover;
		public String time;
		public String province;
		public String city;
		public String area;
		public String addr;
		public String url;
		public int status;

		@Override
		public String toString() {
			return "ActivisBean{" +
					"id='" + id + '\'' +
					", title='" + title + '\'' +
					", recommend='" + recommend + '\'' +
					", cover='" + cover + '\'' +
					", time='" + time + '\'' +
					", province='" + province + '\'' +
					", city='" + city + '\'' +
					", area='" + area + '\'' +
					", addr='" + addr + '\'' +
					", url='" + url + '\'' +
					", status=" + status +
					'}';
		}
	}

	@Override
	public String toString() {
		return "Center{" +
				"status=" + status +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}

}
