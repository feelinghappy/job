package com.longcai.medical.ui.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.SportHorizontalTimeAdapter;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.service.StepData;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.StepService;
import com.longcai.medical.utils.DbUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 计步器
 */
public class PedometerActivity extends BaseActivity implements Handler.Callback {


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
    TextView text_step;
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
    private long time;
    private String content;
    private String img;
    private String sportNum;
    private String str;
    private String type;
    private String familyId = "0";

    private boolean is = true;
    private List<String> data;
    private List<String> data2;

    /**
     * 获取某一天步数
     */
    private static String CURRENTDATE = "";
    private final String TAG = "StepService";
    private String DB_NAME = "basepedo";

    //循环取当前时刻的步数中间的间隔时间
    private long TIME_INTERVAL = 500;
    private Messenger messenger;
    private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
    private Handler delayHandler;
    private SportHorizontalTimeAdapter sportHorizontalTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_info);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        content = getIntent().getStringExtra("content");
        img = getIntent().getStringExtra("img");
        sportNum = getIntent().getStringExtra("sportNum");
        type = getIntent().getStringExtra("type");
        familyId = getIntent().getStringExtra("familyId");
        Log.d("1555", "运动" + familyId);
        sportInfoTimeType.setText("步行");
        sportImg.setImageURI(Uri.parse(img));
//        mbTx1.setText("4000");
        sportInfoText1.setText("步行步数");
//        mbTx2.setText("目标4000步");
//        txBottom.setText("升级vip可获得历史运动数据");
        txBottom.setText(content);
        mbTx2.setText("目标" + sportNum + "步");
//        starSport.setVisibility(View.GONE);

        starSport.setVisibility(View.GONE);

        String str = getTodayDate();
        initTodayData(str);

        delayHandler = new Handler(this);

        data = new ArrayList<>();
        data2 = new ArrayList<>();
        if (Integer.parseInt(sportNum) < 1000) {
            sportNum = "1000";
        }
        for (int i = 0; i < 9; i++) {
            data.add((i + 1) + "000");
            data2.add("false");
        }
        data2.set((Integer.parseInt(sportNum) / 1000) - 1, "true");
        MyApplication.myPreferences.saveStep(sportNum);
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

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                messenger = new Messenger(service);
                Message msg = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                msg.replyTo = mGetReplyMessenger;
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constant.MSG_FROM_SERVER:
                // 更新界面上的步数
                text_step.setText(msg.getData().getInt("step") + "");
                delayHandler.sendEmptyMessageDelayed(Constant.REQUEST_SERVER, TIME_INTERVAL);
                if (msg.getData().getInt("step") >= Integer.parseInt(sportNum)) {
                    if (isForeground(PedometerActivity.this, "com.longcai.medical")) {
                        setDialog();
                    }
                }

                break;
            case Constant.REQUEST_SERVER:
                try {
                    Message msg1 = Message.obtain(null, Constant.MSG_FROM_CLIENT);
                    msg1.replyTo = mGetReplyMessenger;
                    messenger.send(msg1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
        }
        return false;
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


    private void myadapter(boolean is) {
        if (is) {
            sportHorizontalTimeAdapter.setOnItemClickLitener(new SportHorizontalTimeAdapter.ItemClickListener() {
                @Override
                public void OnClick(String id, String string) {
                    mbTx1.setText(string + "步");
                    mbTx2.setText("目标" + string + "步");
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
                    mysport(type, text_step.getText().toString(), familyId, sportNum);

                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                } else {
                    starSportImg.setSelected(is);
                    is = true;
                    //暂停
                    mysport(type, text_step.getText().toString(), familyId, sportNum);
                }
                myadapter(is);
                break;
        }
    }

    private void initTodayData(String str) {
        CURRENTDATE = str;
//        CURRENTDATE = getTodayDate();
        Log.d("1555", "日期" + CURRENTDATE);
        DbUtils.createDb(this, DB_NAME);
        //获取当天的数据，用于展示
        List<StepData> list = DbUtils.getQueryByWhere(StepData.class, "today", new String[]{CURRENTDATE});
        if (list.size() == 0 || list.isEmpty()) {
//            ToastUtils.show(PedometerActivity.this,str+"的数据为空");
            Log.d("1555", str + "的数据为空");
        } else if (list.size() == 1) {
//            ToastUtils.show(PedometerActivity.this,str+"的数据为"+Integer.parseInt(list.get(0).getStep()));
            Log.d("1555", str + "的数据为" + Integer.parseInt(list.get(0).getStep()));
        } else {
            Log.d("1555", "出错了");
//            ToastUtils.show(PedometerActivity.this,"出错了");
        }

    }

    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private void mysport(String type, String jindu, String familyId, String sport_number) {
             /*这个地方在源码里只是访问了接口，并没有做任何处理。*/
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

}
