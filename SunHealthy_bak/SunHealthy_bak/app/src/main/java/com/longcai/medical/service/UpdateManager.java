package com.longcai.medical.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.bean.Update;
import com.longcai.medical.gloabe.Constant;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 版本更新
 * Created by LC-XC on 2016/10/20.
 */
public class UpdateManager {
    //版本更新接口
    private static final String PATH = "";

    private static final int DOWNLOADING = 1;
    private static final int DOWNLOAD_FINISH = 2;
    private static final String TAG = "UpdateManager";
    private String mVersion_code;//版本号
    private String mVersion_path;//下载apk地址
    private String mVersion_name;//名字
    private String mVersion_desc;//更新说明
    private String isCompulsion;//是否强制更新//1:强制更新 0:非强制

    private Context mContext;
    private ProgressBar mProgressBar;
    private AlertDialog mDownloadDialog;
    private String mSavePath;
    private int mProgress;
    private ProgressDialog progressDialog;

    private boolean mIsCancel = false;

    public UpdateManager(Context context) {
        mContext = context;
        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在更新");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();

            }
        });
    }

    public void getCheckUpdate(final String version, final boolean isToast, String url) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("os_type", Constant.osType);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getData: onSuccess   " + result);
                try {
                    JSONObject jso = new JSONObject(result);
                    String code = jso.getString("code");
                    String msg = jso.getString("msg");
                    isCompulsion = jso.getString("is_compulsion");
                    if (code.equals("1")) {
                        Gson gson = new Gson();
                        Update update = gson.fromJson(result, Update.class);
                        mVersion_code = update.result.version_code;
                        mVersion_path = update.result.download_url;
                        mVersion_desc = update.result.version;
                        //showNoticeDialog();
                        Log.e(TAG, "onSuccess:  ,version" + version + "   mVersion_code" + mVersion_code + "");
                        if (Float.parseFloat(mVersion_code) > Float.parseFloat(version)) {
                            showUpdateDialog(mVersion_path, isCompulsion);
                        }

                    }
                    if (isToast) {
                        ToastUtils.show(mContext, msg);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getData: onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getData: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getData: onFinished   ");

            }
        });

    }

    private void showUpdateDialog(final String updateurl, final String is_qiangzhi) {
        // 弹出对话框提示用户是否更新新版本
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("有可更新的版本");
        builder.setMessage("点击\"立即下载\",立即更新");
        builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 使用apkurl下载apk文件,然后自动安装
                downloadApk(updateurl, true);
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //is_qiangzhi 1:强制更新 0:非强制
                if (is_qiangzhi.equals("0")) {
                   // System.exit(0);
                } else if (is_qiangzhi.equals("1")) {
                    // 使用apkurl下载apk文件,然后自动安装
                    downloadApk(updateurl, false);
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void downloadApk(String url, final boolean isShowDialog) {
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(Environment.getExternalStorageDirectory()
                + "/ZHTX.apk");
        x.http().post(params, new Callback.ProgressCallback<File>() {


            @Override
            public void onSuccess(File result) {
                if (isShowDialog) {
                    progressDialog.dismiss();
                }

                // 下载成功后,得到下载后的文件,然后安装

                // 安装apk文件
                installApk(result);
                System.exit(0);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (isShowDialog) {
                    progressDialog.dismiss();
                    ex.printStackTrace();
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                if (isShowDialog) {
                    progressDialog.show();
                }

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                // 显示下载进度
                if (isShowDialog) {
                    int progress = (int) (current * 100 / total);
                    progressDialog.setProgress(progress);
                }
            }
        });

    }

    /**
     * 调用手机自带的安装软件功能
     */
    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("file://" + file), "application/vnd.android.package-archive");
        intent.addCategory("android.intent.category.DEFAULT");
        // 打开安装界面时等待着安装界面在关闭返回数据,只要有返回就表示取消了安装.
        ((Activity) mContext).startActivityForResult(intent, 0);
    }


}
