package com.hrg.robot;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrg.robot.Commands;
import com.hrg.robot.Constants;
import com.hrg.robot.VideoCallUA;
import com.hrg.robot.SharedPrefUtils;
import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;
import com.wilddog.video.LocalStream;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideoView;
import com.wilddog.video.WilddogVideoViewLayout;

import java.util.HashMap;


/**
 * Created by liutao on 2017/9/17.
 */

public class CallActivity extends Activity implements VideoCallUA.DataChangerListener{
    private TextView stateTxt;
    private int callId;
    private RelativeLayout callingIncomeLayout, remoteUserLayout;
    private String callUid;
    private String remouteUid;
    private boolean monitor = false;
    private WilddogVideoView localView, remoteView;
    private LocalStream mLocalStream;
    private RemoteStream mRemoteStream;
    private ImageView closeBtn;
    private WilddogVideoViewLayout localViewLayout;
    private SyncReference ref;
    private SyncReference refcontrol;
    private String localuid;
    private ChildEventListener controllistener;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private Commands commands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_call);
        this.powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        this.wakeLock.acquire();
        Intent intent = getIntent();
        callId = intent.getIntExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, -1);
        callUid = intent.getStringExtra("callUid");
//        Log.e("getStringExtra callUid", callUid);
        localuid = SharedPrefUtils.getConfigInfo(CallActivity.this, Constants.WILDDOG_UID);
//        Log.e("localuid", localuid);
        initData();
        initView();
        refcontrol = WilddogSync.getInstance().getReference("robots").child(localuid).child("control");
        controllistener = refcontrol.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str = (String) dataSnapshot.getKey();
                Log.e("add control user", str);
                if (str.equals(callUid)) {
                    String strcommand = (String) dataSnapshot.getValue();
                    Log.e("add control strcommand", strcommand);
                   /* if (strcommand.equals(commands.MOVE_FORWARD)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_FORWARD, 5);
                        Log.e("ACTION_MOVE_FORWARD", "ACTION_MOVE_FORWARD");
                    } else if (strcommand.equals(commands.MOVE_BACK)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_BACK, 5);
                        Log.e("ACTION_MOVE_BACK", "ACTION_MOVE_BACK");
                    } else if (strcommand.equals(commands.MOVE_LEFT)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_LEFT, 5);
                        Log.e("ACTION_MOVE_LEFT", "ACTION_MOVE_LEFT");
                    } else if (strcommand.equals(commands.MOVE_RIGHT)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_RIGHT, 5);
                        Log.e("ACTION_MOVE_RIGHT", "ACTION_MOVE_RIGHT");
                    } else if (strcommand.equals(commands.MOVE_STOP)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_STOP, 5);
                        Log.e("ACTION_MOVE_STOP", "ACTION_MOVE_STOP");
                    }*/

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String str = (String) dataSnapshot.getKey();
                Log.e("ch  control user", str);
                if (str.equals(callUid)) {
                    String strcommand = (String) dataSnapshot.getValue();
                    Log.e("ch  control strcoand", strcommand);
             /*       if (strcommand.equals(commands.MOVE_FORWARD)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_FORWARD, 5);
                        Log.e("ACTION_MOVE_FORWARD", "ACTION_MOVE_FORWARD");
                    } else if (strcommand.equals(commands.MOVE_BACK)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_BACK, 5);
                        Log.e("ACTION_MOVE_BACK", "ACTION_MOVE_BACK");
                    } else if (strcommand.equals(commands.MOVE_LEFT)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_LEFT, 5);
                        Log.e("ACTION_MOVE_LEFT", "ACTION_MOVE_LEFT");
                    } else if (strcommand.equals(commands.MOVE_RIGHT)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_RIGHT, 5);
                        Log.e("ACTION_MOVE_RIGHT", "ACTION_MOVE_RIGHT");
                    } else if (strcommand.equals(commands.MOVE_STOP)) {
                        zkRequest.moveControl(ZKConfig.ACTION_MOVE_STOP, 5);
                        Log.e("ACTION_MOVE_STOP", "ACTION_MOVE_STOP");
                    }*/

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
           /*     zkRequest.moveControl(ZKConfig.ACTION_MOVE_STOP, 5);
                Log.e("ACTION_MOVE_STOP", "ACTION_MOVE_STOP");*/
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(SyncError syncError) {

            }
        });


    }

    private void initData() {
        Intent intent = getIntent();
        this.remouteUid = VideoCallUA.getInstance().getRemouteUid();
    }

    private void initView() {
        localView = (WilddogVideoView) findViewById(R.id.called_local_video_view);
        remoteView = (WilddogVideoView) findViewById(R.id.called_remote_video_view);
        localViewLayout = (WilddogVideoViewLayout) findViewById(R.id.called_local_video_layout);
        mLocalStream = VideoCallUA.getInstance().getLocalStream();
        VideoCallUA.getInstance().setDataChangerListener(CallActivity.this);
        mLocalStream.attach(localView);
        localView.setZOrderMediaOverlay(true);
        localView.setMirror(true);
        localView.setScalingType(WilddogVideoView.ScalingType.SCALE_ASPECT_BALANCED);
        closeBtn = (ImageView) findViewById(R.id.call_cancel);

        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(localuid);
        HashMap<String, Object> current = new HashMap<>();
        current.put("currentUser", callUid);
        ref.setValue(current, new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("localuid error", error.toString());
                } else {
                    Log.e("localuid success", "setValue success");
                }
            }
        });
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
        ref.removeValue();
        this.wakeLock.release();
        refcontrol.removeEventListener(controllistener);
      //  zkRequest.unregister();
        VideoCallUA.getInstance().release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新获取
        this.wakeLock.acquire();
    }

  /*  @Override
    public void resultMsg(ResultData resultData) {
        Log.e("resultMsg", "ResultData" + resultData.toString());
    }

    @Override
    public void error(String s) {
        Log.e("error", "error:" + s);
    }*/
}