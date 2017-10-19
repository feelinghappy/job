package com.longcai.medical.ui.activity.discover;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/23.
 */

public class FoodDetailAcivity extends BaseActivity{
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.webview)
    WebView webview;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_info);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("食材详情");
        titleRightTv.setVisibility(View.GONE);
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            webview.loadUrl(url);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
        }
    }
    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (null != webview && webview.getChildCount() > 0) {
            webview.removeAllViews();
            webview.destroy();
        }
    }
}
