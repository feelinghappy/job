package com.longcai.medical.ui.activity.person.help;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.HelpInfoResult;
import com.longcai.medical.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HelpInfoContentActivity extends BaseActivity {
    @BindView(R.id.title_back)
    ImageView titleBack;
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
        setContentView(R.layout.activity_help_content);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        HelpInfoResult result = (HelpInfoResult) getIntent().getSerializableExtra("HelpInfo");
        if (null != result) {
            String url = result.getUrl();
            if (!TextUtils.isEmpty(url)) {
                webview.loadUrl(url);
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
            }
        }
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleTv.setText("常见问题");
    }

    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        finish();
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
