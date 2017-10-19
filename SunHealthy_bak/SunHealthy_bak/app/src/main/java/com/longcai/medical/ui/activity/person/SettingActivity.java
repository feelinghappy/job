package com.longcai.medical.ui.activity.person;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.person.address.AddressManageActivity;
import com.longcai.medical.ui.view.popupwindow.ClearCachePopupWindow;
import com.longcai.medical.ui.view.popupwindow.LogoutPopupWindow;
import com.longcai.medical.utils.GlideCacheUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/3.
 */

public class SettingActivity extends BaseActivity implements LogoutPopupWindow.ILogout, ClearCachePopupWindow.IClearCache {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.tv_setting_clear_size)
    TextView tvSettingClearSize;
    @BindView(R.id.rl_setting_clear)
    RelativeLayout rlSettingClear;
    @BindView(R.id.tv_setting_version)
    TextView tvSettingVersion;
    @BindView(R.id.rl_setting_version)
    RelativeLayout rlSettingVersion;
    @BindView(R.id.btn_setting_logout)
    Button btnSettingLogout;
    @BindView(R.id.rl_setting_address)
    RelativeLayout rlSettingAddress;
    private ProgressDialog mProgressDialog;
    private Unbinder unbinder;
    private long initCacheSize;
//    private View view_cache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        unbinder = ButterKnife.bind(this);
        initView();
        getCacheSize();
//        initData();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleTv.setText("设置");
    }

//    private void initData() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("token", MyApplication.myPreferences.readToken());
//        map.put("os_type", "android");
//        HttpUtils.xOverallHttpPost(this, MyUrl.VERSION_INFO, map, VersionInfoResult.class, new HttpUtils.OnxHttpCallBack<VersionInfoResult>() {
//            @Override
//            public void onSuccessMsg(String successMsg) {
//
//            }
//
//            @Override
//            public void onSuccess(VersionInfoResult result) {
//                String version_code = result.getVersion_code();
//                if (!TextUtils.isEmpty(version_code)) {
//                    tvSettingVersion.setText(version_code);
//                }
//            }
//
//            @Override
//            public void onFail(int code, String msg) {
//
//            }
//        });
//        getCacheSize();
//    }

    private void getCacheSize() {
        String glideCacheSize = GlideCacheUtils.getCacheSize();
        if (!TextUtils.isEmpty(glideCacheSize)) {
            tvSettingClearSize.setText(glideCacheSize);
        } else {
            tvSettingClearSize.setText("0");
        }
    }

    @OnClick({R.id.title_back, R.id.rl_setting_clear, R.id.rl_setting_version, R.id.btn_setting_logout, R.id.rl_setting_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.rl_setting_clear:
                initCacheSize = GlideCacheUtils.getCacheSizeLong();
                if (initCacheSize > 0) {
                    ClearCachePopupWindow clearCachePopupWindow = new ClearCachePopupWindow(this);
                    clearCachePopupWindow.showPopupWindow(rlSettingClear);
                    clearCachePopupWindow.setIClearCache(this);
                }
                break;
            case R.id.rl_setting_version:
                break;
            case R.id.rl_setting_address://管理收货地址
                startActivity(new Intent(SettingActivity.this, AddressManageActivity.class));
                break;
            case R.id.btn_setting_logout:
                LogoutPopupWindow logoutPopupWindow = new LogoutPopupWindow(this);
                logoutPopupWindow.showPopupWindow(btnSettingLogout);
                logoutPopupWindow.setILogout(this);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 200) {
                if (null != mProgressDialog && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    getCacheSize();
                    long afterCacheSize = GlideCacheUtils.getCacheSizeLong();
                    long clearCache = initCacheSize - afterCacheSize;
                    if (clearCache > 0) {
                        ToastUtils.showToast(SettingActivity.this, "清除了" + GlideCacheUtils.getFormatSize(clearCache));
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void logout() {
        MyApplication.myPreferences.clear();
        MyApplication.myPreferences.saveToken("-1");
        MyApplication.myPreferences.savePhone("-1");
        MyApplication.myPreferences.saveApplySaler("-1");
        MyApplication.myPreferences.saveSystolic("0");
        MyApplication.myPreferences.saveDiastolic("0");
        MyApplication.myPreferences.saveMin_heart("0");
        MyApplication.myPreferences.saveMax_heart("0");
        MyApplication.myPreferences.saveAvg_heart("0");
        MyApplication.myPreferences.saveSleep_time("0");
        MyApplication.myPreferences.saveSleep_grade("5");
        MyApplication.myPreferences.saveStep_num("0");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.putExtra("logout_success", true));
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    @Override
    public void clearCache() {
        mProgressDialog = ProgressDialog.show(this, "", "正在清除...");
        GlideCacheUtils.clearCacheMemory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideCacheUtils.cleanCatchDisk();
                mHandler.sendEmptyMessage(200);
            }
        }).start();
    }
}
