package com.zcx.helper.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by jingkang on 2017/10/14
 */

public abstract class AppV4Activity extends FragmentActivity {
    protected Context context;

    public AppV4Activity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;

//        try {
//            ((AppApplication)this.getApplication()).addActivity(this);
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }

    }

    protected void onResume() {
        super.onResume();
    }

    protected void onStop() {
        super.onStop();
        System.gc();
    }

    protected void onDestroy() {
        super.onDestroy();

//        try {
//            AppApplication e = (AppApplication)this.getApplication();
//            e.removeAppCallBack(this.getClass());
//            e.finishActivity(this);
//        } catch (Exception var2) {
//            var2.printStackTrace();
//        }

    }

//    public void setAppCallBack(AppCallBack appCallBack) {
//        try {
//            ((AppApplication)this.getApplication()).addAppCallBack(this.getClass(), appCallBack);
//        } catch (Exception var3) {
//            var3.printStackTrace();
//        }
//
//    }
//
//    public AppCallBack getAppCallBack(Class<?> cls) throws AppException {
//        AppCallBack appCallBack = null;
//
//        try {
//            appCallBack = ((AppApplication)this.getApplication()).getAppCallBack(cls);
//            return appCallBack;
//        } catch (Exception var4) {
//            throw new AppException("Application无法转换成AppApplication");
//        }
//    }
//
//    public void startVerifyActivity(Class<?> cls) {
//        this.startVerifyActivity(cls, new Intent());
//    }

    public void startVerifyActivity(Class<?> cls, Intent intent) {
        this.startActivity(intent.setClass(this, cls));
    }

    public void onClick(View view) {
    }
}
