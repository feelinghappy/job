package com.hrg.robot;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.hrg.robot.MediaApplication;
import com.hrg.robot.CallActivity;
import com.hrg.robot.CalledActivity;
import com.hrg.robot.Constants;
import com.hrg.robot.SharedPrefUtils;
import com.wilddog.video.CallStatus;
import com.wilddog.video.Conversation;
import com.wilddog.video.LocalStream;
import com.wilddog.video.LocalStreamOptions;
import com.wilddog.video.RemoteStream;
import com.wilddog.video.WilddogVideo;
import com.wilddog.video.WilddogVideoError;
import com.wilddog.wilddogauth.WilddogAuth;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by liutao on 2017/9/17.
 */

public class VideoCallUA {

    public final String TAG = VideoCallUA.class.getSimpleName();

    //使用完要释放，否则会导致内存泄露
    private DataChangerListener dataChangerListener;
    private CallStateListener callStateListener;

    private WilddogVideo wilddogVideo;
    private Conversation conversation;
    private RemoteStream remoteStream;
    private LocalStream localStream;
    private String remouteUid;

    public static String CALL_COMING_OR_OUTGOING = "coming_or_outgoing";
    public static String CALL_OR_MONITOR = "call_or_monitor";
    public static int CALL_COMING = 1; //呼入
    public static int CALL_OUTGOING = 2;//呼出
    public static boolean withControl = false; //通话界面是否带控制方向盘 。
    private static Context mContext = null;
    public boolean online = false; //通话界面是否带控制方向盘 。

    public interface DataChangerListener {
        void onStreamComing();
        void finishCall();
    }

    public interface CallStateListener {
        void finishCall();
    }

    private static class SingletonHolder{
        private static final VideoCallUA instance = new VideoCallUA();
    }

    private Conversation.Listener listener = new Conversation.Listener(){
        @Override
        public void onCallResponse(CallStatus callStatus) {
            String state = "";
            switch (callStatus){
                case ACCEPTED:
                    Intent intent = new Intent(mContext, CallActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Log.d(TAG,"通话被接受");
                    state = "accept";
                    break;
                case REJECTED:
                    Log.d(TAG,"通话被拒绝");
                    state = "reject";

                    break;
                case BUSY:
                    state = "busy";
                    Log.d(TAG,"正忙");
                    break;
                case TIMEOUT:
                    Log.d(TAG,"超时");
                    state = "timeout";
                    break;
                default:
                    Log.d(TAG,"状态未识别");
                    state = "unkown";
                    break;

            }
            Toast.makeText(mContext, state, Toast.LENGTH_SHORT).show();
            callStateListener.finishCall();
        }

        @Override
        public void onStreamReceived(RemoteStream remoteStream) {
            remoteStream.enableVideo(true);
            remoteStream.enableAudio(true);
            //通知和远程已经建立连接
            setRemoteStream(remoteStream);
            dataChangerListener.onStreamComing();
        }

        @Override
        public void onClosed() {
            Log.d(TAG, "onClosed: "+"*****");
            VideoCallUA.getInstance().rejectCall();
            if(callStateListener != null){
                callStateListener.finishCall();
            }
            if(dataChangerListener != null) {
                dataChangerListener.finishCall();
            }
            release();

        }

        @Override
        public void onError(WilddogVideoError wilddogVideoError) {
            Log.d(TAG,"错误" + wilddogVideoError.getMessage());
            if(callStateListener!=null){
                callStateListener.finishCall();
            }
            if(dataChangerListener != null) {
                dataChangerListener.finishCall();
            }
            release();
        }
    };

    private VideoCallUA(){
        mContext = MediaApplication.mContext;
        //初始化Video
        initVideoSDK();
    }

    public static VideoCallUA getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 初始化视频通话sdk，设置通话状态监听
     */
    public void initVideoSDK() {
        if(WilddogAuth.getInstance().getCurrentUser() != null) {
            String token = WilddogAuth.getInstance().getCurrentUser().getToken(false).getResult().getToken();
            WilddogVideo.initialize(mContext, Constants.videoId, token);
            wilddogVideo = WilddogVideo.getInstance();
            wilddogVideo.setListener(new WilddogVideo.Listener() {
                @Override
                public void onCalled(Conversation conversation, String s) {
                    setConversation(conversation);
                    getConversation().setConversationListener(listener);
                    Intent intent = new Intent(mContext, CalledActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("callUid", conversation.getRemoteUid());
                    Log.e("callUid",conversation.getRemoteUid());
                    intent.putExtra(CALL_COMING_OR_OUTGOING, CALL_COMING);

                    mContext.startActivity(intent);
                }

                @Override
                public void onTokenError(WilddogVideoError wilddogVideoError) {
                    Log.d(TAG, "onTokenError: " + "初始化sdk失败。");
                }
            });
        }
    }

    /**
     * 呼叫
     * @param remoteUid
     * @param data
     */
    public  void callVideo(String remoteUid, String data){
        if(isOnline()){
            this.setRemouteUid(remoteUid);
            this.localStream = createLocalStream();
            this.conversation = wilddogVideo.call(remoteUid, localStream, "liutao");
            this.conversation.setConversationListener(listener);
        }
        else {
            Log.d(TAG, "User is not online, please relogin.");
            Toast.makeText(mContext, "User is not online, please relogin.", Toast.LENGTH_LONG).show();
        }

    }



    /**
     * 接听
     */
    public void acceptCall(){
        this.localStream = createLocalStream();
        this.setRemouteUid(this.conversation.getRemoteUid());
        this.conversation.accept(localStream);
    }

    /**
     * 拒接
     */
    public void rejectCall(){
        this.conversation.reject();
        release();
    }

    /**
     * 创建本地流
     * @return
     */
    public LocalStream createLocalStream(){
        LocalStreamOptions.Builder builder = new LocalStreamOptions.Builder();
        LocalStreamOptions options = builder.captureAudio(true).captureVideo(true).dimension(LocalStreamOptions.Dimension.DIMENSION_720P).build();
        this.localStream = wilddogVideo.createLocalStream(options);
        this.localStream.enableVideo(true);
        this.localStream.enableAudio(true);
        return localStream;
    }

    /**
     * 通话结束释放资源
     */
    public void release(){
        if(this.conversation != null){
            this.conversation.close();
            this.conversation =null;
        }

        if(localStream != null){
            localStream.close();
            localStream = null;
        }

        if(remoteStream != null){
            remoteStream.detach();
            remoteStream = null;
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setCallStateListener(CallStateListener callStateListener) {
        this.callStateListener = callStateListener;
    }

    public void setDataChangerListener(DataChangerListener dataChangerListener) {
        this.dataChangerListener = dataChangerListener;
    }

    public DataChangerListener getDataChangerListener() {
        return dataChangerListener;
    }

    public CallStateListener getCallStateListener() {
        return callStateListener;
    }

    public WilddogVideo getWilddogVideo() {
        return wilddogVideo;
    }

    public void setWilddogVideo(WilddogVideo wilddogVideo) {
        this.wilddogVideo = wilddogVideo;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public RemoteStream getRemoteStream() {
        return remoteStream;
    }

    public void setRemoteStream(RemoteStream remoteStream) {
        this.remoteStream = remoteStream;
    }

    public LocalStream getLocalStream() {
        return localStream;
    }

    public void setLocalStream(LocalStream localStream) {
        this.localStream = localStream;
    }

    public String getRemouteUid() {
        return remouteUid;
    }

    public void setRemouteUid(String remouteUid) {
        this.remouteUid = remouteUid;
    }
}
