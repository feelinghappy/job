package com.longcai.medical.utils;

import android.app.ProgressDialog;
import android.content.Context;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.longcai.medical.MyApplication.mContext;

/**
 * Created by Administrator on 2017/7/5.
 */

public class XutilGloabeParamsUtil {

    public static ProgressDialog mProgressDialog = null;
    private static File file;

    public static RequestParams setRobotListRequestParams(Context mContext, String url, HashMap<String, String> map) {
        //TODO Dialog开始
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(mContext, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.setCharset("UTF-8");
        //添加全局必填参数
        params.addParameter("client_type", "android");//平台标示
        String timestamp = StringUtils.refFormatNowDate();
        params.addParameter("_timestamp", timestamp);//时间戳
        params.addParameter("signature", keySHAE1Util.setSort(map, timestamp));//签名（需经过，除了签名参数，其他参数的运算）
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        return params;
    }

    public static RequestParams setTokenRequestParams(Context mContext, String url, HashMap<String, String> map) {
        //TODO Dialog开始
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(mContext, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.setCharset("UTF-8");
        //添加全局必填参数
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        return params;
    }

    //参数说明   url 访问的路径   parmas 除全局必传参数的其他参数 集合
    public static RequestParams setRequestParams(Context mContext, String url, HashMap<String, String> map) {
        //TODO Dialog开始
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(mContext, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.setCharset("UTF-8");
        //添加全局必填参数
        params.addParameter("client_type", "android");//平台标示
        String timestamp = StringUtils.refFormatNowDate();
        params.addParameter("_timestamp", timestamp);//时间戳
        params.addParameter("signature", keySHAE1Util.setSort(map, timestamp));//签名（需经过，除了签名参数，其他参数的运算）
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        return params;
    }

    public static RequestParams setRequestParamsWithoutDialog(Context mContext, String url, HashMap<String, String> map) {
        //TODO Dialog开始

        RequestParams params = null;
        params = new RequestParams(url);
        params.setAsJsonContent(true);
        params.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.setCharset("UTF-8");
        //添加全局必填参数
        params.addParameter("client_type", "android");//平台标示
        String timestamp = StringUtils.refFormatNowDate();
        params.addParameter("_timestamp", timestamp);//时间戳
        params.addParameter("signature", keySHAE1Util.setSort(map, timestamp));//签名（需经过，除了签名参数，其他参数的运算）
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        return params;
    }

    public static RequestParams setRequestParamsWithFile(Context mContext, String url, List<String> paths, HashMap<String, String> map) {
        //TODO Dialog开始
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(mContext, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        //添加全局必填参数
        params.addParameter("client_type", "android");//平台标示
        String timestamp = StringUtils.refFormatNowDate();
        params.addParameter("_timestamp", timestamp);//时间戳
        params.addParameter("signature", keySHAE1Util.setSort(map, timestamp));//签名（需经过，除了签名参数，其他参数的运算）
        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        //上传文件
        String imagpath = paths.get(0).substring(0, paths.get(0).lastIndexOf("/"));
        String imgName []=paths.get(0).split("/");
        for(int i=0;i<imgName.length;i++){
            if(i==imgName.length-1){
                String name=imgName[i];
                file = new File(imagpath, name);
            }
        }
        params.setAsJsonContent(true);
        List<KeyValue> list = new ArrayList<>();
        if (paths.size() == 1) {
            list.add(new KeyValue("avatar", file));
        } else {
            for (int i = 0; i < paths.size(); i++) {
                list.add(new KeyValue("photo"+(i+1),file.getName()));
            }
        }
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        return params;
    }

    public static RequestParams setRequestParamsWithFile(Context mContext, String url, HashMap<String, String> fileMap, HashMap<String, String> map) {
        //TODO Dialog开始
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(mContext, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        //添加全局必填参数
        params.addParameter("client_type", "android");//平台标示
        String timestamp = StringUtils.refFormatNowDate();
        params.addParameter("_timestamp", timestamp);//时间戳
        params.addParameter("signature", keySHAE1Util.setSort(map, timestamp));//签名（需经过，除了签名参数，其他参数的运算）

        params.setAsJsonContent(true);
        //上传文件
        Set<String> keySet = fileMap.keySet();
        List<KeyValue> list = new ArrayList<>();
        for (String key : keySet) {
            String filePath = fileMap.get(key);
            File file = new File(filePath);
            list.add(new KeyValue(key, file));
        }

        //动态添加所需的非必须参数
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
//            params.addParameter(parmasKey, parmasValue);
            list.add(new KeyValue(parmasKey, parmasValue));
        }

        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
        return params;
    }

    //加载框取消
    public static void progressDialogCancel() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public static RequestParams setLocalRequestParams(Context context, String url, HashMap<String, String> map) {
        if (null == mProgressDialog || !mProgressDialog.isShowing()) {
            mProgressDialog = ProgressDialog.show(context, "", "正在加载...");
            mProgressDialog.setCancelable(true);
        }

        RequestParams params = null;
        params = new RequestParams(url);
        Set<String> parmasKeySet = map.keySet();
        Iterator<String> parmasIterator = parmasKeySet.iterator();
        while (parmasIterator.hasNext()) {
            String parmasKey = parmasIterator.next();
            String parmasValue = map.get(parmasKey);
            params.addParameter(parmasKey, parmasValue);
        }
        return params;
    }
}
