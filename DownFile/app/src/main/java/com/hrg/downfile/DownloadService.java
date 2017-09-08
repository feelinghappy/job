package com.hrg.downfile;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.hrg.downfile.MainActivity;
import com.hrg.downfile.DownloadReceiver;

import java.security.Provider;

public class DownloadService extends Service
{
    Context mContext;
    long mTaskId;
    DownloadManager downloadManager;
    private  DownloadBinder mBinder = new DownloadBinder();
    String downloadUrl = "http://192.168.43.95/报告模板——3D-CELL宇航员体检3大系统9项.pdf";
    String fileName = "报告模板——3D-CELL宇航员体检3大系统9项.pdf";
    class DownloadBinder extends Binder
    {
        public int getProgress()
        {
            return  0;
        }
    }
    @Override
    public IBinder onBind(Intent intent)
    {
         return  mBinder;
     }
     @Override
    public   void onCreate()
     {
         super.onCreate();
     }
    @Override
     public int onStartCommand(Intent intent,int flags,int startId)
     {
         mContext = this.getApplicationContext();
         new Thread(new Runnable() {
             @Override
             public void run() {
                 //创建下载任务,downloadUrl就是下载链接
                 DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
                 //指定下载路径和下载文件名
                 request.setAllowedOverRoaming(false);
                 //漫游网络是否可以下载
                 //在通知栏中显示，默认就是显示的
                 request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                 request.setVisibleInDownloadsUi(true);//sdcard的目录下的download文件夹，必须设置
                 request.setDestinationInExternalPublicDir("/download/", fileName);
                 //获取下载管理器
                 downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
                 //加入下载队列后会给该任务返回一个long型的id，
                 //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
                 mTaskId = downloadManager.enqueue(request);
                 //注册广播接收者，监听下载状态
                 //注册广播接收者，监听下载状态
                 DownloadReceiver downloadReceiver = new DownloadReceiver();
                 mContext.registerReceiver(downloadReceiver,
                         new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


             }
         }).start();
         return  super.onStartCommand(intent,flags,startId);
     }
     @Override
     public  void onDestroy()
     {
         super.onDestroy();
     }
    //检查下载状态

}