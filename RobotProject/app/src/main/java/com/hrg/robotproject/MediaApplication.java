package com.hrg.robotproject;
import android.app.Application;
import android.provider.SyncStateContract;
import com.hrg.robotproject.bean.Constants;
import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogauth.WilddogAuth;
import android.content.Context;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * Created by chaihua on 16-8-22.
 */
public class MediaApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化WilddogApp,完成初始化之后可在项目任意位置通过getInstance()获取Sync & Auth对象
        WilddogOptions.Builder builder = new WilddogOptions.Builder().setSyncUrl("https://" + Constants.appId + ".wilddogio" + ".com");
        WilddogOptions options = builder.build();
        WilddogApp.initializeApp(getApplicationContext(), options);
        WilddogAuth mauth= WilddogAuth.getInstance();
        SyncReference ref = WilddogSync.getInstance().getReference();
        mContext = this;
    }
}