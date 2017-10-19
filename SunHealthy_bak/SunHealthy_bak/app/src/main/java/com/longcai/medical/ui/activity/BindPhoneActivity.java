package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.LoginTencentResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.index.ZiXunInfoActivity;
import com.longcai.medical.ui.activity.login.PersonInfoActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.ui.view.PhoneNumberTextWatcher;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/30.
 * 绑定手机号
 */

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.ed_bind_phone)
    ClearEditText edBindPhone;
    @BindView(R.id.ed_bind_pin)
    ClearEditText edBindPin;
    @BindView(R.id.tv_bind_send)
    TextView tvBindSend;
    @BindView(R.id.btn_bind)
    Button btnBind;
    @BindView(R.id.login_agreen)
    TextView loginAgreen;

    private String mBindPhone;
    private String mBindPin;
    private boolean isSending;
    private String mAccessToken;
    private String mOpenid;
    private int bindType;
    private Unbinder unbinder;
    private int lastContentLength = 0;
    private boolean isDelete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        unbinder = ButterKnife.bind(this);
        edBindPhone.addTextChangedListener(new PhoneNumberTextWatcher(edBindPhone));
        Intent data = getIntent();
        if (null != data) {
            mAccessToken = data.getStringExtra("access_token");
            mOpenid = data.getStringExtra("openid");
            bindType = data.getIntExtra("bind_type", 0);
        }
        initListener();
    }

    private void initListener() {
        edBindPhone.addTextChangedListener(new TextWatcher() {
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
                    edBindPhone.setText(sb.toString());
                    edBindPhone.setSelection(sb.length());
                }

                //删除的位置到4，9时，剔除空格
                if (isDelete && (s.length() == 4 || s.length() == 9)) {
                    sb.deleteCharAt(sb.length() - 1);
                    edBindPhone.setText(sb.toString());
                    edBindPhone.setSelection(sb.length());
                }

                lastContentLength = sb.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = editable.toString().replaceAll("-", "");
                String pin = edBindPin.getText().toString();
                if (RegexUtils.checkMobile(phone)) {
                    tvBindSend.setEnabled(true);
                } else {
                    tvBindSend.setEnabled(false);
                }
                if (RegexUtils.checkMobile(phone) && !TextUtils.isEmpty(pin)) {
                    btnBind.setEnabled(true);
                } else {
                    btnBind.setEnabled(false);
                }
            }
        });
        edBindPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pin = editable.toString();
                String phone = edBindPhone.getText().toString().replaceAll("-", "");
                if (RegexUtils.checkMobile(phone) && !TextUtils.isEmpty(pin)) {
                    btnBind.setEnabled(true);
                } else {
                    btnBind.setEnabled(false);
                }
            }
        });
    }

    private void initData() {
        mBindPhone = edBindPhone.getText().toString().trim().replaceAll("-", "");
        mBindPin = edBindPin.getText().toString().trim();
    }

    @OnClick({R.id.login_back, R.id.tv_bind_send, R.id.btn_bind, R.id.login_agreen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_bind_send:
                initData();
                if (TextUtils.isEmpty(mBindPhone)) {
                    ToastUtils.showToast(this, "请填写手机号");
                    return;
                } else if (!RegexUtils.checkMobile(mBindPhone)) {
                    ToastUtils.showToast(this, "请填写正确手机号");
                    return;
                }
                // 验证码
                mySMS();
                break;
            case R.id.btn_bind:
                initData();
                if (TextUtils.isEmpty(mBindPhone)) {
                    ToastUtils.showToast(this, "请填写手机号");
                    return;
                } else if (!RegexUtils.checkMobile(mBindPhone)) {
                    ToastUtils.showToast(this, "请填写正确手机号");
                    return;
                } else if (TextUtils.isEmpty(mBindPin)) {
                    ToastUtils.showToast(this, "请填写验证码");
                    return;
                }
                bindPhone();
                break;
            case R.id.login_agreen:
                startActivity(new Intent(this, ZiXunInfoActivity.class)
                        .putExtra("url", MyUrl.AGREEMENT_USER).putExtra("agreement", 1));
                break;
        }
    }

    private void bindPhone() {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", mAccessToken);
        map.put("mobile", mBindPhone);
        map.put("code", mBindPin);
        String url = MyUrl.Loginqq;
        if (bindType == 1) {
            url = MyUrl.Loginqq;
            map.put("openid", mOpenid);
        } else if (bindType == 2) {
            url = MyUrl.LoginWechat;
            map.put("openid", mOpenid);
        } else if (bindType == 3) {
            url = MyUrl.LoginWeibo;
            map.put("uid", mOpenid);
        }
        HttpUtils.xOverallHttpPost(this, url, map, LoginTencentResult.class, new HttpUtils.OnxHttpCallBack<LoginTencentResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(LoginTencentResult loginTencentResult) {
                String token = loginTencentResult.getToken();
                Intent intent = new Intent(BindPhoneActivity.this, PersonInfoActivity.class);
                Bundle data = new Bundle();
                data.putString("mobile", mBindPhone);
                data.putString("token", token);
                data.putBoolean("open_login", true);
                intent.putExtra("mobile_bundle", data);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10056) {
                    ToastUtils.showToast(BindPhoneActivity.this, "短信验证码验证失败");
                }
            }
        });
    }

    private void mySMS() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", mBindPhone);
        tvBindSend.setText("发送中...");
        HttpUtils.xOverallHttpPost(this, MyUrl.SMS, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<?> list) {
                if (!isSending) {
                    isSending = true;
                    ToastUtils.showToast(BindPhoneActivity.this, mBindPhone + "\n发送成功");
                    // 获取接口验证码成功后 设置60s
                    setTextViewCountDownTimer();
                }
            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showToast(BindPhoneActivity.this, mBindPhone + "\n发送失败");
            }
        });
    }

    // 设置发送验证码间隔时间
    private void setTextViewCountDownTimer() {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvBindSend != null) {
                    tvBindSend.setText("已发送（" + (millisUntilFinished / 1000) + "s）");
                    tvBindSend.setClickable(false);
                }
            }

            @Override
            public void onFinish() {
                if (tvBindSend != null) {
                    tvBindSend.setText(R.string.register_tx9);
                    tvBindSend.setClickable(true);
                    isSending = false;
                }
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
