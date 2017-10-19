package com.longcai.medical.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallListener;
import com.fm.openinstall.listener.AppWakeUpListener;
import com.fm.openinstall.model.AppData;
import com.fm.openinstall.model.Error;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetUserInfoResult;
import com.longcai.medical.bean.InstallBean;
import com.longcai.medical.bean.MsgInfo;
import com.longcai.medical.bean.OrderSnResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.service.PermissionsManager;
import com.longcai.medical.service.PermissionsResultAction;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.FamilyFragment;
import com.longcai.medical.ui.fragment.HomeFragment;
import com.longcai.medical.ui.fragment.MineFragment;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * 主页面
 * Created by Administrator on 2017/7/3.
 */

public class MainActivity extends BaseActivity implements CustomPopupWindow.IOKBack, AppWakeUpListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_family)
    RadioButton rbFamily;
    @BindView(R.id.rb_news)
    RadioButton rbNews;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;

    private Unbinder unbinder;
    private FragmentManager mManager;
    private HomeFragment homeFragment;
    private FamilyFragment familyFragment;
    private MineFragment mineFragment;

    private List<MsgInfo.MsgInfoBean> mList = new ArrayList<>();
    private String orderSn;
    private boolean showRedPoint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_main);
//        AppManager.getAppManager().addActivity(this);
        unbinder = ButterKnife.bind(this);
        //设置radiogroup tab图片大小
        setTabSize();
        initView();

        initRobotCall();
        if (!MyApplication.myPreferences.readToken().equals("-1")) {
            //获取系统和家庭消息
            getMsg();
        }
        JPushInterface.init(getApplicationContext());
        JPushInterface.setDebugMode(true);
        h.postDelayed(orderUnpayRunnable, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Handler h = new Handler();

    private void getOrderUnpay() {
        orderSn = "";
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(this, MyUrl.UNORDER_MEMBER, map, OrderSnResult.class, new HttpUtils.OnxHttpCallBack<OrderSnResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(OrderSnResult result) throws Exception {
                orderSn = result.getOrder_sn();
                if (!TextUtils.isEmpty(orderSn)) {
                    if (MyApplication.myPreferences.readUnpayOrder().equals("-1")) {
//                        CustomPopupWindow customPopupWindow = new CustomPopupWindow(MainActivity.this);
//                        customPopupWindow.showPopupWindow(rbHome);
//                        customPopupWindow.setTitle("您还有未支付的订单，请去支付");
//                        customPopupWindow.setIOKBack(MainActivity.this);
//                        MyApplication.myPreferences.saveUnpayOrder("1");
                    }
                    if (!showRedPoint) {
                        Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_redpoint_selector);
                        drawable_me.setBounds(10, 0, 84, 68);
                        rbMine.setCompoundDrawables(null, drawable_me, null, null);
                        showRedPoint = true;
                    }
                    if (null != mineFragment) {
                        mineFragment.setMyOrderHint(true);
                    }
                }
                h.removeCallbacks(orderUnpayRunnable);
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10480) {
                    if (null == mList || mList.size() <= 0){
                        Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_selector);
                        drawable_me.setBounds(0, 0, 60, 60);
                        rbMine.setCompoundDrawables(null, drawable_me, null, null);
                        showRedPoint = false;
                    }
                    h.removeCallbacks(orderUnpayRunnable);
                }
            }
        });
    }

    private Runnable orderUnpayRunnable = new Runnable() {
        @Override
        public void run() {
            OpenInstall.getInstall(new AppInstallListener() {
                @Override
                public void onInstallFinish(AppData appData, Error error) {
                    if (error == null) {
                        if (appData == null) return;
                        try {
                            //获取自定义数据
                            LogUtils.d("OpenInstall", "getInstall : bindData = " + appData.getData());
//                            CustomPopupWindow customPopupWindow = new CustomPopupWindow(MainActivity.this);
//                            customPopupWindow.showPopupWindow(rbHome);
//                            customPopupWindow.setTitle(appData.getData());
                            String installData = appData.getData();
                            InstallBean installBean = GsonUtils.GsonToBean(installData, InstallBean.class);
                            MyApplication.myPreferences.saveInviteCode(installBean.getCode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("OpenInstall", "getInstall : errorMsg = " + error.toString());
                    }
                }
            });
            getOrderUnpay();
        }
    };

    //TODO 初始化与机器人视频的条件
    private void initRobotCall() {
        requestRobotPermissions();//机器人 请求权限
        if (!MyApplication.myPreferences.readToken().equals("-1")) {
            if (!MyApplication.myPreferences.readPhone().equals("-1")) {

            } else {
                getUserInfoData();
            }
        }
    }

    //机器人 请求权限
    @TargetApi(23)
    private void requestRobotPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(MainActivity.this,
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        //授予
                    }

                    @Override
                    public void onDenied(String permission) {
                        //否认
                    }
                });
    }

    private void initView() {
        mManager = getSupportFragmentManager();

        int source = getIntent().getIntExtra("source", 1);
        switch (source) {
            case -1:
                switch2Home();
                break;
            case 1:
                switch2Home();
                break;
            case 2:
                switch2Family();
                break;
            case 3:
                switch2Mine();
                break;
        }

        // 底栏标签切换监听
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        switch2Home();
                        break;
                    case R.id.rb_family:
                        switch2Family();
                        break;
//                    case R.id.rb_news:
//                        break;
                    case R.id.rb_mine:
                        switch2Mine();
                        if (!TextUtils.isEmpty(orderSn)) {
                            boolean hint = mineFragment.setMyOrderHint(true);
                            if (!hint) {
                                Bundle b = new Bundle();
                                b.putBoolean("hint_myorder", true);
                                if (null != mList && mList.size() > 0) {
                                    b.putParcelableArrayList("notice_list", (ArrayList<? extends Parcelable>) mList);
                                }
                                mineFragment.setArguments(b);
                            } else {
                                if (null != mList && mList.size() > 0) {
                                    mineFragment.getNotice(mList);
                                }
                            }
                        } else {
                            if (null != mList && mList.size() > 0) {
                                boolean isNotice = mineFragment.getNotice(mList);
                                if (!isNotice) {
                                    Bundle b = new Bundle();
                                    if (null != mList && mList.size() > 0) {
                                        b.putParcelableArrayList("notice_list", (ArrayList<? extends Parcelable>) mList);
                                    }
                                    mineFragment.setArguments(b);
                                }
                            }
                        }
                        break;
                }
            }
        });
    }

    private void switch2Mine() {
        if (null == mineFragment) {
            mineFragment = new MineFragment();
        }
        switchContent(mineFragment);
        setTab(2);
    }

    private void switch2Family() {
        if (null == familyFragment) {
            familyFragment = new FamilyFragment();
        }
        switchContent(familyFragment);
        setTab(1);
    }

    private void switch2Home() {
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
        }
        switchContent(homeFragment);
        setTab(0);
    }

    private void switchContent(Fragment fragment) {
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragments(transaction);
        if (!fragment.isAdded()) {
            String tag = fragment.getClass().getName();
            transaction.add(R.id.content, fragment, tag);
            transaction.commit();
        } else {
            transaction.show(fragment).commit();
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        String tag1 = HomeFragment.class.getName();
        String tag2 = FamilyFragment.class.getName();
        String tag3 = MineFragment.class.getName();
        HomeFragment frag1 = (HomeFragment) mManager.findFragmentByTag(tag1);
        if (null != frag1) {
            transaction.hide(frag1);
        }
        FamilyFragment frag2 = (FamilyFragment) mManager.findFragmentByTag(tag2);
        if (null != frag2) {
            transaction.hide(frag2);
        }
        MineFragment frag3 = (MineFragment) mManager.findFragmentByTag(tag3);
        if (null != frag3) {
            transaction.hide(frag3);
        }
    }

    //系统和家庭消息网络请求
    private void getMsg() {
        if (null != mList && mList.size() > 0) {
            mList.clear();
        }
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(this, MyUrl.MESSAGE_SYSTEM_INFO, map, MsgInfo.class, new HttpUtils.OnxHttpCallBack<MsgInfo>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(MsgInfo msgInfo) {
                processMsgData(msgInfo);
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.d(code + msg);
            }
        });
    }

    private void getUserInfoData() {
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(this, MyUrl.USER_GET_INFO, map, GetUserInfoResult.class, new HttpUtils.OnxHttpCallBack<GetUserInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetUserInfoResult getUserInfoResult) {
                try {
                    MyApplication.myPreferences.savePhone(getUserInfoResult.getMember_mobile());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    private void processMsgData(MsgInfo msgInfo) {
        List<MsgInfo.MsgInfoBean> familyInfo = msgInfo.getFamily_info();
        List<MsgInfo.MsgInfoBean> systemInfo = msgInfo.getSystem_info();
        if (null != mList && mList.size() > 0) {
            mList.clear();
        }
        if (familyInfo != null) {
            mList.addAll(familyInfo);
        }
        if (systemInfo != null) {
            mList.addAll(systemInfo);
        }
        for (int i = 0; i < mList.size(); i++) {
            String msgNotice = mList.get(i).getNotice();
            if (msgNotice.equals("0")) {
                Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_redpoint_selector);
                drawable_me.setBounds(10, 0, 84, 68);
                rbMine.setCompoundDrawables(null, drawable_me, null, null);
                showRedPoint = true;
                mineFragment.getNotice(mList);
                break;
            } else {
                Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_selector);
                drawable_me.setBounds(0, 0, 60, 60);
                rbMine.setCompoundDrawables(null, drawable_me, null, null);
                if (TextUtils.isEmpty(orderSn)) {
                    showRedPoint = false;
                }
            }
        }
    }

    public void refreshMsg() {
        LogUtils.d("MainActivity", "refreshMsg");
        if (!MyApplication.myPreferences.readToken().equals("-1")) {
            //获取系统和家庭消息
            getMsg();
        }
    }

    public void resetMineHint() {
        showRedPoint = false;
        mineFragment.setMyOrderHint(false);
        if (null != mList) {
            mineFragment.getNotice(mList);
        }
        Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_selector);
        drawable_me.setBounds(0, 0, 60, 60);
        rbMine.setCompoundDrawables(null, drawable_me, null, null);
        getOrderUnpay();
        getMsg();
    }

    @Override
    public void okBack() {

    }

    @Override
    public void onWakeUpFinish(AppData appData, Error error) {
        if (error == null) {
            if (appData == null) return;
            try {
                //获取自定义数据
                Log.d("OpenInstall", "getInstall : bindData = " + appData.getData());
                String installData = appData.getData();
                InstallBean installBean = GsonUtils.GsonToBean(installData, InstallBean.class);
                MyApplication.myPreferences.saveInviteCode(installBean.getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "errorMsg : " + error.toString());
        }
    }

    private Boolean isExit = false; // 双击退出程序

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); // 调用双击退出函数
        }
        return false;
    }

    /**
    * 双击返回键退出程序
    */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            MobclickAgent.onKillProcess(this);
            System.exit(0);
        }
    }

    private void setTab(int position) {
        try {
            switch (position) {
                case 0:
                    rbHome.setChecked(true);
                    rbFamily.setChecked(false);
                    rbMine.setChecked(false);
                    break;
                case 1:
                    rbHome.setChecked(false);
                    rbFamily.setChecked(true);
                    rbMine.setChecked(false);
                    break;
                case 2:
                    rbHome.setChecked(false);
                    rbFamily.setChecked(false);
                    rbMine.setChecked(true);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置底部图标大小
    private void setTabSize() {
        //定义底部标签图片大小和位置
        Drawable drawable_news = getResources().getDrawable(R.drawable.tab_smart_selector);
        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
        drawable_news.setBounds(0, 0, 60, 60);
        //设置图片在文字的哪个方向
        rbHome.setCompoundDrawables(null, drawable_news, null, null);

        Drawable drawable_live = getResources().getDrawable(R.drawable.tab_home_selector);
        drawable_live.setBounds(0, 0, 74, 60);
        rbFamily.setCompoundDrawables(null, drawable_live, null, null);

        Drawable drawable_tuijian = getResources().getDrawable(R.drawable.tab_news_selector);
        drawable_tuijian.setBounds(0, 0, 60, 60);
        rbNews.setCompoundDrawables(null, drawable_tuijian, null, null);

        Drawable drawable_me = getResources().getDrawable(R.drawable.tab_gov_selector);
        drawable_me.setBounds(0, 0, 60, 60);
        rbMine.setCompoundDrawables(null, drawable_me, null, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int source = getIntent().getIntExtra("source", -1);
        boolean fromLogin = getIntent().getBooleanExtra("login_success", false);
        boolean logout_success = getIntent().getBooleanExtra("logout_success", false);
        boolean refresh_myhint = getIntent().getBooleanExtra("refresh_myhint", false);
        boolean refresh_family = getIntent().getBooleanExtra("refresh_family", false);
        if (refresh_family) {
            if (null != familyFragment) {
                familyFragment.refreshFamilyList();
            }
        }
        if (refresh_myhint) {
            resetMineHint();
        }
        if (logout_success) {
            resetMineHint();
            if (null != homeFragment) {
                homeFragment.refreshHealthData();
            }
            if (null != familyFragment) {
                familyFragment.refreshFamilyList();
            }
        }
        if (source == -1) {
            try {
                if (null != homeFragment) {
                    homeFragment.refreshHealthData();
                }
                if (null != familyFragment) {
                    familyFragment.refreshFamilyList();
                }
                if (null != mineFragment) {
                    mineFragment.getUserInfoData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (source == 1) {
            try {
                if (null != homeFragment) {
                    homeFragment.refreshHealthData();
                }
                if (fromLogin) {
                    if (null != familyFragment) {
                        familyFragment.refreshFamilyList();
                    }
                    if (null != mineFragment) {
                        mineFragment.getUserInfoData();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (source == 2) {
            boolean refreshFamily = getIntent().getBooleanExtra("refresh_family", false);
            int family_position = getIntent().getIntExtra("family_position", -1);
            try {
                if (null != familyFragment) {
                    if (refreshFamily) {
                        familyFragment.refreshFamilyList(family_position);
                    } else {
                        if (fromLogin) {
                            familyFragment.refreshFamilyList();
                        }
                    }
                }
                if (fromLogin) {
                    if (null != homeFragment) {
                        homeFragment.refreshHealthData();
                    }
                    if (null != mineFragment) {
                        mineFragment.getUserInfoData();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (source == 3) {
            if (null != mineFragment) {
                mineFragment.getUserInfoData();
            }
            if (fromLogin) {
                if (null != familyFragment) {
                    familyFragment.refreshFamilyList();
                }
                if (null != homeFragment) {
                    homeFragment.refreshHealthData();
                }
            }
        } else if (source == 4) {

        }
        if (fromLogin) {
            initRobotCall();
            getMsg();
            h.postDelayed(orderUnpayRunnable, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
//        AppManager.getAppManager().finishActivity(this);
    }

}
