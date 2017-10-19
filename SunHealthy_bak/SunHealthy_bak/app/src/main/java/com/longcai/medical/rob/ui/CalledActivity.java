package com.longcai.medical.rob.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.rob.bean.Commands;
import com.longcai.medical.rob.logic.RobotDataSync;
import com.longcai.medical.rob.logic.VideoCallUA;
import com.longcai.medical.rob.utils.DataUtils;
import com.longcai.medical.rob.utils.SharedPrefUtils;
import com.wilddog.client.SyncReference;
import com.wilddog.video.LocalStream;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideoView;
import com.wilddog.video.WilddogVideoViewLayout;

/**
 * Created by liutao on 2017/9/17.
 */
public class CalledActivity extends Activity implements VideoCallUA.DataChangerListener{
    private final String TAG = CalledActivity.class.getSimpleName();
    private ImageButton frontBtn, backBtn, leftBtn, rightBtn, closeBtn;
    private WilddogVideoView localView,remoteView;
    private LocalStream mLocalStream;
    private RemoteStream mRemoteStream;
    private WilddogVideoViewLayout localViewLayout;
    private SyncReference syncRef;
    private TextView waitTxt,showTxt;

    private long buttonDownTime;
    private boolean buttonDownState = false;

    private String remouteUid;
    private boolean withControl;
    private String command;


    Vibrator vibrator;

    private View.OnTouchListener listener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            command = Commands.MOVE_STOP;
            switch (view.getId()){
                case R.id.called_robot_move_front:
                    command = Commands.MOVE_FORWARD;
                    break;
                case R.id.called_robot_move_left:
                    command = Commands.MOVE_LEFT;
                    break;
                case R.id.called_robot_move_right:
                    command = Commands.MOVE_RIGHT;
                    break;
                case R.id.called_robot_move_back:
                    command = Commands.MOVE_BACK;
                    break;
            }

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                buttonDownState = true;
                buttonDownTime = System.currentTimeMillis();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long loopTime = buttonDownTime;
                        while (buttonDownState){
                            if(System.currentTimeMillis() - loopTime > 750){
                                loopTime = System.currentTimeMillis();
                                DataUtils.setRobotMoveAction(remouteUid, SharedPrefUtils.getConfigInfo(CalledActivity.this, "currentUid"),command);
                                vibrator.vibrate(50);
                            }
                        }
                    }
                }).start();
            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                buttonDownState = false;
                vibrator.vibrate(10);
                if(System.currentTimeMillis() - buttonDownTime < 750){
                    DataUtils.setRobotMoveAction(remouteUid, SharedPrefUtils.getConfigInfo(CalledActivity.this, "currentUid"),command);
                }

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_called);

        initData();
        Log.d(TAG, "withControl: " + withControl);
        initView(withControl);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    private void initData() {
        Intent intent = getIntent();
        withControl = intent.getBooleanExtra(VideoCallUA.CALL_OR_MONITOR, false);
        this.remouteUid = VideoCallUA.getInstance().getRemouteUid();
    }

    private void initView(boolean withControl) {
        localView = (WilddogVideoView)findViewById(R.id.called_local_video_view);
        remoteView = (WilddogVideoView)findViewById(R.id.called_remote_video_view);
        localViewLayout = (WilddogVideoViewLayout)findViewById(R.id.called_local_video_layout);

        waitTxt = (TextView)findViewById(R.id.called_page_wait);

        mLocalStream = VideoCallUA.getInstance().getLocalStream();
        VideoCallUA.getInstance().setDataChangerListener(CalledActivity.this);
        mLocalStream.attach(localView);

        localView.setZOrderMediaOverlay(true);
        localView.setMirror(true);
        localView.setScalingType(WilddogVideoView.ScalingType.SCALE_ASPECT_BALANCED);
        localViewLayout.setPosition(0, 66, 33, 33);

        closeBtn = (ImageButton)findViewById(R.id.called_page_colse);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCallUA.getInstance().release();
                finishCall();
            }
        });
        if(withControl){
            RelativeLayout controlPanel = (RelativeLayout)findViewById(R.id.called_page_control);
            controlPanel.setVisibility(View.VISIBLE);
            frontBtn = (ImageButton)findViewById(R.id.called_robot_move_front);
            backBtn = (ImageButton)findViewById(R.id.called_robot_move_back);
            leftBtn = (ImageButton)findViewById(R.id.called_robot_move_left);
            rightBtn = (ImageButton)findViewById(R.id.called_robot_move_right);
            frontBtn.setOnTouchListener(listener);
            backBtn.setOnTouchListener(listener);
            leftBtn.setOnTouchListener(listener);
            rightBtn.setOnTouchListener(listener);
        }

        showTxt = (TextView)findViewById(R.id.called_page_show);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //页面进入pause状态后，不在响应按钮事件,否则onTouchListener事件里的循环会在页面终止后持续运行。
         buttonDownState = false;
    }

    @Override
    public void onStreamComing() {
        waitTxt.setVisibility(View.INVISIBLE);
        mRemoteStream = VideoCallUA.getInstance().getRemoteStream();
        mRemoteStream.attach(remoteView);
    }

    @Override
    public void finishCall() {
        VideoCallUA.getInstance().setDataChangerListener(null);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.release();
        localView.release();
        VideoCallUA.getInstance().release();
    }
}
