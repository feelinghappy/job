package com.hrg.downfile;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.text.GetChars;
import android.util.Log;

import static android.app.DownloadManager.STATUS_PAUSED;


//广播接受者，接收下载状态
public class  DownloadReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("onReceive",action);
        if ("android.intent.action.DOWNLOAD_COMPLETE".equals(action))
        {
            Log.e("checkDownloadStatus", ">>>下载完成");
        }


    }


};
