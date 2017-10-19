package com.longcai.medical.ui.activity.index;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/29.
 * 智能硬件-手表
 */

public class DeviceWatchActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.watch_tv_name)
    TextView watchTvName;
    @BindView(R.id.watch_tv_status)
    TextView watchTvStatus;
    @BindView(R.id.watch_iv_contact)
    ImageView watchIvContact;
    @BindView(R.id.watch_iv_info)
    ImageView watchIvInfo;
    @BindView(R.id.watch_tv_step)
    TextView watchTvStep;
    @BindView(R.id.watch_tv_sleep)
    TextView watchTvSleep;
    @BindView(R.id.watch_tv_heart)
    TextView watchTvHeart;
    @BindView(R.id.watch_tv_blood)
    TextView watchTvBlood;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_watch);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.title_back, R.id.title_right_tv,R.id.watch_iv_contact, R.id.watch_iv_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_tv://绑定
                break;
            case R.id.watch_iv_contact://联系人
                break;
            case R.id.watch_iv_info://设备信息
                break;
        }
    }
}
