package com.longcai.medical.ui.view.Alarm;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.MineRest;
import com.longcai.medical.utils.DbUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */

public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //数据库
    private String DB_NAME = "minerest";
    //搜索的东西
    private String CURRENTDATE="";

    List<MineRest> mineRestsBoo;
    Calendar calendar= Calendar.getInstance();
    //振动
    Vibrator vibrator;
    int pos = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int anHour = 10 * 1000;
//        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
//        Intent alarmintent = new Intent(this, AlarmReceiver.class);
//        //pi对象设置动作，启动的是activity还是service
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, alarmintent, 0);

   //     manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        final String id =intent.getStringExtra("id");

        Log.d("1555","id"+id);
        DbUtils.createDb(this, DB_NAME);
        CURRENTDATE="all";
        mineRestsBoo=DbUtils.getQueryByWhere(MineRest.class,"str",new String[]{CURRENTDATE});
        for (int i=0;i<mineRestsBoo.size();i++){
            Log.d("1555","id-----------"+id);
            Log.d("1555","pos-----------"+mineRestsBoo.get(i).getTime());
            if (id.equals(mineRestsBoo.get(i).getTime())){
                pos=i;
                Log.d("1555","匹配到");
            }
        }
        Log.d("1555", "闹钟service开启"+pos);

        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                if (mineRestsBoo.get(pos).getType().equals("0")){
       /*
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         * */
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
//                    vibrator.vibrate(pattern, -1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
                    vibrator.vibrate(5000);
                    Log.d("1555","振动开启");
                }else if (mineRestsBoo.get(pos).getType().equals("1")){
                    mediaPlayer.start();
                    Log.d("1555","铃声开启");
                }else if (mineRestsBoo.get(pos).getType().equals("2")){
                    Log.d("1555","铃声振动开启");
                    mediaPlayer.start();
                           /*
         * 想设置震动大小可以通过改变pattern来设定，如果开启时间太短，震动效果可能感觉不到
         * */
                    vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
//                    vibrator.vibrate(pattern, 2);           //重复两次上面的pattern 如果只想震动一次，index设为-1
                    vibrator.vibrate(5000);
                }
            }

//            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            final AlertDialog dialog = builder.create();
//            View contentView = LayoutInflater.from(this).inflate(
//                    R.layout.sport_over_view, null);
//            MyApplication.ScaleScreenHelper.loadView((ViewGroup) contentView);
//            dialog.show();
//            dialog.getWindow().setContentView(contentView);

            AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
//            builder.setMessage("闹钟");
//            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    mediaPlayer.stop();
//                    Log.d("1555", "闹钟service关闭");
//                }
//            });

            /**
             * 修改状态
             */
//            DbUtils.createDb(this, DB_NAME);
//            CURRENTDATE=""+id;
//            mineRestsBoo=DbUtils.getQueryByWhere(MineRest.class,"id",new String[]{CURRENTDATE});

//            mineRestsBoo.get(0).setRestboolean(false);
            final AlertDialog ad=builder.create();
            View contentView= LayoutInflater.from(this).inflate(R.layout.alarm_close,null);
//            MyApplication.ScaleScreenHelper.loadView((ViewGroup) contentView);
            Button positiveButton= (Button) contentView.findViewById(R.id.alarm_close_btn);
            TextView textView= (TextView) contentView.findViewById(R.id.alarm_close_text);
            textView.setText(mineRestsBoo.get(pos).getTitle()+"时间到了");
            //悬浮窗
            ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            //如果当前系统设备版本号大于19
//            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//                ad.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
//                Log.d("1555","系统api大于19");
//            }else {
//
//                Log.d("1555","系统api小于19");
//            }
            //点击外面区域不会消失
            ad.setCanceledOnTouchOutside(false);
            ad.show();
            ad.getWindow().setContentView(contentView);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mediaPlayer.stop();
                    ad.dismiss();
                    //取消震动
                    vibrator.cancel();
                    Log.d("1555","之前"+mineRestsBoo.get(pos).getFlag()+"判断--"+mineRestsBoo.get(pos).isRestboolean());
                    if (mineRestsBoo.get(pos).getFlag().equals("1")){
                        Log.d("1555","之后"+mineRestsBoo.get(pos).isRestboolean());
                    }else if (mineRestsBoo.get(pos).getFlag().equals("0")){
//                        MineRest data =mineRestsBoo.get(id);
//                        mineRestsBoo.get(id).setRestboolean(false);
//                        data.setRestboolean(true);
                        mineRestsBoo.get(pos).setRestboolean(false);
                        MineRest datas = mineRestsBoo.get(pos);
                        datas.setRestboolean(false);
                        DbUtils.update(datas);
                        Log.d("1555","之前"+mineRestsBoo.get(pos).isRestboolean());
                    }
                    Log.d("1555", "闹钟service关闭");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在Service结束后关闭AlarmManager
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent i = new Intent(this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        Log.d("1555", "关闭闹钟service");
//        manager.cancel(pi);
    }
}
