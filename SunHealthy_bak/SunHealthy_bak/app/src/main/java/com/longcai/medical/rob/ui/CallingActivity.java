package com.longcai.medical.rob.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.rob.bean.Commands;
import com.longcai.medical.rob.logic.VideoCallUA;
import com.longcai.medical.rob.utils.DataUtils;
import com.longcai.medical.rob.utils.SharedPrefUtils;
import com.wilddog.video.WilddogVideoView;

/**
 * Created by liutao on 2017/9/17.
 */

public class CallingActivity extends Activity implements VideoCallUA.CallStateListener{
    private TextView stateTxt;
    private ImageButton acceptBtn, rejectBtn, cancelBtn;
    private int callId;
    private LinearLayout callingOutgoingLayout;
    private RelativeLayout callingIncomeLayout, remoteUserLayout;
    private WilddogVideoView localView;
    private String callUid;
    private boolean monitor = false;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        switch (view.getId()){
            case R.id.calling_accept:
                VideoCallUA.getInstance().acceptCall();
                Intent intent = new Intent(CallingActivity.this, CalledActivity.class);
                startActivity(intent);
                break;
            case R.id.calling_reject:
                VideoCallUA.getInstance().rejectCall();
                break;
            case R.id.calling_cancel:
                VideoCallUA.getInstance().release();
                break;
        }
        finishCall();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calling);

        Intent intent = getIntent();
        callId = intent.getIntExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, -1);
        callUid = intent.getStringExtra("callUid");
        monitor = intent.getBooleanExtra("monitor", false);
        initCallingUI();
        VideoCallUA.getInstance().setCallStateListener(this);

    }

    private void initCallingUI(){

        acceptBtn = (ImageButton)findViewById(R.id.calling_accept);
        rejectBtn = (ImageButton)findViewById(R.id.calling_reject);
        cancelBtn = (ImageButton)findViewById(R.id.calling_cancel);
        callingIncomeLayout = (RelativeLayout) findViewById(R.id.calling_come_layout);
        callingOutgoingLayout = (LinearLayout)findViewById(R.id.calling_outgoing_layout);
        remoteUserLayout = (RelativeLayout) findViewById(R.id.calling_remote_user_layout);

        localView = (WilddogVideoView)findViewById(R.id.calling_local_video_view);

        if(callId == VideoCallUA.CALL_COMING){
            localView.setVisibility(View.INVISIBLE);
            callingOutgoingLayout.setVisibility(View.INVISIBLE);
            cancelBtn.setVisibility(View.INVISIBLE);
            acceptBtn.setOnClickListener(listener);
            rejectBtn.setOnClickListener(listener);
        }
        else if(callId == VideoCallUA.CALL_OUTGOING){
            remoteUserLayout.setVisibility(View.INVISIBLE);
            callingIncomeLayout.setVisibility(View.INVISIBLE);
            cancelBtn.setOnClickListener(listener);

            if(monitor){
                VideoCallUA.getInstance().callVideoWithControl(callUid,"CALL_TYPE_MONITOR");
            }
            else
            {
                VideoCallUA.getInstance().callVideo(callUid,"CALL_TYPE_VIDEO");
            }

            /*
            for(int i = 0; i < 4; i++){
                if(i == 0){
                    DataUtils.setRobotMoveAction(callUid, SharedPrefUtils.getConfigInfo(CallingActivity.this, "currentUid"), Commands.MOVE_RIGHT);
                }
                else if(i==1){
                    DataUtils.setRobotMoveAction(callUid, SharedPrefUtils.getConfigInfo(CallingActivity.this, "currentUid"), Commands.MOVE_FORWARD);
                }
                else if(i==2){
                    DataUtils.setRobotMoveAction(callUid, SharedPrefUtils.getConfigInfo(CallingActivity.this, "currentUid"), Commands.MOVE_LEFT);
                }
                else if(i==3){
                    DataUtils.setRobotMoveAction(callUid, SharedPrefUtils.getConfigInfo(CallingActivity.this, "currentUid"), Commands.MOVE_BACK);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            */
        }

    }

    @Override
    public void finishCall() {
        VideoCallUA.getInstance().setCallStateListener(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        localView.release();
        super.onDestroy();
    }
}
