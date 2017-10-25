package com.hrg.robotproject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        Intent service = new Intent(context, LoginService.class);
        context.startService(service);
        Log.e("TAG", "开机自动服务自动启动.....");

        //这个必须添加flags


    }
}