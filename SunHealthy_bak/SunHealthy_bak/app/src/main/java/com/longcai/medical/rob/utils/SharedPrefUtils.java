package com.longcai.medical.rob.utils;

import android.content.Context;

/**
 * 
 * @author cf
 *
 */
public class SharedPrefUtils {
	
	private static final String FILE_NAME            = "robot";
	public static String EMAIL = "email";
	/**
	 * 保存任何键值对
	 * @param mContext
	 * @param key
	 * @param value
	 */
	public static void saveConfigInfo(Context mContext, String key, String value) {
		mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putString(key, value).apply();
	}
	
	/**
	 * 获取key的值
	 * @param mContext
	 * @param key
	 * @return
	 */
	public static String getConfigInfo(Context mContext, String key) {
		String ret = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, null);
		return ret;
	}

}
