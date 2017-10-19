package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.LoginResult;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.index.ZiXunInfoActivity;
import com.longcai.medical.ui.activity.login.PersonInfoActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.utils.AppManager;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.ToastUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/30.
 * 登录
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.ed_login_phone)
    ClearEditText edLoginPhone;
    @BindView(R.id.ed_login_pin)
    ClearEditText edLoginPin;
    @BindView(R.id.tv_login_send)
    TextView tvLoginSend;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private Tencent mTencent;
    private String scope;
    private IUiListener loginListener;
    private String mPhone;
    private String mPin;
    private boolean isSending = false;
    private Unbinder unbinder;
    private SsoHandler ssoHandler;
    private CountDownTimer countDownTimer;
    private int lastContentLength = 0;
    private boolean isDelete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_bak);
//        AppManager.getAppManager().addActivity(this);
        unbinder = ButterKnife.bind(this);
        initListener();
    }

    private void initListener() {
        edLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                StringBuffer sb = new StringBuffer(s);
                //是否为输入状态
                isDelete = s.length() > lastContentLength ? false : true;

                //输入是第4，第9位，这时需要插入空格
                if(!isDelete&& (s.length() == 4||s.length() == 9)){
                    if(s.length() == 4) {
                        sb.insert(3, "-");
                    }else {
                        sb.insert(8, "-");
                    }
                    edLoginPhone.setText(sb.toString());
                    edLoginPhone.setSelection(sb.length());
                }

                //删除的位置到4，9时，剔除空格
                if (isDelete && (s.length() == 4 || s.length() == 9)) {
                    sb.deleteCharAt(sb.length() - 1);
                    edLoginPhone.setText(sb.toString());
                    edLoginPhone.setSelection(sb.length());
                }

                lastContentLength = sb.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = editable.toString().replaceAll("-", "");
                String pin = edLoginPin.getText().toString();
                if (RegexUtils.checkMobile(phone)) {
                    tvLoginSend.setEnabled(true);
                } else {
                    tvLoginSend.setEnabled(false);
                }
                if (RegexUtils.checkMobile(phone) && !TextUtils.isEmpty(pin)) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });
        edLoginPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pin = editable.toString();
                String phone = edLoginPhone.getText().toString().replaceAll("-", "");
                if (RegexUtils.checkMobile(phone) && !TextUtils.isEmpty(pin)) {
                    btnLogin.setEnabled(true);
                } else {
                    btnLogin.setEnabled(false);
                }
            }
        });
    }

    private void applyWeibo() {
        WbSdk.install(this, new AuthInfo(this, Constant.SINA_APP_KEY, Constant.SINA_REDIRECT_URL, Constant.SINA_SCOPE));
        ssoHandler = new SsoHandler(this);
        ssoHandler.authorizeClientSso(new AuthListener());
    }

    private class AuthListener implements WbAuthListener {

        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
            weiboLogin(oauth2AccessToken.getToken(), oauth2AccessToken.getUid());
        }

        @Override
        public void cancel() {

        }

        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

        }
    }

    private void weiboLogin(final String access_token, final String uid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("uid", uid);
        HttpUtils.xOverallHttpPost(this, MyUrl.LoginWeibo, map, LoginResult.class, new HttpUtils.OnxHttpCallBack<LoginResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(LoginResult loginResult) throws Exception {
                String has_info = loginResult.getHas_info();
                String token = loginResult.getToken();
                if (has_info.equals("0")) {
                    Intent intent = new Intent(LoginActivity.this, PersonInfoActivity.class);
                    Bundle data = new Bundle();
                    data.putString("token", token);
                    data.putBoolean("open_login", true);
                    intent.putExtra("mobile_bundle", data);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else if (has_info.equals("1")) {
                    MyApplication.myPreferences.saveToken(token);
//                    // 给MainActivity返回结果 (判断了是否跳入家庭)
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
//                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//                    finish();
                    if (MyApplication.source != -1) {
                        // 给MainActivity返回结果 (判断了是否跳入家庭)
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                    } else {
                        finish();
                        overridePendingTransition(0, R.anim.pop_exit_bottom);
                    }
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10054) {
                    Intent data = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    data.putExtra("access_token", access_token);
                    data.putExtra("openid", uid);
                    data.putExtra("bind_type", 3);
                    startActivity(data);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }
        });
    }

    private void initView() {
        mPhone = edLoginPhone.getText().toString().trim();
        mPhone = mPhone.replaceAll("-", "");
        mPin = edLoginPin.getText().toString().trim();
    }

    @OnClick({R.id.login_back, R.id.tv_login_send, R.id.btn_login, R.id.login_agreen, R.id.login_qq, R.id.login_wx, R.id.login_weibo})
    public void onViewClicked(View view) {
        initView();
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                overridePendingTransition(0, R.anim.pop_exit_bottom);
                break;
            case R.id.tv_login_send:
                if (TextUtils.isEmpty(mPhone)) {
                    ToastUtils.showToast(this, "请填写手机号");
                    return;
                } else if (!RegexUtils.checkMobile(mPhone)) {
                    ToastUtils.showToast(this, "请填写正确手机号");
                    return;
                }
                //验证码
                mySMS();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(mPhone)) {
                    ToastUtils.showToast(this, "请填写手机号");
                    return;
                } else if (!RegexUtils.checkMobile(mPhone)) {
                    ToastUtils.showToast(this, "请填写正确手机号");
                    return;
                }
                if (TextUtils.isEmpty(mPin)) {
                    ToastUtils.showToast(this, "请填写验证码");
                    return;
                }
//                else if (mPin.length() != 6) {
//                    ToastUtils.showToast(this, "请填写6位数字验证码");
//                    return;
//                }
                //登录接口
                myLogin();
                break;
            case R.id.login_agreen:
                startActivity(new Intent(this, ZiXunInfoActivity.class)
                        .putExtra("url", MyUrl.AGREEMENT_USER).putExtra("agreement", 1));
                break;
            case R.id.login_qq:
                //qq拉取授权
                applyQQLogin();
                qqLogin();
                break;
            case R.id.login_wx:
                //微信登录
                wxLogin();
                break;
            case R.id.login_weibo:
                applyWeibo();
                break;
        }
    }

    //验证码
    private void mySMS() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mPhone);
        tvLoginSend.setText("发送中...");
        HttpUtils.xOverallHttpPost(this, MyUrl.SMS, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<?> list) {
                if (!isSending) {
                    isSending = true;
                    ToastUtils.showToast(LoginActivity.this, mPhone + "\n发送成功");
                    // 获取接口验证码成功后 设置60s
                    setTextViewCountDownTimer();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(LoginActivity.this, mPhone + "\n发送失败");
            }
        });
    }

    private void wxLogin() {
        IWXAPI api;
        //api注册
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, true);
        api.registerApp(Constant.WX_APP_ID);

        SendAuth.Req req = new SendAuth.Req();
        //授权读取用户信息
        req.scope = "snsapi_userinfo";
        //自定义信息
        req.state = "wechat_sdk_demo_test";

        //向微信发送请求,发送完成切换回第三方
        api.sendReq(req);
    }

    private void applyQQLogin() {
        //初始化qq主操作对象
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, this.getApplicationContext());
        //要所有权限，不然会再次申请增量权限，这里不要设置成get_user_info,add_t
        scope = "all";
        loginListener = new IUiListener() {
            @Override
            public void onError(UiError arg0) {

            }

            @Override
            public void onComplete(Object value) {
                qqLoginResult(value);
            }

            @Override
            public void onCancel() {

            }
        };
    }

    //IUiListener qq登录拉起授权返回结果
    private void qqLoginResult(Object value) {
        if (value == null) {
            return;
        }
        try {
            JSONObject jo = (JSONObject) value;
            LogUtils.e("qqq", "onComplete: " + jo);
            int ret = jo.getInt("ret");

            LogUtils.e("json=" + String.valueOf(jo));

            if (ret == 0) {

                String openID = jo.getString("openid");
                String accessToken = jo.getString("access_token");

                loginQQ(accessToken, openID);
            }

        } catch (JSONException e) {
        }
    }

    private void qqLogin() {
        //如果session无效，就开始登录
        if (!mTencent.isSessionValid()) {
            //开始qq授权登录
            mTencent.login(LoginActivity.this, scope, loginListener);
        }
    }

    private void loginQQ(final String access_token, final String openid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", access_token);
        map.put("openid", openid);
        HttpUtils.xOverallHttpPost(this, MyUrl.Loginqq, map, LoginResult.class, new HttpUtils.OnxHttpCallBack<LoginResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(LoginResult loginResult) {
                String has_info = loginResult.getHas_info();
                String token = loginResult.getToken();
                if (has_info.equals("0")) {
                    Intent intent = new Intent(LoginActivity.this, PersonInfoActivity.class);
                    Bundle data = new Bundle();
                    data.putString("token", token);
                    data.putBoolean("open_login", true);
                    intent.putExtra("mobile_bundle", data);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else if (has_info.equals("1")) {
                    MyApplication.myPreferences.saveToken(token);
//                    // 给MainActivity返回结果 (判断了是否跳入家庭)
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
//                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//                    finish();
                    if (MyApplication.source != -1) {
                        // 给MainActivity返回结果 (判断了是否跳入家庭)
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                    } else {
                        finish();
                        overridePendingTransition(0, R.anim.pop_exit_bottom);
                    }
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10054) {
                    Intent data = new Intent(LoginActivity.this, BindPhoneActivity.class);
                    data.putExtra("access_token", access_token);
                    data.putExtra("openid", openid);
                    data.putExtra("bind_type", 1);
                    startActivity(data);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                }
            }
        });
    }

    //登录接口
    private void myLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mPhone);
        map.put("code", mPin);
        HttpUtils.xOverallHttpPost(this, MyUrl.LOGIN, map, LoginResult.class, new HttpUtils.OnxHttpCallBack<LoginResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtils.d(loginResult.toString());
                String has_info = loginResult.getHas_info();
                String token = loginResult.getToken();
                if (has_info.equals("0")) {
                    Intent intent = new Intent(LoginActivity.this, PersonInfoActivity.class);
                    Bundle data = new Bundle();
                    data.putString("mobile", mPhone);
                    data.putString("token", token);
                    intent.putExtra("mobile_bundle", data);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    finish();
                } else if (has_info.equals("1")) {
                    // TODO remove
                    Constant.PHONE_NUMBER = mPhone;
                    MyApplication.myPreferences.saveToken(token);
                    MyApplication.myPreferences.savePhone(mPhone);
//                    // 给MainActivity返回结果 (判断了是否跳入家庭)
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
//                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//                    finish();
                    if (MyApplication.source != -1) {
                        // 给MainActivity返回结果 (判断了是否跳入家庭)
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent.putExtra("source", MyApplication.source).putExtra("login_success", true));
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        finish();
                    } else {
                        finish();
                        overridePendingTransition(0, R.anim.pop_exit_bottom);
                    }
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10012) {
                    ToastUtils.showToast(LoginActivity.this, "请输入正确的验证码");
                }
            }
        });
    }

    //设置发送验证码间隔时间
    private void setTextViewCountDownTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvLoginSend != null) {
                    tvLoginSend.setText("已发送（" + (millisUntilFinished / 1000) + "s）");
                    tvLoginSend.setClickable(false);
                }
            }

            @Override
            public void onFinish() {
                if (tvLoginSend != null) {
                    tvLoginSend.setText(R.string.register_tx9);
                    tvLoginSend.setClickable(true);
                    isSending = false;
                }
            }
        };
        countDownTimer.start();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }

        Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mTencent != null) {
            //注销登录
            mTencent.logout(LoginActivity.this);
        }
        if (null != unbinder) {
            unbinder.unbind();
        }
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(0, R.anim.pop_exit_bottom);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
