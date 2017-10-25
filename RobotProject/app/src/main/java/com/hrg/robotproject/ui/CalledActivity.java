package  com.hrg.robotproject.ui;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrg.robotproject.R;
import com.hrg.robotproject.bean.Commands;
import com.hrg.robotproject.logic.RobotDataSync;
import com.hrg.robotproject.logic.VideoCallUA;
import com.hrg.robotproject.utils.DataUtils;
import  com.hrg.robotproject.utils.SharedPrefUtils;
import com.wilddog.client.SyncReference;
import com.wilddog.video.LocalStream;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideoView;
import com.wilddog.video.WilddogVideoViewLayout;

/**
 * Created by liutao on 2017/9/17.
 */
public class CalledActivity extends Activity  implements VideoCallUA.CallStateListener{
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
    private String callUid;
    private ImageView imgAccept;
    private ImageView imgReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_called);
        Intent intent = getIntent();
        callUid = intent.getStringExtra("callUid");
        TextView tv = (TextView)findViewById(R.id.txtName);
        tv.setText(callUid);
        imgAccept = (ImageView)findViewById(R.id.calling_accept);
        imgReject = (ImageView)findViewById(R.id.calling_reject);
        imgReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCallUA.getInstance().rejectCall();
                CalledActivity.this.finish();

            }
        });
        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoCallUA.getInstance().acceptCall();
                Intent intent = new Intent(CalledActivity.this, CallActivity.class);
                intent.putExtra("callUid",callUid);
                startActivity(intent);
                CalledActivity.this.finish();
            }
        });
        VideoCallUA.getInstance().setCallStateListener(this);
    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finishCall() {
        VideoCallUA.getInstance().setDataChangerListener(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
