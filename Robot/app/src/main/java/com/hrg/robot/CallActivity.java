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
import android.widget.Toast;

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

public class CallActivity extends Activity implements VideoCallUA.DataChangerListener,ZKCallback,VideoCallUA.CallStateListener {
    private TextView stateTxt;
    private int callId;
    private RelativeLayout callingIncomeLayout, remoteUserLayout;
    private String strcallUid;
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
    private ZKRequest zkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_call);
        VideoCallUA.getInstance().setCallStateListener(CallActivity.this);
        zkRequest = new ZKRequest(this, this);
        this.powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        this.wakeLock.acquire();
        Intent intent = getIntent();
        //callId = intent.getIntExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, -1);
        strcallUid = intent.getStringExtra("callUid");
        Log.e("getStringExtra callUid", strcallUid);
        localuid = SharedPrefUtils.getConfigInfo(CallActivity.this, Constants.WILDDOG_UID);
        Log.e("localuid", localuid);
        initData();
        initView();
        SyncReference ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(localuid).child("currentUser");
        ref.setValue(strcallUid, new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("currentUser error", error.toString());
                } else {
                    Log.e("currentUser success", "setValue success");
                }
            }
        });
        refcontrol = WilddogSync.getInstance().getReference("robots").child(localuid).child("control");
        controllistener = refcontrol.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String str = (String) dataSnapshot.getKey();
                Log.e("add control user", str);
                if (str.equals(strcallUid)) {
                    String strcommand = (String) dataSnapshot.getValue();
                    Log.e("add control strcommand", strcommand);
                    if (strcommand.equals(commands.MOVE_FORWARD)) {
                        if (zkRequest != null){
                            zkRequest.moveControl(MoveDirection.FORWARD);
                            Log.e("commands.MOVE_FORWARD", "zkRequest != null");
                        }
                    } else if (strcommand.equals(commands.MOVE_BACK)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.BACKWARD);
                            Log.e("ACTION_MOVE_BACK", "ACTION_MOVE_BACK");
                        }

                    } else if (strcommand.equals(commands.MOVE_LEFT)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.TURN_LEFT);
                            Log.e("ACTION_MOVE_LEFT", "ACTION_MOVE_LEFT");
                        }

                    } else if (strcommand.equals(commands.MOVE_RIGHT)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.TURN_RIGHT);
                            Log.e("ACTION_MOVE_RIGHT", "ACTION_MOVE_RIGHT");
                        }

                    }
                    refcontrol.setValue(" ", new SyncReference.CompletionListener() {
                        @Override
                        public void onComplete(SyncError syncError, SyncReference syncReference) {
                            if (syncError != null) {
                                Log.e("refcontrol error", syncError.toString());
                                Toast.makeText(getApplicationContext(), syncError.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("refcontrol success", "refcontrol success");
                                Toast.makeText(getApplicationContext(), "refcontrol success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String str = (String) dataSnapshot.getKey();
                Log.e("ch  control user", str);
                if (str.equals(strcallUid)) {
                    String strcommand = (String) dataSnapshot.getValue();
                    Log.e("ch  control strcoand", strcommand);
                    if (strcommand.equals(commands.MOVE_FORWARD)) {
                        if (zkRequest != null){
                            zkRequest.moveControl(MoveDirection.FORWARD);
                            Log.e("commands.MOVE_FORWARD", "zkRequest != null");
                            Toast.makeText(getApplicationContext(), "commands.MOVE_FORWARD zkRequest != null", Toast.LENGTH_SHORT).show();

                        }
                    } else if (strcommand.equals(commands.MOVE_BACK)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.BACKWARD);
                            Log.e("ACTION_MOVE_BACK", "ACTION_MOVE_BACK");
                            Toast.makeText(getApplicationContext(), "ACTION_MOVE_BACK", Toast.LENGTH_SHORT).show();
                        }

                    } else if (strcommand.equals(commands.MOVE_LEFT)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.TURN_LEFT);
                            Log.e("ACTION_MOVE_LEFT", "ACTION_MOVE_LEFT");
                            Toast.makeText(getApplicationContext(), "ACTION_MOVE_LEFT", Toast.LENGTH_SHORT).show();
                        }

                    } else if (strcommand.equals(commands.MOVE_RIGHT)) {
                        if (zkRequest != null) {
                            zkRequest.moveControl(MoveDirection.TURN_RIGHT);
                            Log.e("ACTION_MOVE_RIGHT", "ACTION_MOVE_RIGHT");
                            Toast.makeText(getApplicationContext(), "ACTION_MOVE_RIGHT", Toast.LENGTH_SHORT).show();
                        }

                    }
                    refcontrol.setValue(" ", new SyncReference.CompletionListener() {
                        @Override
                        public void onComplete(SyncError syncError, SyncReference syncReference) {
                            if (syncError != null) {
                                Log.e("refcontrol error", syncError.toString());
                                Toast.makeText(getApplicationContext(), syncError.toString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("refcontrol success", "refcontrol success");
                                Toast.makeText(getApplicationContext(), "refcontrol success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


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
        current.put("currentUser", strcallUid);
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
        zkRequest.unregister();
        VideoCallUA.getInstance().release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新获取
        this.wakeLock.acquire();
    }


/*    resultData:ResultData{type=1, obj=RobotData{status=1111, robotName='',
      devSN=HSRasd0170400003, electricity=0}}*/
 /*   resultMsg: resultData:ResultData{type=2, obj=AirData{fanStatus=1, anionStatus=1,
     electricity='0', isCharging=false, co='0',
     co_decimal='0', ch2o='0', ch2o_decimal='43',
     pm='23', humidity='49', humidity_decimal='9', temperature='25', temperature_decimal='8', gas=1}}*/

    @Override
    public void resultMsg(ResultData resultData) {
        Log.e("resultMsg", "ResultData" + resultData.toString());
        if (resultData.getType()== 5)////返回数据类型，1：机器人基本信息，2：空气盒子信息，3：监控状态信息，4：控制开关结果信息，5：控制移动
        {

        }


    }



    @Override
    public void error(String s) {
        Log.e("error", "error:" + s);
    }
}