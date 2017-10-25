package com.hrg.robotproject.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrg.robotproject.R;
import com.hrg.robotproject.bean.Commands;
import com.hrg.robotproject.logic.VideoCallUA;
import com.hrg.robotproject.utils.DataUtils;
import com.hrg.robotproject.utils.SharedPrefUtils;
import com.wilddog.video.LocalStream;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideoView;
import com.wilddog.video.WilddogVideoViewLayout;

/**
 * Created by liutao on 2017/9/17.
 */

public class CallActivity extends Activity implements VideoCallUA.DataChangerListener{
    private TextView stateTxt;
    private int callId;
    private LinearLayout callingOutgoingLayout;
    private RelativeLayout callingIncomeLayout, remoteUserLayout;
    private String callUid;
    private String remouteUid;
    private boolean monitor = false;
    private WilddogVideoView localView,remoteView;
    private LocalStream mLocalStream;
    private RemoteStream mRemoteStream;
    private ImageView closeBtn;
    private  WilddogVideoViewLayout localViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_call);
        Intent intent = getIntent();
        callId = intent.getIntExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, -1);

        initData();
        initView();

    }
    private void initData() {
        Intent intent = getIntent();
        this.remouteUid = VideoCallUA.getInstance().getRemouteUid();
    }
    private void initView() {
        localView = (WilddogVideoView)findViewById(R.id.called_local_video_view);
        remoteView = (WilddogVideoView)findViewById(R.id.called_remote_video_view);
        localViewLayout = (WilddogVideoViewLayout)findViewById(R.id.called_local_video_layout);
        mLocalStream = VideoCallUA.getInstance().getLocalStream();
        VideoCallUA.getInstance().setDataChangerListener(CallActivity.this);
        mLocalStream.attach(localView);
        localView.setZOrderMediaOverlay(true);
        localView.setMirror(true);
        localView.setScalingType(WilddogVideoView.ScalingType.SCALE_ASPECT_BALANCED);
        closeBtn = (ImageView)findViewById(R.id.call_cancel);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoCallUA.getInstance().release();
                finishCall();
                CallActivity.this.finish();
            }
        });
    }


    @Override
    public void onStreamComing() {
        mRemoteStream = VideoCallUA.getInstance().getRemoteStream();
        mRemoteStream.attach(remoteView);
    }

    @Override
    public void finishCall() {
        VideoCallUA.getInstance().setCallStateListener(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localView.release();
        remoteView.release();
        VideoCallUA.getInstance().release();
    }




}