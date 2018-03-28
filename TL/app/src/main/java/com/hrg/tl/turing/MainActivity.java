package com.hrg.tl.turing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.hrg.tl.R;
import com.hrg.tl.kdxf.Iat;
import com.hrg.tl.kdxf.TTS;
import com.hrg.tl.turing.adapter.TuringInfoAdapter;
import com.hrg.tl.turing.domain.MessageType;
import com.hrg.tl.turing.domain.TuringBaseInfo;
import com.hrg.tl.turing.util.TuringCodeType;
import com.hrg.tl.turing.util.TuringHandler;
import com.hrg.tl.turing.util.TuringUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.hrg.tl.kdxf.util.JsonParser;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class MainActivity extends Activity implements View.OnClickListener{

	public ListView mChatMiddleListView;
	public TuringInfoAdapter mTuringInfoAdapter;
	public List<TuringBaseInfo> mData;

	private EditText mChatBottomSendEditText;
	private Button mChatBottomSendButton;

	private static MainActivity instance;
    public Iat mIat;
    public TTS mTts;


	public static MainActivity getInstance() {
		if (instance == null) {
			instance = new MainActivity();
		}
		return instance;
	}
    private Toast mToast;
    @SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat_main);
		requestPermissions();
		instance = this;
        SpeechUtility.createUtility(instance, SpeechConstant.APPID +"=5aa8dafe");
        mIat = new Iat(instance);
        mIat.Init();
        mTts = new TTS(instance);
        mTts.Init();
		String infoValue = "你叫什么名字";
		String locValue = null;
		String useridValue = "小墨";
        if(( null == mTts)||( null == mIat)){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            Toast.makeText(this,"创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化",Toast.LENGTH_SHORT ).show();
            return;
        }
		TuringUtil.turingPost(TuringHandler.getInstance(this).handler, infoValue, locValue, useridValue);
		initView();
		initData();
		initEventClick();


	}

    private void initData() {
        mData = new ArrayList<TuringBaseInfo>();
        mTuringInfoAdapter = new TuringInfoAdapter(this, mData, mChatMiddleListView);
        mChatMiddleListView.setAdapter(mTuringInfoAdapter);
    }

    private void initView() {
        mChatMiddleListView = (ListView) findViewById(R.id.chat_middle_listview);
        mChatBottomSendEditText = (EditText) findViewById(R.id.chat_bottom_send_edittext);
        mChatBottomSendButton = (Button) findViewById(R.id.chat_bottom_send_button);
    }

	private void initEventClick() {
		mChatBottomSendButton.setOnClickListener(this);
	}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chat_bottom_send_button:
            ///////////////////语音识别方面的code////////////////////////////

            //boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), true);
            /*if (TRUE) {
                // 显示听写对话框
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
                Toast.makeText(instance,getString(R.string.text_begin),Toast.LENGTH_SHORT).show();
            } else {
                // 不显示听写对话框
                ret = mIat.startListening(mRecognizerListener);
                if (ret != ErrorCode.SUCCESS) {
                    Toast.makeText(instance,"听写失败,错误码：" + ret,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(instance,getString(R.string.text_begin),Toast.LENGTH_SHORT).show();
                }
            }*/
            mIat.StartIAT();
            break;
            ///////////////////////////////////////////////

 /*           case R.id.chat_bottom_send_button:
                String infoValue = mChatBottomSendEditText.getText().toString();
                // 设置参数
                int code = mTts_man.startSpeaking(infoValue,mTtsListener);


                if (code != ErrorCode.SUCCESS) {
                    Log.e("mainactivity","error");
                    Toast.makeText(MainActivity.getInstance(), "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.currentThread().sleep(1500);//阻断2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                turingBaseInfo = new TuringBaseInfo();
                turingBaseInfo.setMessageType(MessageType.TO);
                turingBaseInfo.setText(infoValue);
                turingBaseInfo.setTime(new Date());
                mData.add(turingBaseInfo);
                mTuringInfoAdapter.notifyDataSetChanged();

                String locValue = null;
                String useridValue = "shankes";

                TuringUtil.turingPost(TuringHandler.getInstance(this).handler, infoValue, locValue, useridValue);

                mChatBottomSendEditText.setText("");
                break;*/

            default:
                break;
        }

    }





	private void requestPermissions(){
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				int permission = ActivityCompat.checkSelfPermission(instance,
						Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if(permission!= PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(instance,new String[] {
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS},0x0010);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mIat ){
            // 退出时释放连接
            mIat.Destroy();
        }
        if( null != mTts ){
            // 退出时释放连接
            mTts.Destroy();
        }
    }

}

