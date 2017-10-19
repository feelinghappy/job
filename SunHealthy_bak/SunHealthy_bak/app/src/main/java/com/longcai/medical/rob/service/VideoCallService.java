package com.longcai.medical.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import com.longcai.medical.rob.bean.Constants;
import com.longcai.medical.rob.logic.RobotInit;
import com.longcai.medical.rob.ui.RobotHomeActivity;
import com.longcai.medical.rob.utils.SharedPrefUtils;
import com.longcai.medical.rob.logic.VideoCallUA;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.model.WilddogUser;
/**
 * Created by liutao on 2017/9/21.
 */

public class VideoCallService extends Service {
    private WilddogAuth wilddogAuth;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wilddogAuth = WilddogAuth.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerUserState(VideoCallService.this.getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    private void registerUserState(final Context context){
        wilddogAuth.addAuthStateListener(new WilddogAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(WilddogAuth wilddogauth){
                WilddogUser wilddogUser = wilddogauth.getCurrentUser();
                if (wilddogUser != null) {
                    toast("*Login success!");
                    SharedPrefUtils.saveConfigInfo(VideoCallService.this, "currentUid", wilddogUser.getUid());
                    SharedPrefUtils.saveConfigInfo(VideoCallService.this, Constants.WILDDOG_TOKEN_SAVE_TIME, ""+System.currentTimeMillis());
                    VideoCallUA.getInstance().initVideoSDK();
                    VideoCallUA.getInstance().setOnline(true);

                } else {
                    VideoCallUA.getInstance().setOnline(false);
                    // 没有用户登录
                }
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(VideoCallService.this, msg, Toast.LENGTH_SHORT).show();
    }
}
