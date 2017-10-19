package com.longcai.medical.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.BaseListResult;
import com.longcai.medical.bean.BaseResult;
import com.longcai.medical.ui.activity.LoginActivity;
import com.longcai.medical.MyApplication;
import android.content.Context;
import org.xutils.common.Callback;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/17.
 */
public class HttpUtils<T> {


    /**
     *从服务端获取登录野狗sdk的token
     * @param context
     * @param url
     * @param map
     * @param cls
     * @param callBack
     * @param <T>
     */

    public static <T> void xListRobotsHttpPost(final Context context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpWithListResultCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRobotListRequestParams(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                XutilGloabeParamsUtil.progressDialogCancel();
                LogUtils.d(result);
                BaseListResult<T> baseResult = null;
                try {
                    baseResult = GsonUtils.toBaseListResult(result, cls);
                } catch (Exception e) {
                    baseResult = GsonUtils.GsonToBean(result, BaseListResult.class);
                }
                String code = baseResult.getCode();
                String msg = baseResult.getMsg();
                if (code.equals("200")) {
                    List<T> t = baseResult.getResult();
                    if (null != t && null != callBack) {
                        callBack.onSuccess(t);
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
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
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

    /**
     *从服务端获取登录野狗sdk的token
     * @param context
     * @param url
     * @param map
     * @param cls
     * @param callBack
     * @param <T>
     */
    public static <T> void xTokenHttpPost(final Context context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpCallBack<T> callBack) {
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
    public static <T> void xOverallHttpPost(final Activity context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParams(context, url, map), new Callback.CommonCallback<String>() {
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
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
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

    private static void loginAgain(Activity context) {
//        ToastUtils.showToast(context, "登录失效，请重新登录");
        // TODO ?wheather clear
        // MyApplication.myPreferences.clear();
        MyApplication.myPreferences.saveToken("-1");
        Intent intent = new Intent(context, LoginActivity.class);
        MyApplication.source = -1;
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.pop_enter_anim, 0);
    }

    public static <T> void xOverallHttpPost(final Activity context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpWithListResultCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParams(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                XutilGloabeParamsUtil.progressDialogCancel();
                LogUtils.d(result);
                BaseListResult<T> baseResult = null;
                try {
                    baseResult = GsonUtils.toBaseListResult(result, cls);
                } catch (Exception e) {
                    baseResult = GsonUtils.GsonToBean(result, BaseListResult.class);
                }
                String code = baseResult.getCode();
                String msg = baseResult.getMsg();
                if (code.equals("200")) {
                    List<T> t = baseResult.getResult();
                    if (null != t && null != callBack) {
                        callBack.onSuccess(t);
                    }
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        callBack.onSuccessMsg(msg);
                    }
                } else {
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        int codeInt = StringUtils.parseInt(code);
                        if (codeInt == 10004) {
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
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

    public static <T> void xOverallHttpPost(final Activity context, String url, HashMap<String, String> map, final OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParams(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                XutilGloabeParamsUtil.progressDialogCancel();
                LogUtils.d(result);
                BaseResult<T> baseResult = GsonUtils.GsonToBean(result, BaseResult.class);
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
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
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

    public static <T> void xOverallHttpPostWithoutDialog(final Activity context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParamsWithoutDialog(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
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
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (null != callBack) {
                    callBack.onFail(1001, "onCancelled");
                }
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public static <T> void xOverallHttpPostWithoutDialog(final Activity context, String url, HashMap<String, String> map, final Class<T> cls, final OnxHttpWithListResultCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParamsWithoutDialog(context, url, map), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtils.d(result);
                BaseListResult<T> baseResult = null;
                try {
                    baseResult = GsonUtils.toBaseListResult(result, cls);
                } catch (Exception e) {
                    baseResult = GsonUtils.GsonToBean(result, BaseListResult.class);
                }
                String code = baseResult.getCode();
                String msg = baseResult.getMsg();
                if (code.equals("200")) {
                    List<T> t = baseResult.getResult();
                    if (null != t && null != callBack) {
                        callBack.onSuccess(t);
                    }
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        callBack.onSuccessMsg(msg);
                    }
                } else {
                    if (!TextUtils.isEmpty(msg) && null != callBack) {
                        int codeInt = StringUtils.parseInt(code);
                        if (codeInt == 10004) {
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (null != callBack) {
                    callBack.onFail(1000, "onError");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (null != callBack) {
                    callBack.onFail(1001, "onCancelled");
                }
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /*
    * 参数里暂时包含一张图片文件,添加了一个存放图片路径的list集合
    * */
    public static <T> void xOverallHttpPost(final Activity context, String url, List<String> paths, HashMap<String, String> map, final Class<T> cls, final OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParamsWithFile(context, url,paths, map), new Callback.CommonCallback<String>() {
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
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "网络错误");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1001, "已取消");
                }
            }

            @Override
            public void onFinished() {
                XutilGloabeParamsUtil.progressDialogCancel();
            }
        });
    }

    public static <T> void xOverallHttpPost(final Activity context, String url, HashMap<String, String> filemap, HashMap<String, String> map, final Class<T> cls, final OnxHttpCallBack<T> callBack) {
        x.http().post(XutilGloabeParamsUtil.setRequestParamsWithFile(context, url, filemap, map), new Callback.CommonCallback<String>() {
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
                            loginAgain(context);
                        }
                        callBack.onFail(codeInt, msg);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1000, "网络错误");
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                XutilGloabeParamsUtil.progressDialogCancel();
                if (null != callBack) {
                    callBack.onFail(1001, "已取消");
                }
            }

            @Override
            public void onFinished() {
                XutilGloabeParamsUtil.progressDialogCancel();
            }
        });
    }

    public interface OnxHttpCallBack<T> {
        void onSuccessMsg(String successMsg);

        void onSuccess(T t) throws Exception;

        void onFail(int code, String msg);
    }

    public interface OnxHttpWithListResultCallBack<T> {
        void onSuccessMsg(String successMsg);

        void onSuccess(List<T> t);

        void onFail(int code, String msg);
    }
}
