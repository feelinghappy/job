package com.longcai.medical.utils.helper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.List;

/**
 * Created by jingkang on 2017/10/14
 */

public final class UtilApp {
    private UtilApp() {
    }

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packInfo = null;

        try {
            packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return packInfo;
    }

    public static String appName(Context context) {
        try {
            int e = getPackageInfo(context).applicationInfo.labelRes;
            return context.getResources().getString(e);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static String versionName(Context context) {
        try {
            return getPackageInfo(context).versionName;
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List packageInfos = packageManager.getInstalledPackages(0);
        if(packageInfos != null) {
            for(int i = 0; i < packageInfos.size(); ++i) {
                if(((PackageInfo)packageInfos.get(i)).packageName.equals(packageName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void call(Context context, String number) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + number)));
    }

    public static void callQQ(Context context, String qqNumber) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber + "&version=1")));
    }
}
