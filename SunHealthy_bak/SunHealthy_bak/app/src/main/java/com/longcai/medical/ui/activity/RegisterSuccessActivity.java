package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/30.
 * 注册成功
 */

public class RegisterSuccessActivity extends BaseActivity {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.btn_apply)
    Button btnApply;
    @BindView(R.id.btn_success_login)
    Button btnSuccessLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);
        ButterKnife.bind(this);
        initView();
    }

    void initView() {
        titleTv.setText("注册成功");
        titleRightTv.setVisibility(View.GONE);
        titleBack.setVisibility(View.GONE);
    }

    @OnClick({R.id.title_back,R.id.btn_apply, R.id.btn_success_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_apply:
                Intent intent = new Intent(RegisterSuccessActivity.this,ApplySaleActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.btn_success_login:
                startActivity(new Intent(RegisterSuccessActivity.this,MainActivity.class));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
