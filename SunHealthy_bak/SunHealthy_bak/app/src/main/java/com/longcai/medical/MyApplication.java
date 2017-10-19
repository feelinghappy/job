package com.longcai.medical;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.fm.openinstall.OpenInstall;
import com.longcai.medical.rob.bean.Constants;
import com.longcai.medical.utils.MyPreferences;
import com.umeng.analytics.MobclickAgent;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import org.xutils.x;


/**
 * Created by xinghongfei on 16/8/12.
 */
public class MyApplication extends MultiDexApplication {
//    private static final String TAG = "AppApplication";
//    public static MyApplication myApplication;
    public static MyPreferences myPreferences;
    public static Context mContext;
    public static int source = -1;
    public static boolean isWeixinPayFromUser = false;
//    private static Context context;
//    private static Handler handler;
//    private static int mainThreadId;
//    public static com.zcx.helper.scale.ScaleScreenHelper ScaleScreenHelper;
    /** Activity 栈 */
//    public ActivityStack mActivityStack = null;
    @Override
    public void onCreate() {
        super.onCreate();
//        context = getApplicationContext();
//        mActivityStack = new ActivityStack();   // 初始化Activity 栈
//        handler = new Handler();
//        mainThreadId = android.os.Process.myTid();
        mContext = this;
//        myApplication = this;
//        try {
//            Helper.Initialize(getApplicationContext(), "yiliao");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        init();
        initWildDogSDK();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        if (isMainProcess()) {
            OpenInstall.init(this);
        }
    }

    private void init() {
        //xutils的初始化
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.
//        x.Ext.setDebug(BuildConfig.DEBUG);
        myPreferences = new MyPreferences(getApplicationContext(), "yiliao");
//        Fresco.initialize(this);//初始化Fresco图片框架
//        PgyCrashManager.register(this);
//        ScaleScreenHelper = ScaleScreenHelperFactory.create(this, 720);
        instance = this;
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setCatchUncaughtExceptions(true);

//        SDKInitializer.initialize(getApplicationContext());//初始化百度地图
    }

    public boolean isMainProcess() {

        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }
//    public static Handler getHandler() {
//        return handler;
//    }
//
//    public static int getMainThreadId() {
//        return mainThreadId;
//    }

//    public static Application getContext() {
//        return myApplication;
//    }

//    public String readText(Context context, String assetPath) {
//        LogUtils.d("read assets file as text: " + assetPath);
//        try {
//            return ConvertUtils.toString(context.getResources().getAssets().open(assetPath));
//        } catch (Exception e) {
//            LogUtils.e(e);
//            return "";
//        }
//    }

    private void initWildDogSDK(){
        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://"+ Constants.appId+".wilddogio.com").build();
        WilddogApp.initializeApp(this.getApplicationContext(), options);
    }

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

}
