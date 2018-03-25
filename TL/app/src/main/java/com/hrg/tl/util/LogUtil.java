package com.hrg.tl.util;

import android.util.Log;

/**
 * Log统一管理类
 * <p>
 * Created by shankes on 2016/9/4.
 */
public class LogUtil {

	private LogUtil() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "shankes";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.i(TAG, msg);
			} else {
				Log.e(TAG, "msg为空");
			}
	}

	public static void d(String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.d(TAG, msg);
			} else {
				Log.e(TAG, "msg为空");
			}
	}

	public static void e(String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.e(TAG, msg);
			} else {
				Log.e(TAG, "msg为空");
			}
	}

	public static void v(String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.v(TAG, msg);
			} else {
				Log.e(TAG, "msg为空");
			}
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.i(tag, msg);
			} else {
				Log.e(tag, "msg为空");
			}
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.d(tag, msg);
			} else {
				Log.e(tag, "msg为空");
			}
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.e(tag, msg);
			} else {
				Log.e(tag, "msg为空");
			}
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			if (msg != null && !"".equalsIgnoreCase(msg)) {
				Log.v(tag, msg);
			} else {
				Log.e(tag, "msg为空");
			}
	}
}
