package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/30.
 * 申请销售人员成功
 */

public class ApplySuccessActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.apply_suc_5sintent)
    TextView applySuc5sintent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_success);
        ButterKnife.bind(this);
        initView();
    }

    void initView() {
        titleTv.setText("申请成功");
        titleRightTv.setVisibility(View.GONE);
    }

    @OnClick({R.id.title_back, R.id.apply_suc_5sintent, R.id.apply_suc_phone,R.id.apply_suc_gomain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.apply_suc_5sintent:
                break;
            case R.id.apply_suc_phone:
                break;
            case R.id.apply_suc_gomain:
                finish();
//                startActivity(new Intent(ApplySuccessActivity.this, MainActivity.class));
                break;
        }
    }
}
