package com.longcai.medical.bean;


public class Health {
	/*
	  { 
     "code" : 1, 
     "msg" : "获取成功", 
     "result" : { 
          "sleep_data" : { 
               "sleep_time" : "24468", //睡眠时间
               "sleep_grade" : "4" //睡眠等级 1.优秀 2. 良好,3.一般,4.不好5. 极不好
          }, 
          "heart_data" : { 
               "avg_heart" : "78", //平均心率
               "min_heart" : "59", //最低心率
               "max_heart" : "108" //最高心率
          }, 
          "blood_data" : { 
               "systolic" : "115", //收缩压
               "diastolic" : "75" //舒张压
               "create_time": "02 :08pm"//测量时间
          }, 
          "sport_data" : { 
               "step_num" : "2133", //步数
               "calory" : "89" //消耗卡路里
          } 
     } 
 }
	 */


	public int code;
	public String msg;
	public HealthResult result;

	public class HealthResult {
		public SleepData sleep_data;
		public HeartData heart_data;  
		public BloodData blood_data;
		public SportData sport_data;
		@Override
		public String toString() {
			return "HealthResult [sleep_data=" + sleep_data + ", heart_data="
					+ heart_data + ", blood_data=" + blood_data
					+ ", sport_data=" + sport_data + "]";
		}
		

	}
	/**
	 * 睡眠
	 * @author Administrator
	 *
	 */
	public class SleepData {
		public String sleep_time;
		public String sleep_grade;
		@Override
		public String toString() {
			return "SleepData [sleep_time=" + sleep_time + ", sleep_grade="
					+ sleep_grade + "]";
		} 
		  
		
	}
	/**
	 * 心率
	 * @author Administrator
	 *
	 */
	public class HeartData {
		public String avg_heart;
		public String min_heart;
		public String max_heart;
		@Override
		public String toString() {
			return "HeartData [avg_heart=" + avg_heart + ", min_heart="
					+ min_heart + ", max_heart=" + max_heart + "]";
		}
		
	}
	/**
	 * 血压
	 * @author Administrator
	 *
	 */
	public class BloodData {
		public String systolic;
		public String diastolic;
		public String create_time;

		@Override
		public String toString() {
			return "BloodData [systolic=" + systolic + ", diastolic="
					+ diastolic + "]";
		} 
		
	}
	/**
	 * 运动
	 * @author Administrator
	 *
	 */
	public class SportData {
		public String step_num;
		public String calory;
		@Override
		public String toString() {
			return "SportData [step_num=" + step_num + ", calory=" + calory
					+ "]";
		} 
		
	}
	@Override
	public String toString() {
		return "Health [code=" + code + ", msg=" + msg + ", result=" + result
				+ "]";
	}



}
