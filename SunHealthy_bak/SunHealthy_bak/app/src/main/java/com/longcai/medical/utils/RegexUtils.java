package com.longcai.medical.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.longcai.medical.MyApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jingkang on 2017/10/7
 */

public class RegexUtils {

    public static boolean checkMobile(String str) {
        if (str == null)
            return false;
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public static boolean isIdCard(String str) {
        if (str == null)
            return false;
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    // 检测当前有无可用网络
    public static boolean isNetworkConnected() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            Context context = MyApplication.getInstance();
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.v(e.toString());
        }
        return false;
    }
}
