package com.longcai.medical.ui.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.SportHorizontalTimeAdapter;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.CountDownTimerUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 运动详情跑步页面
 */
public class SportInfoActivity extends BaseActivity {

    private static final String TAG = "SportInfoActivity";
    @BindView(R.id.bank_img)
    RelativeLayout bankImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_container)
    RelativeLayout titleContainer;
    @BindView(R.id.sport_img)
    SimpleDraweeView sportImg;
    @BindView(R.id.sport_right)
    ImageView sportRight;
    @BindView(R.id.sport_info_time_type)
    TextView sportInfoTimeType;
    @BindView(R.id.mb_tx1)
    TextView mbTx1;
    @BindView(R.id.sport_tx0)
    TextView sportTx0;
    @BindView(R.id.star_sport_img)
    ImageView starSportImg;
    @BindView(R.id.star_sport)
    LinearLayout starSport;
    @BindView(R.id.sport_tx1)
    TextView sportTx1;
    @BindView(R.id.sport_info_position1)
    RelativeLayout sportInfoPosition1;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.sport_info_position2)
    RelativeLayout sportInfoPosition2;
    @BindView(R.id.sport_info_text1)
    TextView sportInfoText1;
    @BindView(R.id.chronometer)
    TextView chronometer;
    @BindView(R.id.mb_tx2)
    TextView mbTx2;
    @BindView(R.id.sport_info_position3)
    RelativeLayout sportInfoPosition3;
    @BindView(R.id.tx_bottom)
    TextView txBottom;
    @BindView(R.id.sport_info_position4)
    RelativeLayout sportInfoPosition4;
    @BindView(R.id.activity_sport_info)
    RelativeLayout activitySportInfo;
    private List<String> data;
    private List<String> data2;
    private Intent intent;
    int minute = 0;
    private long timer_unit = 1000;
    //    private long distination_total = timer_unit * 10;
    private long timer_couting = 0 * 60 * 1000;
    private Timer timer;
    private TimerTask timerTask;
    private boolean is = true;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    chronometer.setText(formateTimer(timer_couting));
                    break;
                case 2:
                    if (isForeground(SportInfoActivity.this,"com.longcai.medical")){
                        setDialog();
                        initTimerStatus();
                        starSport.setVisibility(View.GONE);
                        sportTx1.setVisibility(View.VISIBLE);
                        chronometer.setText("00:00");
                    }
                    break;
            }
        }
    };
    private long time;
    private String content;
    private String img;
    private String sportNum;
    private String str;
    private String type;
    private String familyId = "0";
    private SportHorizontalTimeAdapter sportHorizontalTimeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_info);
        ButterKnife.bind(this);
        familyId = getIntent().getStringExtra("familyId");
        Log.d("1555", "运动" + familyId);
        content = getIntent().getStringExtra("content");
        img = getIntent().getStringExtra("img");
        sportNum = getIntent().getStringExtra("sportNum");
        type = getIntent().getStringExtra("type");
        //  txBottom.setText(content);
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        if (sportNum.equals("0")) {
            sportNum = "10";
        }
        for (int i = 0; i < 9; i++) {
            data.add((i + 1) + "0");
            data2.add("false");
        }
        //type判断早晚
        if (type.equals("1")) {
            sportInfoText1.setText("晨练时间");
        } else if (type.equals("3")) {
            sportInfoText1.setText("晚练时间");
        }
        data2.set((Integer.parseInt(sportNum) / 10) - 1, "true");
        mbTx2.setText("目标" + sportNum + "分钟");
        sportImg.setImageURI(Uri.parse(img));
        sportHorizontalTimeAdapter = new SportHorizontalTimeAdapter(this);
        sportHorizontalTimeAdapter.setData(data);
        sportHorizontalTimeAdapter.setData2(data2);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(sportHorizontalTimeAdapter);
        myadapter(true);
    }

    private void myadapter(boolean is) {

        Log.d("1555", "运行" + is);
        if (is) {
            sportHorizontalTimeAdapter.setOnItemClickLitener(new SportHorizontalTimeAdapter.ItemClickListener() {
                @Override
                public void OnClick(String id, String string) {
                    mbTx1.setText(string + "分钟");
                    mbTx2.setText("目标" + string + "分钟");
                    sportNum = string;
                    str = string;
                    int size = data2.size();
                    data2.clear();
                    for (int i = 0; i < size; i++) {
                        data2.add("false");
                    }
                    data2.set((Integer.parseInt(id)), "true");
                    sportHorizontalTimeAdapter.setData2(null);
                    sportHorizontalTimeAdapter.setData2(data2);
                    int i = Integer.parseInt(string);
//                time = (long) i * 60 * 1000;
                    time = (long) 0;
                    initTimerStatus();
                    if (!MyApplication.myPreferences.readUID().equals("-1")) {
                        mysport(type, "" + minute, familyId, sportNum);
                    }
                }
            });
        } else {
            sportHorizontalTimeAdapter.setOnItemClickLitener(new SportHorizontalTimeAdapter.ItemClickListener() {

                @Override
                public void OnClick(String id, String string) {

                }
            });
        }
    }

    private void mysport(String type, String jindu, String familyId, String sport_number) {
        Log.i(TAG, "mysport: ");
        RequestParams params = new RequestParams(MyUrl.SprotRecord);
        params.addParameter("uid", Constant.UID);
        params.addParameter("type", type);
        params.addParameter("jindu", jindu);
        params.addParameter("familyId", familyId);
        params.addParameter("sport_number", sport_number);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "mysport: onSuccess   " + result);
              //  processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "mysport: onError   " + ex.getMessage());
                ToastUtils.show(SportInfoActivity.this, ""+R.string.jsonfail);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "mysport: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "mysport: onFinished   ");

            }
        });
    }

    private void setDialog() {
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Log.d("1555", "音乐响起");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.alarm_close, null);
//        MyApplication.ScaleScreenHelper.loadView((ViewGroup) contentView);
        Button positiveButton = (Button) contentView.findViewById(R.id.alarm_close_btn);
        TextView textView = (TextView) contentView.findViewById(R.id.alarm_close_text);
        textView.setText("完成运动目标,运动要适度哦");
//        RelativeLayout layout = (RelativeLayout) contentView.findViewById(R.id.layout);
        dialog.setCanceledOnTouchOutside(false);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                dialog.dismiss();
                mysport(type, sportNum, familyId, sportNum);
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(contentView);
    }

    @OnClick({R.id.bank_img, R.id.star_sport_img, R.id.tx_bottom})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_img:
                this.finish();
                break;
            case R.id.tx_bottom:

                break;
            case R.id.star_sport_img:
                if (is) {
                    starSportImg.setSelected(is);
                    is = false;
                    //开始
                    startTimer();
                    sportTx0.setText("暂停运动");
                    timerStatus = CountDownTimerUtil.START;
                } else {
                    starSportImg.setSelected(is);
                    is = true;
                    //暂停
                    sportTx0.setText("开始运动");
                    if (!MyApplication.myPreferences.readUID().equals("-1")) {
                        mysport(type, "" + minute, familyId, sportNum);
                    }
                    timer.cancel();
                    timerStatus = CountDownTimerUtil.PASUSE;

                }
                myadapter(is);
                break;
        }
    }

    /**
     * init timer status
     * 初始化时间状态
     * 设置开始时间
     */
    private int timerStatus = CountDownTimerUtil.PREPARE;
    private void initTimerStatus() {
        timerStatus = CountDownTimerUtil.PREPARE;
        timer_couting = time;
    }

    /**
     * formate timer shown in textview
     * 格式化时间
     *
     * @param time
     * @return
     */
    private String formateTimer(long time) {
        String str = "00:00";
        int hour = 0;

        time += 1000;
        int sec = 0;
        if (time >= 1000 * 60) {
            minute = (int) time / 60 / 1000;
        }
        sec = ((int) time / 1000) % 60;

        str = formateNumber(minute) + ":" + formateNumber(sec);
        return str;
    }

    /**
     * formate time number with two numbers auto add 0
     *
     * @param time
     * @return
     */
    private String formateNumber(int time) {
        return String.format("%02d", time);
    }

    /**
     * start count down
     */
    private void startTimer() {
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.scheduleAtFixedRate(timerTask, 0, timer_unit);
    }

    /**
     * count down task
     */
    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            timer_couting += timer_unit;
            if (minute == Integer.parseInt(sportNum)) {
                cancel();
                mHandler.sendEmptyMessage(2);
                return;
            }
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
