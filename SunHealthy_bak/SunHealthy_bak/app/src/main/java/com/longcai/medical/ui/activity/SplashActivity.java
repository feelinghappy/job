package com.longcai.medical.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.AdvertListResult;
import com.longcai.medical.bean.AdvertResult;
import com.longcai.medical.bean.IndexResult;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 欢迎界面
 * Created by Administrator on 2017/7/3.
 */
public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler();
    private ImageView welImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        initView();
        Constant.PHONE_NUMBER = MyApplication.myPreferences.readPhone();
        LogUtils.d("phone", Constant.PHONE_NUMBER);
    }

    private void initView() {
        MyApplication.myPreferences.saveUnpayOrder("-1");
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        int i = sharedPreferences.getInt("medical", 0);
        if (i == 0) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("medical", 1);
            edit.apply();
            Intent intent = new Intent(this, GuideActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            finish();
        } else {
            welImg = (ImageView) findViewById(R.id.wel_img);
            Glide.with(this).load(R.mipmap.welcome).into(welImg);
            startMainActivity();
        }
    }

    private void getIndex() {
        HashMap<String, String> map = new HashMap<>();
        HttpUtils.xOverallHttpPostWithoutDialog(this, MyUrl.INDEX, map, IndexResult.class, new HttpUtils.OnxHttpCallBack<IndexResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(IndexResult result) {
                LogUtils.d("Index", result.toString());
                final AdvertListResult startAdvert = result.getStart_advert();
                List<AdvertResult> adverts = null;
                if (null != startAdvert) {
                    adverts = startAdvert.getAdvert();
                }
                if (null != adverts && null != adverts.get(0)) {
                    AdvertResult advert = adverts.get(0);
                    String advPic = advert.getAdv_pic();
                    if (!TextUtils.isEmpty(advPic)) {
                        Glide.with(SplashActivity.this).load(advPic)
                                .placeholder(R.mipmap.welcome)
                                .listener(new RequestListener<String, GlideDrawable>() {
                                              @Override
                                              public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                  LogUtils.e("Glide Exception", e.toString());
                                                  startMainActivity();
                                                  return false;
                                              }

                                              @Override
                                              public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                                  startMainActivity();
                                                  return false;
                                              }
                                          }
                                )
                                .into(welImg);
                    }

                }
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.d("onFail", msg);
                startMainActivity();
            }
        });
    }

    private void startMainActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        }, 3000);
    }

}
