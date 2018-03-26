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
import com.hrg.tl.turing.adapter.TuringInfoAdapter;
import com.hrg.tl.turing.domain.MessageType;
import com.hrg.tl.turing.domain.TuringBaseInfo;
import com.hrg.tl.turing.util.TuringCodeType;
import com.hrg.tl.turing.util.TuringHandler;
import com.hrg.tl.turing.util.TuringUtil;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;


public class MainActivity extends Activity implements View.OnClickListener{

    /////////////////////////////////////tts功能////////////////////////////////////////////
    // 默认云端发音人
    public static String voicerCloud="xiaoyan";
    // 默认本地发音人
    public static String voicerLocal="vils";
    // 语音合成对象
    public SpeechSynthesizer mTts_woman;
    public SpeechSynthesizer mTts_man;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    //缓冲进度
    private int mPercentForBuffering = 0;
    //播放进度
    private int mPercentForPlaying = 0;



    /////////////////////////////////////tts功能////////////////////////////////////////////
	public ListView mChatMiddleListView;
	public TuringInfoAdapter mTuringInfoAdapter;
	public List<TuringBaseInfo> mData;

	private EditText mChatBottomSendEditText;
	private Button mChatBottomSendButton;

	private static MainActivity instance;



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
        // 初始化合成对象
        mTts_woman = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
        mTts_man = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
        setParam();
		String infoValue = "你叫什么名字";
		String locValue = null;
		String useridValue = "小墨";
        if( null == mTts_woman||null == mTts_man ){
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
	void setParam()
    {
        //设置合成语速
        mTts_man.setParameter(SpeechConstant.SPEED,  "50");
        //设置合成音调
        mTts_man.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts_man.setParameter(SpeechConstant.VOLUME,  "50");
        //设置播放器音频流类型
        mTts_man.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //设置发音人
        mTts_man.setParameter(SpeechConstant.VOICE_NAME,"vils");
        //设置合成音调
        // 设置播放合成音频打断音乐播放，默认为true
        mTts_man.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts_man.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts_man.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
        //设置合成语速
        mTts_woman.setParameter(SpeechConstant.SPEED,  "50");
        mTts_woman.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts_woman.setParameter(SpeechConstant.VOLUME,  "50");
        //设置播放器音频流类型
        mTts_woman.setParameter(SpeechConstant.STREAM_TYPE, "3");
        //设置发音人

        mTts_woman.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts_woman.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts_woman.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts_woman.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.chat_bottom_send_button:
                String infoValue = mChatBottomSendEditText.getText().toString();
                // 设置参数
                int code = mTts_man.startSpeaking(infoValue,mTtsListener);
//			/**
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);

                if (code != ErrorCode.SUCCESS) {
                    Log.e("mainactivity","error");
                    Toast.makeText(MainActivity.getInstance(), "语音合成失败,错误码: " + code, Toast.LENGTH_SHORT).show();
                }
                try {
                    Thread.currentThread().sleep(1500);//阻断2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TuringBaseInfo turingBaseInfo = new TuringBaseInfo();
                turingBaseInfo.setMessageType(MessageType.TO);
                turingBaseInfo.setText(infoValue);
                turingBaseInfo.setTime(new Date());
                mData.add(turingBaseInfo);
                mTuringInfoAdapter.notifyDataSetChanged();

                String locValue = null;
                String useridValue = "shankes";

                TuringUtil.turingPost(TuringHandler.getInstance(this).handler, infoValue, locValue, useridValue);

                mChatBottomSendEditText.setText("");
                break;

            default:
                break;
        }

    }


    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            //Log.d(TAG, "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(instance,"初始化失败,错误码："+code,Toast.LENGTH_SHORT).show();
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    public SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Toast.makeText(instance,"开始播放",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakPaused() {
            Toast.makeText(instance,"暂停播放",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakResumed() {
            Toast.makeText(instance,"继续播放",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            mPercentForBuffering = percent;
            Toast.makeText(instance,String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            mPercentForPlaying = percent;
            Toast.makeText(instance,String.format(getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Toast.makeText(instance,"播放完成",Toast.LENGTH_SHORT).show();
            } else if (error != null) {
                Toast.makeText(instance,error.getPlainDescription(true),Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
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






}

