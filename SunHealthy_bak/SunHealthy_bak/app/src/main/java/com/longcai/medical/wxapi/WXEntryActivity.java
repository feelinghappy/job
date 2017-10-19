package com.longcai.medical.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.LoginResult;
import com.longcai.medical.bean.WXGetIdBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.activity.BindPhoneActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.login.PersonInfoActivity;
import com.longcai.medical.utils.AppManager;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;


/**
 * Created by Administrator on 2016/11/23.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXEntryActivity";
    HashMap<String, String> map = new HashMap<>();
    private IWXAPI api;
    String appid;
    String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        //注册API
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
        api.handleIntent(getIntent(), this);

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        getData(resp);
    }

    private void getData(BaseResp baseResp) {
        if (baseResp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) baseResp;
            //获取微信传回的code
            String code = newResp.code;
            Log.e("wxcode", "onResp: " + code);
            //微信获取openid
            RequestParams params = new RequestParams(MyUrl.WXOPenid);
            params.addParameter("appid", Constant.WX_APP_ID);
            params.addParameter("secret", Constant.WX_Secret);
            params.addParameter("code", code);
            params.addParameter("grant_type", "authorization_code");

            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i(TAG, "getData: onSuccess   " + result);
                    WXGetIdBean info = new Gson().fromJson(result, WXGetIdBean.class);
                    if (info.getOpenid() != null) {
                        appid = info.getOpenid();
                        token = info.getAccess_token();
                        loginWechat(token, appid);
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
            int errorCode = baseResp.errCode;
            switch (errorCode) {
                case BaseResp.ErrCode.ERR_OK:
                    Log.d("1555", "用户同意");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    Log.d("1555", "用户拒绝");
                    this.finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    Log.d("1555", "用户离开");
                    this.finish();
                    break;
                default:
                    break;
            }
        } else {
            int errorCode = baseResp.errCode;
            switch (errorCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtils.showToast(this, "分享成功");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtils.showToast(this, "分享失败");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtils.showToast(this, "分享取消");
                    break;
                default:
                    ToastUtils.showToast(this, "分享失败");
                    break;
            }
            finish();
        }
//        int errorCode = baseResp.errCode;
//        switch (errorCode) {
//            case BaseResp.ErrCode.ERR_OK:
//                Log.d("1555", "用户同意");
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                Log.d("1555", "用户拒绝");
//                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                Log.d("1555", "用户离开");
//                break;
//            default:
//                break;
//        }
//        this.finish();
//        Log.d("1555", "code:" + baseResp.errStr);
    }

    private void loginWechat(final String access_token, final String openid) {
        map.put("access_token", access_token);
        map.put("openid", openid);
        HttpUtils.xOverallHttpPost(this, MyUrl.LoginWechat, map, LoginResult.class, new HttpUtils.OnxHttpCallBack<LoginResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(LoginResult loginResult) {
//                String token = loginResult.getToken();
//                if (!TextUtils.isEmpty(token)) {
//                    ToastUtils.showToast(WXEntryActivity.this, "登录成功");
//                    MyApplication.myPreferences.saveToken(token);
//                    Constant.LoginMark = true;
//                    Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
//                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
//                    AppManager.getAppManager().leaveActivity(MainActivity.class);
//
////                    Intent i = new Intent(WXEntryActivity.this, MainActivity.class);
////                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
////                    startActivity(i);
////                    AppManager.getAppManager().leaveTopActivity();
//                }
                String has_info = loginResult.getHas_info();
                String token = loginResult.getToken();
                if (has_info.equals("0")) {
                    Intent intent = new Intent(WXEntryActivity.this, PersonInfoActivity.class);
                    Bundle data = new Bundle();
                    data.putString("token", token);
                    data.putBoolean("open_login", true);
                    data.putBoolean("weixin_login", true);
                    intent.putExtra("mobile_bundle", data);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else if (has_info.equals("1")) {
                    MyApplication.myPreferences.saveToken(token);
//                    // 给MainActivity返回结果 (判断了是否跳入家庭)
                    Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                    AppManager.getAppManager().leaveTopActivity();

//                    Intent i = new Intent(WXEntryActivity.this, MainActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//                    startActivity(i);
//                    finish();
//                    AppManager.getAppManager().leaveTopActivity();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10054) {
                    Intent data = new Intent(WXEntryActivity.this, BindPhoneActivity.class);
                    data.putExtra("access_token", access_token);
                    data.putExtra("openid", openid);
                    data.putExtra("bind_type", 2);
                    startActivity(data);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}