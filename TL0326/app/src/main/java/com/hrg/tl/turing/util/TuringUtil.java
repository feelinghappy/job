package com.hrg.tl.turing.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.hrg.tl.util.LogUtil;


/**
 * 图灵机器人
 * 
 * @author shankes
 * 
 * @api:http://www.tuling123.com
 */
public class TuringUtil {

	protected static final String TAG = "TURING";

	private static String HTTP_URL = "http://www.tuling123.com/openapi/api";
	private static String APIKEY = "b5c15ac081f34b929ff252cc08f8a8d8";

	// * 注册之后在机器人接入页面获得
	private static String key;// * APIKEY
	// * 请求内容，编码方式为UTF-8长度1-30
	private static String info;// * 今天天气怎么样
	private static String loc;// 北京市中关村”，
	// 开发者给自己的用户分配的唯一标志（对应自己的每一个用户）
	// abc123（支持0-9，a-z,A-Z组合，不能包含特殊字符）
	private static String userid;// 123456

	// 拼接参数
	private static StringBuffer HTTP_ARG = null;

	private static void init(String infoValue, String locValue, String useridValue) {
		key = "key=KEY";
		info = "info=INFO";
		loc = "loc=LOC";
		userid = "userid=USERID";
		HTTP_ARG = new StringBuffer();

		// 1.key
		key = key.replace("KEY", APIKEY);
		HTTP_ARG.append(key);

		// 2.info
		info = info.replace("INFO", infoValue.trim());
		HTTP_ARG.append("&");
		HTTP_ARG.append(info);

		// 3.loc
		if (locValue != null && !"".equals(locValue.trim())) {
			loc = loc.replace("LOC", locValue.trim());
			HTTP_ARG.append("&");
			HTTP_ARG.append(loc);
		}// 4.userid
		if (useridValue != null && !"".equals(useridValue.trim())) {
			userid = userid.replace("USERID", useridValue.trim());
			HTTP_ARG.append("&");
			HTTP_ARG.append(userid);
		}

	}

	/**
	 * @param infoValue
	 *            请求内容，编码方式为UTF-8长度1-30,例如:今天天气怎么样
	 * @param locValue
	 *            地点
	 * @param useridValue
	 *            开发者给自己的用户分配的唯一标志（对应自己的每一个用户）
	 * @param textView
	 *            显示结果的文本框
	 */
	public static void turingPost(final Handler handler, String infoValue, String locValue, String useridValue) {
		if (TextUtils.isEmpty(infoValue)) {
			infoValue = "发送消息失败";
		}
		init(infoValue, locValue, useridValue);
		try {
			// get方法
			HttpUtilsAndroid.doGetAsyn(HTTP_URL + "?" + HTTP_ARG.toString(), new HttpUtilsAndroid.CallBack() {
				@Override
				public void onRequestComplete(String result) {
					try {
						LogUtil.e(result);
						JSONObject jsonObject = new JSONObject(result);
						Message msg = new Message();
						msg.what = jsonObject.getInt("code");
						Bundle bundle = new Bundle();
						bundle.putString("result", result);
						msg.setData(bundle);
						handler.sendMessage(msg);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			/*
			 * HttpUtils.doPostAsyn(HTTP_URL, HTTP_ARG.toString(), new
			 * CallBack() {
			 * 
			 * @Override public void onRequestComplete(String result) {
			 * textView.setText(result); } });
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}