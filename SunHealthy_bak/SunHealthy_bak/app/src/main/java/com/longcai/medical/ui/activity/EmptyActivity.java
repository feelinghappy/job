package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/17.
 * 敬请期待
 */

public class EmptyActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        titleTv.setText(title);
        titleRightTv.setVisibility(View.GONE);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
