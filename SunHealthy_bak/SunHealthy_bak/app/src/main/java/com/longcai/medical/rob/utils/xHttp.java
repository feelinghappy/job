package com.longcai.medical.rob.utils;

import android.content.Context;
import android.text.TextUtils;

import com.longcai.medical.bean.BaseResult;
import com.longcai.medical.MyApplication;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.utils.XutilGloabeParamsUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

/**
 * Created by liutao on 2017/9/30.
 */

public class xHttp {
    public static <T> void post(final Context context, String url, HashMap<String, String> map, final Class<T> cls, final HttpUtils.OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setTokenRequestParams(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                XutilGloabeParamsUtil.progressDialogCancel();
                LogUtils.d(result);
                BaseResult<T> baseResult = null;
                try {
                    baseResult = GsonUtils.toBaseResult(result, cls);
                } catch (Exception e) {
                    baseResult = GsonUtils.GsonToBean(result, BaseResult.class);
                }
                String code = baseResult.getCode();
                String msg = baseResult.getMsg();
                if (code.equals("200")) {
                    T t = baseResult.getResult();
                    if (null != t && null != callBack) {
                        try {
                            callBack.onSuccess(t);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        callBack.onSuccessMsg(msg);
                    }
                } else {
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        int codeInt = StringUtils.parseInt(code);
                        if (codeInt == 10004) {
                            ToastUtils.showToast(context, "登录失效，请重新登录");
                            MyApplication.myPreferences.clear();
                            MyApplication.myPreferences.saveToken("-1");
                        }
                        callBack.onFail(codeInt, codeInt+"," + msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "onError" + ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1001, "onCancelled");
                }
            }

            @Override
            public void onFinished() {
                XutilGloabeParamsUtil.progressDialogCancel();
            }
        });
    }

}
