package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二期  收藏详情
 */
public class Web2Activity extends BaseActivity {

    private static final String TAG = "Web2Activity";

    @BindView(R.id.bank_btn)
    RelativeLayout bankBtn;
    @BindView(R.id.collect_tx)
    TextView collectTx;
    @BindView(R.id.collect_btn)
    RelativeLayout collectBtn;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.collect_webview)
    WebView collectWebview;
    @BindView(R.id.activity_web2)
    RelativeLayout activityWeb2;
    private String title1;
    private String url;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web2);
        ButterKnife.bind(this);
        title1 = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        id = getIntent().getStringExtra("id");
        setCollectType("1", id);
        Log.e(TAG, "onCreate: " + url);

        collectWebview.loadUrl(url);
        collectWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        bankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Web2Activity.this.finish();
            }
        });
        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCollectType("2", id);
            }
        });
    }

    private void setCollectType(String s, String id) {

    }
}
