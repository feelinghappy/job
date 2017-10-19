package com.longcai.medical.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtil {
	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	static String mHours = "0";

	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		mHours = hours + "";
		String time = days + " days " + hours + " hours " + minutes
				+ " minutes " + seconds + " seconds ";
		if (seconds > 0 && seconds < 30) {
		}
		if (hours < 1 && minutes > 0 && minutes < 60) {
			mHours = "0.5";
		}
		if (hours < 1 && minutes < 1 && seconds > 0) {
			mHours = "0.1";
		}
		LogUtils.i("TimeUtil", time);
		return mHours;
	}

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	public static void setYearMonthDay() {
		//获取 当前日期前三十天的日期    倒序   可  年、月、日
		Calendar c = Calendar.getInstance();
		String[] monthday = new String[30];
		String[] days = new String[30];
		String[] yearmonthday = new String[30];
		yearMonthDay = new ArrayList<>();
		monthsDay = new ArrayList<>();
		day = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			yearmonthday[i] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(
					c.getTimeInMillis()));
			monthday[i] = new SimpleDateFormat("MM-dd").format(new Date(
					c.getTimeInMillis()));
			days[i] = new SimpleDateFormat("dd").format(new Date(
					c.getTimeInMillis()));
			c.add(Calendar.DAY_OF_MONTH, -1);
			yearMonthDay.add(yearmonthday[i]);
			monthsDay.add(monthday[i]);
			day.add(days[i]);
		}
	}
	private static List<String> yearMonthDay;
	private static List<String> monthsDay;
	private static List<String> day;
	public static List<String> getYearMonthDay() {
		setYearMonthDay();
		return yearMonthDay;
	}
	public static List<String> getMonthsDay() {
		setYearMonthDay();
		return monthsDay;
	}
	public static List<String> getDay() {
		setYearMonthDay();
		return day;
	}
}
