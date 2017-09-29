package com.hrg.bletest;

import java.util.LinkedList;
import java.util.Timer;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;

/**
 * 自己实现Application，实现数据共享
 * 
 * @author jason
 */
public class MyApplication extends Application {
	// 共享变量
	private Handler handler1 = null;
	private Handler handler2 = null;
	private Handler handler3 = null;
	private Handler handler4 = null;
	private Handler handler5 = null;// 动态跟随
	private Timer timer = null;
	private Activity mActivity = null;
	private LinkedList<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;

	public static MyApplication getActivityInstance() {
		if (null == instance) {
			instance = new MyApplication();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// 遍历所有Activity并finish

	public void exit() {

		for (Activity activity : activityList) {
			activity.finish();
		}

		System.exit(0);

	}

	// set方法
	public void setHandler1(Handler handler) {
		this.handler1 = handler;
	}

	// set方法
	public void setHandler2(Handler handler) {
		this.handler2 = handler;
	}

	public void setHandler3(Handler handler) {
		this.handler3 = handler;
	}

	public void setHandler4(Handler handler) {
		this.handler4 = handler;
	}

	public void setHandler5(Handler handler) {
		this.handler5 = handler;
	}

	// get方法
	public Handler getHandler1() {
		return handler1;
	}

	public Handler getHandler2() {
		return handler2;
	}

	public Handler getHandler3() {
		return handler3;
	}

	public Handler getHandler4() {
		return handler4;
	}

	public Handler getHandler5() {
		return handler5;
	}

	public void setInstance(Activity instance) {
		mActivity = instance;
	}

	public Activity getInstance() {
		return mActivity;
	}


}