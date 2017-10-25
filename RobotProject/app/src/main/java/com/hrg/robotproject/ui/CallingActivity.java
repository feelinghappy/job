package com.hrg.robotproject.ui;

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

import com.hrg.robotproject.R;
import com.hrg.robotproject.bean.Commands;
import com.hrg.robotproject.logic.VideoCallUA;
import com.hrg.robotproject.utils.DataUtils;
import com.hrg.robotproject.utils.SharedPrefUtils;
import com.wilddog.video.WilddogVideoView;

/**
 * Created by liutao on 2017/9/17.
 */

public class CallingActivity extends Activity implements VideoCallUA.CallStateListener{
    private TextView stateTxt;
    private ImageButton acceptBtn, rejectBtn, cancelBtn;
    private int callId;
    private WilddogVideoView localView;
    private String callUid;
    private boolean monitor = false;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.calling_reject:
                    VideoCallUA.getInstance().rejectCall();
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
        VideoCallUA.getInstance().setCallStateListener(this);

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