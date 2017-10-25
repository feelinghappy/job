package com.hrg.robotproject;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService extends Service
{
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
    };
    private String LOG = "LoginService";
    private static final int REQUEST_CODE = 0; // 请求码
    private WilddogAuth auth;
    private boolean isInlogin =false;
    Oauth oauth;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public static final  int UPDATE_TEXT = 1;
    public static final  int UPDATE_CUSTOM_TOKEN = 2;
    public static final  int UPDATE_HIDDEN = 3;
    public static final  int UPDATE_UNBIND = 4;
    public static final  int UPDATE_OTA = 5;
    public static final  int UPDATE_SET_NAME = 6;
    public static final  int UPDATE_GET_NAME = 7;
    public static final  int UPDATE_SET_CONTROLABLE = 8;
    public static final  int UPDATE_GET_CONTROLABLE = 9;
    final OkHttpClient client = new OkHttpClient();
    String strhidden;
    String strfromserver;
    String Login_token;
    String strfromserver1;
    public static String customtoken;
    String strhiddenfromserver;
    String strunbindfromsever;
    String strOTAfromsever;
    String strgetnamefromsever;
    String strsetnamefromsever;
    String strgetcontrolfromsever;
    String strsetcontrolfromsver;
    public void onCreate() {
        super.onCreate();
        Log.e(LOG, "Oncreate");
        Toast.makeText(getApplicationContext(), LOG + "onCreate start!",
                Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), LOG + "onCreate end!",
                Toast.LENGTH_LONG).show();
        postRequest();


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
       return START_STICKY_COMPATIBILITY;
       //return super.onStartCommand(intent, flags, startId);
    }


    public void onDestroy()
    {
        Intent localIntent = new Intent();
        localIntent.setClass(this, LoginService.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }


    @Override
    public void onStart(Intent intent, int startId)
    {
// 再次动态注册广播
        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
        BootCompletedReceiver searchReceiver = new BootCompletedReceiver();
        registerReceiver(searchReceiver, localIntentFilter);
        super.onStart(intent, startId);
    }

    public void login() {
        if (isInlogin) {
            return;
        }
        isInlogin = true;
        //获取Sync & Auth 对象
        Log.e("strcustomtoken",customtoken);
        if(customtoken==null)
        {
            Toast.makeText(getApplicationContext(), "登录失败!", Toast.LENGTH_SHORT).show();
            isInlogin = false;
            return;
        }
        auth = WilddogAuth.getInstance();
        auth.signInWithCustomToken(customtoken).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //身份认证成功
                    String uid = auth.getCurrentUser().getUid();
                    //用户可以使用任意自定义节点来保存用户数据，但是不要使用 [wilddogVideo]节点存放私有数据
                    //以防和Video SDK 数据发生冲突
                    //本示例采用根节点下的[users] 节点作为用户列表存储节点
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(uid, true);
                    SyncReference userRef= WilddogSync.getInstance().getReference("users");
                    userRef.updateChildren(map);
                    userRef.child(uid).onDisconnect().removeValue();
                } else {
                    Log.d("result", "认证失败" + task.getException().toString());
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("getException()",task.getException().toString());
                    Toast.makeText(getApplicationContext(), "登录失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    try {
                        parseJSONWithJSONObject(strfromserver);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_CUSTOM_TOKEN:
                    try {
                        parseJSONWith1JSONObject(strfromserver1);
                        login();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    login();
                    break;
                case UPDATE_HIDDEN:
                    try {
                        parseJSONWith2JSONObject(strhiddenfromserver);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_UNBIND:
                    try {
                        parseJSONWith3JSONObject(strunbindfromsever);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }

    };

    public void oauthInit()
    {
        oauth = new Oauth();
        oauth.robot_sn = "HSRasd0170400001";
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        Log.e("timeStamp", timeStamp+"");
        oauth._timestamp = timeStamp + "";
        Log.e("oauth._timestamp",oauth._timestamp);
        int num = ((int)((Math.random()*9+1)*100000));
        oauth.random_str = num+"";
        String pre = "rmqyXgNgJRR5fMTlpkW5EGPNiGnk5IL0"+ oauth.random_str + oauth._timestamp;
        Log.e("pre",pre);
        oauth.secret_code = md5(pre);
    }
    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    /*
    * 将时间戳转换为时间
    */
    public String stampToDate(long timeMillis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }
    private void postRequest() {
        Log.e("postRequest","postRequest");

        oauthInit();
        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("robot_sn",oauth.Getrobot_sn());
        builder.add("random_str",oauth.Getrandom_str());
        builder.add("_timestamp",oauth.Gettimestamp());
        builder.add("secret_code",oauth.Getsecretcode());

        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody",requestBody.toString());


        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://sun.healthywo.com/robot/oauth/get_token")
                .post(requestBody)
                .build();
        //发送请求获取响应

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        //Log.e("WY","打印POST响应的数据：" + response.body().string());
                        strfromserver = response.body().string();
                        Toast.makeText(getApplicationContext(), strfromserver, Toast.LENGTH_SHORT).show();
                        Log.e("strfromserver",strfromserver);
                        Message msg = new Message();
                        msg.what = UPDATE_TEXT;
                        handler.sendMessage(msg);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    //{"code":"200","msg":"success","result":{"token":"00d00dfb95de7e5d077ad24754bb5b5d","expires_in":"86400"}}
    private void parseJSONWithJSONObject(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        String strtoken = jsonresult.getString("token");
        Log.e("strtoken",strtoken);
        String strexpires_in = jsonresult.getString("expires_in");
        Log.e("strexpires_in",strexpires_in);
        Login_token = strtoken;



    }

    private void parseJSONWith1JSONObject(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strmsg = jsonObject.getString("msg");
        Log.e("strmsg",strmsg);
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        customtoken = jsonresult.getString("custom_token");
        Log.e("strcustomtoken",customtoken);

    }

    private void parseJSONWith2JSONObject(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strmsg = jsonObject.getString("msg");
        Log.e("strmsg",strmsg);
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        strhidden = jsonresult.getString("custom_token");
        Log.e("customtoken",customtoken);

    }
    private void parseJSONWith3JSONObject(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strmsg = jsonObject.getString("msg");
        Log.e("strmsg",strmsg);
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        strhidden = jsonresult.getString("custom_token");
        Log.e("strcustomtoken",customtoken);

    }
    private void getRequest() {

        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("client_type","robot");
        builder.add("token",Login_token);
        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody",requestBody.toString());
        //设置编码
        //发送Post,并返回一个HttpResponse对象
        final Request request = new Request.Builder()
                .url("http://auth.healthywo.com/api/auth/robot")
                .post(requestBody)
                .build();
        //发送请求获取响应

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        strfromserver1 = response.body().string();
                        Log.e("strfromserver1",strfromserver1);
                        Message msg = new Message();
                        msg.what = UPDATE_CUSTOM_TOKEN;
                        handler.sendMessage(msg);

                        //parseJSONWithJSONObject(strfromserver);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}