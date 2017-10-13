package com.hrg.robotproject;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class LoginService extends Service
{
    private String LOG = "LoginService";

    public void onCreate() {
        super.onCreate();
        Log.e(LOG, "Oncreate");
        Toast.makeText(getApplicationContext(), LOG + "onCreate start!",
                Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), LOG + "onCreate end!",
                Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       return START_STICKY_COMPATIBILITY;
       //return super.onStartCommand(intent, flags, startId);
    }


    public void onDestroy()
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, LoginService.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }


    @Override
    public void onStart(Intent intent, int startId)
    {
// 再次动态注册广播
        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
        BootCompletedReceiver searchReceiver = new BootCompletedReceiver();
        registerReceiver(searchReceiver, localIntentFilter);
        super.onStart(intent, startId);
    }
}