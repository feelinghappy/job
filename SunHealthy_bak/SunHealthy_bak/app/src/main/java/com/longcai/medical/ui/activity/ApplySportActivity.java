package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import butterknife.ButterKnife;

/**
 * 活动报名
 */
public class ApplySportActivity extends BaseActivity {


    TextView phoneTx;
    private String sex = "1";
    private String aid;
    private int ps;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_sport);
        ButterKnife.bind(this);
        aid = getIntent().getStringExtra("aid");
        ps = getIntent().getIntExtra("ps", -1);
    }
}
