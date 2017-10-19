package com.longcai.medical.ui.activity.index;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.ArticleListResult;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.ShareUtils;
import com.longcai.medical.utils.ToastUtils;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ZiXunInfoActivity extends BaseActivity implements WbShareCallback {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.title_right_iv)
    ImageView titleRightIv;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.title_line)
    View titleLine;

    private Unbinder unbinder;
    //  private Tencent mTencent;
    private LayoutInflater mInflater;
    private PopupWindow popupWindow;
    //  private int article_id;
    private WbShareHandler shareHandler;
    private ArticleListResult article;
    private BaseUiListener uiListener;
    private ShareUtils shareUtil;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun_info);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(this);
        initView();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        /*用户协议*/
        String agree_url = getIntent().getStringExtra("url");
        int tag = getIntent().getIntExtra("agreement", -1);
        if (tag != -1 && null != agree_url) {
            titleTv.setVisibility(View.GONE);
            titleLine.setVisibility(View.GONE);
            if (tag == 1) {
                loadWeb(agree_url);
            } else if (tag == 2) {
                loadWeb(agree_url);
            }
        } else {
            titleTv.setText("资讯详情");
            titleRightIv.setVisibility(View.VISIBLE);
            titleRightIv.setImageResource(R.mipmap.icon_share);
            /*文章详情*/
            article = (ArticleListResult) getIntent().getSerializableExtra("article_result");
            if (null != article) {
                this.url = article.getUrl();
                loadWeb(url);
            }
            shareUtil = new ShareUtils();
            shareUtil.regToQQ(getApplicationContext());//向QQ终端注册appID
            shareUtil.regToWX(ZiXunInfoActivity.this);//向微信终端注册appID
            //  mTencent = shareUtil.getTencent();
//            WbSdk.install(this, new AuthInfo(this, Constant.SINA_APP_KEY, Constant.SINA_REDIRECT_URL, Constant.SINA_SCOPE));
            uiListener = new BaseUiListener();
        }
    }

    private void loadWeb(String url) {
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.title_back, R.id.title_right_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.title_right_iv://点击分享
                View share_view = mInflater.inflate(R.layout.pop_share, null);
                popupWindow = new ShowPopupWindow(this).showPopup(share_view);
                popupWindow.showAtLocation(share_view, Gravity.BOTTOM, 0, 0);
                if (null != article) {
                    initPopView(share_view);
                }
                break;
        }
    }

    private void initPopView(View share_view) {
        share_view.findViewById(R.id.pop_share_qq).setOnClickListener(listener);
        share_view.findViewById(R.id.pop_share_qzone).setOnClickListener(listener);
        share_view.findViewById(R.id.pop_share_wx).setOnClickListener(listener);
        share_view.findViewById(R.id.pop_share_wxZone).setOnClickListener(listener);
        share_view.findViewById(R.id.pop_share_sina).setOnClickListener(listener);
    }

    // !iwxapi.isWXAppInstalled() 未安装微信   mTencent.isSessionValid() && mTencent.getOpenId() == null 未安装qq
    // String TEST_IMAGE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502269574909&di=a8ba8e32a2925a1936ffd280ec67bfff&imgtype=0&src=http%3A%2F%2Fg.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3De9ffdfb9201f95caa6a09ab2fc275308%2Fb3b7d0a20cf431adb51a99fe4e36acaf2fdd9829.jpg";

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String mShareTitle = article.getTitle();
            final String description = "";
            final String thumb = article.getThumb();
            popupWindow.dismiss();
            switch (v.getId()) {
                case R.id.pop_share_qq:
//                    shareUtil.shareToQQ(ZiXunInfoActivity.this, thumb, url, mShareTitle, description, uiListener);
                    break;
                case R.id.pop_share_qzone:
//                    shareUtil.shareToQzone(ZiXunInfoActivity.this, url, thumb, mShareTitle, description, uiListener);
                    break;
                case R.id.pop_share_wx:
//                    shareUtil.weiChat(0, url, mShareTitle, description);
//                    GetBitmapFromUrlWeixin task0 = new GetBitmapFromUrlWeixin(0, shareUtil, mShareTitle, url);
//                    task0.execute(thumb);
                    break;
                case R.id.pop_share_wxZone:
//                    shareUtil.weiChat(1, url, mShareTitle, description);
//                    GetBitmapFromUrlWeixin task1 = new GetBitmapFromUrlWeixin(1, shareUtil, mShareTitle, url);
//                    task1.execute(thumb);
                    break;
                case R.id.pop_share_sina:
//                    if (null == shareHandler) {
//                        shareHandler = new WbShareHandler(ZiXunInfoActivity.this);
//                        shareHandler.registerApp();
//                    }

//                    GetBitmapFromUrl task = new GetBitmapFromUrl(shareUtil, shareHandler, mShareTitle, url);
//                    task.execute(thumb);
                    break;
            }
        }
    };

    class GetBitmapFromUrl extends AsyncTask<String, Integer, Bitmap> {
        private ShareUtils shareUtils;
        private WbShareHandler shareHandler;
        private String shareTitle;
        private String shareUrl;

        public GetBitmapFromUrl(ShareUtils shareUtils, WbShareHandler shareHandler, String shareTitle, String shareUrl) {
            this.shareUtils = shareUtils;
            this.shareHandler = shareHandler;
            this.shareTitle = shareTitle;
            this.shareUrl = shareUrl;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                bitmap = shareUtils.getBitmapFromPath(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            shareUtils.shareToSinaWeiBo(shareHandler, shareTitle, shareUrl, bitmap);
        }
    }

    ;

    class GetBitmapFromUrlWeixin extends AsyncTask<String, Integer, Bitmap> {
        private ShareUtils shareUtils;
        private String shareTitle;
        private String shareUrl;
        private int type;

        public GetBitmapFromUrlWeixin(int type, ShareUtils shareUtils, String shareTitle, String shareUrl) {
            this.type = type;
            this.shareUtils = shareUtils;
            this.shareTitle = shareTitle;
            this.shareUrl = shareUrl;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                bitmap = shareUtils.getBitmapFromPath(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null == bitmap) {
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            shareUtils.weiChat(type, shareUrl, shareTitle, "", bitmap);
        }
    }

    ;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            shareHandler.doResultIntent(intent, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWbShareSuccess() {
        ToastUtils.showToast(this, "分享成功");
    }

    @Override
    public void onWbShareCancel() {
        ToastUtils.showToast(this, "分享取消");
    }

    @Override
    public void onWbShareFail() {
        ToastUtils.showToast(this, "分享失败");
    }

    private class BaseUiListener implements IUiListener {//QQ和Qzone分享回调

        @Override
        public void onCancel() {
            ToastUtils.showToast(ZiXunInfoActivity.this, "分享取消");
        }

        @Override
        public void onComplete(Object arg0) {
            ToastUtils.showToast(ZiXunInfoActivity.this, "分享成功");
        }

        @Override
        public void onError(UiError arg0) {
            ToastUtils.showToast(ZiXunInfoActivity.this, "分享失败");
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, uiListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, uiListener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();
//        if (null != webview && webview.getChildCount() > 0) {
//            webview.removeAllViews();
//            webview.destroy();
//        }
    }

}
