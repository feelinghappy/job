package com.longcai.medical.utils.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by jingkang on 2017/10/14
 */

public abstract class AppPreferences {
    private Secret<String, String> s;
    private SharedPreferences.Editor a;
    private SharedPreferences b;

    public AppPreferences(Context context, String name) {
        this.s = new SecretAESDESede(UtilMD5.MD5Encode(UtilApp.getPackageInfo(context).packageName, "UTF-8"), "AES/CBC/PKCS7Padding");
        this.b = context.getSharedPreferences(UtilMD5.MD5Encode(name, "UTF-8"), 0);
        this.a = this.b.edit();
    }

    public void putBoolean(String arg0, boolean arg1) {
        this.a.putBoolean(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1).commit();
    }

    public void putFloat(String arg0, float arg1) {
        this.a.putFloat(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1).commit();
    }

    public void putInt(String arg0, int arg1) {
        this.a.putInt(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1).commit();
    }

    public void putLong(String arg0, long arg1) {
        this.a.putLong(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1).commit();
    }

    public void putString(String arg0, String arg1) {
        this.a.putString(UtilMD5.MD5Encode(arg0, "UTF-8"), (String)this.s.encrypt(arg1)).commit();
    }

    public void putStringSet(String arg0, Set<String> arg1) {
        this.a.putStringSet(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1).commit();
    }

    public boolean getBoolean(String arg0, boolean arg1) {
        return this.b.getBoolean(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1);
    }

    public float getFloat(String arg0, float arg1) {
        return this.b.getFloat(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1);
    }

    public int getInt(String arg0, int arg1) {
        return this.b.getInt(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1);
    }

    public long getLong(String arg0, long arg1) {
        return this.b.getLong(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1);
    }

    public String getString(String arg0, String arg1) {
        return (String)this.s.decrypt(this.b.getString(UtilMD5.MD5Encode(arg0, "UTF-8"), (String)this.s.encrypt(arg1)));
    }

    public Set<String> getStringSet(String arg0, Set<String> arg1) {
        return this.b.getStringSet(UtilMD5.MD5Encode(arg0, "UTF-8"), arg1);
    }

    public void clear() {
        this.a.clear().commit();
    }
}
