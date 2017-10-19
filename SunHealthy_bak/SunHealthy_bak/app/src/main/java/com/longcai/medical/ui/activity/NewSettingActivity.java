package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.service.UpdateManager;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.DataCleanManager;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSettingActivity extends BaseActivity {
    private static final String TAG = "NewSettingActivity";
    @BindView(R.id.setting_person_btn)
    RelativeLayout settingPersonBtn;
    @BindView(R.id.version_tx)
    TextView versionTx;
    @BindView(R.id.setting_versions_btn)
    RelativeLayout settingVersionsBtn;
    @BindView(R.id.setting_about_us_btn)
    RelativeLayout settingAboutUsBtn;
    @BindView(R.id.setting_opinion_btn)
    RelativeLayout settingOpinionBtn;
    @BindView(R.id.setting_clean_btn)
    RelativeLayout settingCleanBtn;
    @BindView(R.id.setting_quit_btn)
    RelativeLayout settingQuitBtn;
    @BindView(R.id.activity_new_setting)
    RelativeLayout activityNewSetting;
    private int version;
    private Tencent mTencent;
    private PopupWindow popupWindow_quit;
    private TextView positiveButton;
    private TextView negativeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_setting);
        ButterKnife.bind(this);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, this.getApplicationContext());
        try {
            version = this.getPackageManager().getPackageInfo("com.longcai.medical", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionTx.setText("V:" + version);
    }

    @OnClick({R.id.setting_back_btn, R.id.setting_person_btn,
            R.id.setting_versions_btn, R.id.setting_about_us_btn, R.id.setting_opinion_btn,
            R.id.setting_clean_btn, R.id.setting_quit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_back_btn:
                this.finish();
                break;
            case R.id.setting_person_btn://个人资料
                if (!MyApplication.myPreferences.readToken().equals("-1")) {
                    startActivity(new Intent(NewSettingActivity.this,
                            NewPersonInfoActivity.class));
                }
                break;
            case R.id.setting_versions_btn://版本更新
                myversion();
                break;
            case R.id.setting_about_us_btn:
                startActivity(new Intent(NewSettingActivity.this, WebActivity.class).
                        putExtra("title", "关于我们").putExtra("url", getIntent().getStringExtra("about")));
                break;
            case R.id.setting_opinion_btn://意见反馈
                startActivity(new Intent(NewSettingActivity.this, ErQiOpinionActivity.class));
                break;
            case R.id.setting_clean_btn://清理缓存
                clean();
                break;
            case R.id.setting_quit_btn:
                if (!MyApplication.myPreferences.readToken().equals("-1")) {
                    View contentView = LayoutInflater.from(this).inflate(
                            R.layout.dialog_text_view, null);
                    popupWindow_quit = new ShowPopupWindow(this).showPopup(contentView);
                    //设置popwindow显示位置
                    popupWindow_quit.showAtLocation(settingQuitBtn, Gravity.CENTER, 0, 0);
                    initPop(contentView);
                }
                break;
        }
    }

    //获取版本更新
    private void myversion() {
        PackageManager manager;
        PackageInfo info = null;
        manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            int versionCode = info.versionCode;
            String versionName = info.versionName;
            Log.e(TAG, "myversion: " + versionCode + "------------" + versionName);
            new UpdateManager(this).getCheckUpdate(versionName, false, MyUrl.Version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initPop(View view) {
        positiveButton = (TextView) view.findViewById(R.id.positiveButton);//确定
        negativeButton = (TextView) view.findViewById(R.id.negativeButton);//取消
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                MyApplication.myPreferences.clear();
                MyApplication.myPreferences.saveToken("-1");
                /*DemoHelper.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.d("1535", "环信退出成功" + DemoHelper.getInstance().isLoggedIn());
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });*/
                //qq登录退出
                mTencent.logout(NewSettingActivity.this);
                popupWindow_quit.dismiss();
                onBackPressed();
            }


        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                popupWindow_quit.dismiss();
            }
        });
    }

    private void clean() {
        //应用程序清除 文件，设置，账户，数据库
//        String file="/sdcard/mdcxlift/head";
        //清除缓存
        DataCleanManager.cleanInternalCache(this);
        ToastUtils.show(NewSettingActivity.this, getResources().getString(R.string.setting_clean));
    }

}
