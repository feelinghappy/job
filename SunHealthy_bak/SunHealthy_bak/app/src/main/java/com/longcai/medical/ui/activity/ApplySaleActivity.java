package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.ApplySaleFragment;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/30.
 * 申请销售人员
 */

public class ApplySaleActivity extends BaseActivity {


    private ApplySaleFragment applySaleFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        initView();
    }
    void initView() {
        if (applySaleFragment == null){
            applySaleFragment = ApplySaleFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,applySaleFragment).commit();
    }


}
