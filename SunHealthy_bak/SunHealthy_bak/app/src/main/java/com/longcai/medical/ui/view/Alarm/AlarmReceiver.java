package com.longcai.medical.ui.view.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Administrator on 2016/11/10.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("1555","闹钟broadcast");
            String msg = intent.getStringExtra("msg");
            final String id=intent.getStringExtra("id");
            final long intervalMillis = intent.getLongExtra("intervalMillis", 0);
            final boolean rest_image=intent.getBooleanExtra("rest_image",true);
            String time=intent.getStringExtra("time");
            if (intervalMillis != 0) {
                AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                        intent);
            }
            int flag = intent.getIntExtra("soundOrVibrator", 0);


             Intent i = new Intent(context, LongRunningService.class).putExtra("id",id);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (rest_image==true){
                context.startService(i);
                Log.d("1555","闹钟broadcast开启");
            }else {
                context.stopService(i);
                Log.d("1555","闹钟broadcast关闭");
            }

//        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(context, alert);
//            final AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
//            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
//                mediaPlayer.setLooping(true);
//                mediaPlayer.prepare();
//                mediaPlayer.start();
//                Log.d("1555","闹钟响起");
//                ToastUtils.show(context,"闹钟响起");
//                //提示框
////                      setDialog();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //再次开启LongRunningService这个服务，从而可以每隔多长时间响起
//        Intent i = new Intent(context, LongRunningService.class);
//        context.startService(i);
    }
}
