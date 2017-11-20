package com.hrg.robot;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        try {
            VideoCallUA.getInstance().callVideo(callUid, "CALL_TYPE_VIDEO");
        }
        catch (Exception e)
        {
            Log.e("callVideo",e.toString());
        }
        VideoCallUA.getInstance().setCallStateListener(this);
        ImageView calling_reject_video = (ImageView)findViewById(R.id.calling_reject_video);
        calling_reject_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CallingActivity.this, "关闭",Toast.LENGTH_SHORT).show();
                VideoCallUA.getInstance().rejectCall();
                finishCall();
            }
        });

    }


    @Override
    public void finishCall() {
        VideoCallUA.getInstance().setCallStateListener(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        if(localView!=null) {
            localView.release();
        }
        super.onDestroy();
    }
}