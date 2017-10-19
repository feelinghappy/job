package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/3.
 */

/**
 * 常见问题
 * 资讯详情
 */
public class WebActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bank_btn)
    RelativeLayout bankBtn;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.baoming_tx)
    TextView baomingTx;
    @BindView(R.id.baoming_btn)
    RelativeLayout baomingBtn;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.webview)
    WebView webview;
    private String aid;
    private int ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        ButterKnife.bind(this);
        if (title.equals("活动详情")) {
            ps = getIntent().getIntExtra("ps", -1);
            if (ps == 1) {
                baomingTx.setText("查看报名");
            } else {
                baomingTx.setText("报名参加");
            }
            aid = intent.getStringExtra("aid");
            baomingBtn.setVisibility(View.VISIBLE);
        } else {
            baomingBtn.setVisibility(View.GONE);
        }
        titleText.setText(title);
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }
    @OnClick({R.id.bank_btn, R.id.baoming_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bank_btn:
                onBackPressed();
                break;
            case R.id.baoming_btn:
                ToastUtils.showToast(this,"WebActivity_活动btn");
                startActivity(new Intent(WebActivity.this,
                        ApplySportActivity.class).putExtra("aid", aid).putExtra("ps", ps));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }
}

