package com.zcx.helper.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by jingkang on 2017/10/14
 */

public final class UtilSDCard {
    private UtilSDCard() {
    }

    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }
}
