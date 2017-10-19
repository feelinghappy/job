package com.longcai.medical.utils;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/7/4.
 */

public class keySHAE1Util {
    public static String TAG = "keySHAE1Util";

    public static String setSort(HashMap<String, String> parmas, String timestamp) {
        String resualt = "";
        String resualtSingle = "";
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                        // 升序排序
                        // return obj1.compareTo(obj2);
                    }
                });
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = parmas.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = parmas.get(parmasKey);
            map.put(parmasKey, parmasValue);

        }
        /*全局必传参数*/
        map.put("client_type", "android"); //平台标示
        map.put("_timestamp", timestamp);//时间戳

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            LogUtils.i(TAG, key + ":" + map.get(key));
            resualt = key + "=" + encode(map.get(key)) + "&";
            resualtSingle = resualtSingle + resualt;

        }

        LogUtils.i(TAG, "setSort: resualt  " + resualt);
        String single = resualtSingle.trim().substring(0, resualtSingle.length() - 1);
        LogUtils.i(TAG, "setSort: share 1 resualt " + single);

        return encryptToSHA(single);
    }

    /**
     * treeMap排序
     */
    public static String setSort(HashMap<String, String> parmas) {
        String resualt = "";
        String resualtSingle = "";
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                        // 升序排序
                        // return obj1.compareTo(obj2);
                    }
                });
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = parmas.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = parmas.get(parmasKey);
            map.put(parmasKey, parmasValue);

        }
        /*全局必传参数*/
        map.put("client_type", "android"); //平台标示
        map.put("_timestamp", StringUtils.refFormatNowDate());//时间戳

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
            resualt = key + "=" + encode(map.get(key)) + "&";
            resualtSingle = resualtSingle + resualt;

        }

        Log.i(TAG, "setSort: resualt  " + resualt);
        String single = resualtSingle.trim().substring(0, resualtSingle.length() - 1);
        Log.i(TAG, "setSort: share 1 resualt " + single);
        return encryptToSHA(single);
    }


    public static String encryptToSHA(String info) {
        Log.i(TAG, "encryptToSHA:  info  " + info);
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String rs = byte2hex(digesta);
        return rs;
    }

    public static String encode(String rawStr) {
        String result = "";
        try {
            result = URLEncoder.encode(rawStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decode(String rawStr) {
        String result = "";
        try {
            result = URLDecoder.decode(rawStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        Log.e(TAG, "KeySHA1Utils: byte2hex: "+hs );
        return hs;
    }

}
