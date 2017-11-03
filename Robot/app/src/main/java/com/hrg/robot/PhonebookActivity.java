package com.hrg.robot;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.wilddog.client.ChildEventListener;
import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class PhonebookActivity extends Activity {
    private Oauth oauth;
    private static final int REQUEST_CODE = 0; // 请求码
    private WilddogAuth auth;
    private boolean isInlogin =false;
    final OkHttpClient client = new OkHttpClient();
    String wilddogcustomtoken = null;
    public static final  int UPDATE_TOKEN = 1;
    public static final  int UPDATE_CUSTOM_TOKEN = 2;
    public static final  int UPDATE_SECRET = 3;
    public static final  int UPDATE_UNBIND = 4;
    public static final  int UPDATE_OTA = 5;
    public static final  int UPDATE_SET_NAME = 6;
    public static final  int UPDATE_GET_NAME = 7;
    public static final  int UPDATE_SET_CONTROLABLE = 8;
    public static final  int UPDATE_GET_CONTROLABLE = 9;
    String strSecret;
    String strfromserver;
    String Login_token;
    String strcustomtoken;
    String strunbindfromsever;
    String strOTAfromsever;
    String strgetnamefromsever;
    String strsetnamefromsever;
    String strgetcontrolfromsever;
    String strsetcontrolfromsver;
    private ValueEventListener mConnectedListener;
    private SyncReference mWilddogRef;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_phonebook);
        hideNavigationBar();
        hideVirtualKey();
        ImageView imgback = (ImageView)findViewById(R.id.video_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView txtback = (TextView)findViewById(R.id.callNavigateBack);
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
                Intent startIntent = new Intent(PhonebookActivity.this, VideoCallService.class);
                startService(startIntent);
            }
        });
        sendRequestWithOkHttp();




    }
    public void oauthInit()
    {
        oauth = new Oauth();
        oauth.robot_sn = "HSRasd0170400003";
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        Log.e("timeStamp", timeStamp+"");
        oauth._timestamp = timeStamp + "";
        Log.e("oauth._timestamp",oauth._timestamp);
        int num = ((int)((Math.random()*9+1)*100000));
        oauth.random_str = num+"";
        String pre = "2zXlePhpzRJJlGsErEmcWZK2O96SefLI"+ oauth.random_str + oauth._timestamp;
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
    private void GetSecretRequestWithOkHttp()
    {
        Log.e("RequestWithOkHttp","GetSecretRequestWithOkHttp");
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("robot_sn","HSRasd0170400003");
        RequestBody requestBody = builder.build();
        Log.e("requestBody",requestBody.toString());

        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://robot.healthywo.cn/api/secret")
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
                        strfromserver = null;
                        strfromserver = response.body().string();
                        Toast.makeText(PhonebookActivity.this, strfromserver, Toast.LENGTH_SHORT).show();
                        Log.e("strfromserver", strfromserver);
                        Message msg = new Message();
                        msg.what = UPDATE_SECRET;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();

    }
    private void sendRequestWithOkHttp() {
        Log.e("sendRequestWithOkHttp","sendRequestWithOkHttp");

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
                        strfromserver = null;
                        strfromserver = response.body().string();
                        Message msg = new Message();
                        msg.what = UPDATE_TOKEN;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }
    private void GetCustomToken(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strmsg = jsonObject.getString("msg");
        Log.e("strmsg",strmsg);
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        strcustomtoken = jsonresult.getString("custom_token");
        Log.e("strcustomtoken",strcustomtoken);

    }
    //{"code":"200","msg":"success","result":{"token":"00d00dfb95de7e5d077ad24754bb5b5d","expires_in":"86400"}}
    private void GetLoginToken(String strData) throws JSONException {
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
    private void getCustomTokenRequest() {

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
                        strfromserver = null;
                        strfromserver = response.body().string();
                        Message msg = new Message();
                        msg.what = UPDATE_CUSTOM_TOKEN;
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
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TOKEN:
                    try {
                        GetLoginToken(strfromserver);
                        getCustomTokenRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_CUSTOM_TOKEN:
                    try {
                        GetCustomToken(strfromserver);
                        login();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_SECRET:
 /*                    try {
                      GetSecret(strfromserver);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    break;
                case UPDATE_SET_NAME:
 /*                   try {
                        GetSetname(strfromserver);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    break;
                case UPDATE_SET_CONTROLABLE:
/*                    try {
                        GetSetcontrolable(strfromserver);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                    break;
/*                case UPDATE_UNBIND:
                    try {
                        parseJSONWith3JSONObject(strunbindfromsever);
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    break;*/

                default:
                    break;
            }
        }

    };

    public void login() {
        if (isInlogin) {
            return;
        }
        isInlogin = true;
        //获取Sync & Auth 对象
        if(strcustomtoken==null)
        {
            Toast.makeText(getApplicationContext(), "登录失败!", Toast.LENGTH_SHORT).show();
            isInlogin = false;
            return;
        }
        auth = WilddogAuth.getInstance();
        auth.signInWithCustomToken(strcustomtoken).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //身份认证成功
                    String uid = auth.getCurrentUser().getUid();
                    SharedPrefUtils.saveConfigInfo(PhonebookActivity.this, Constants.WILDDOG_UID, uid);
                    Log.e("uid",uid);
                    mWilddogRef = WilddogSync.getInstance().getReference().child("roots");
                    writeRobotInfo(uid);


                } else {
                    Log.d("result", "认证失败" + task.getException().toString());
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("getException()",task.getException().toString());
                    Toast.makeText(getApplicationContext(), "登录失败!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeRobotInfo(String uid) {

        Robot robot = new Robot();
        final AirBox airBox = new AirBox();
        airBox.setCo2("10");
        airBox.setCh2o("5");
        airBox.setTemperature("10");
        airBox.setHumidity("20");
        airBox.setPm25("7");
        airBox.setGas("true");
        robot.setName("小火宝");
        robot.setType("desktop");
        robot.setElectricity("0.5");
        robot.setCurrentUser("12366666");
        robot.setAirBox(airBox);
        SyncReference ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("name");
        //写机器人名称
        ref.setValue("小火宝", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("error", error.toString());
                } else {
                    Log.e("success", "setValue success");
                }
            }
        });
        //写空气净化器
        ref = WilddogSync.getInstance().getReference("robots").child(uid).child("airBox");
        ref.setValue(airBox, new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("error", error.toString());
                } else {
                    Log.e("success", "setValue success");
                }
            }
        });
//写在线不在线信息
        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("status");
        ref.setValue("online", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("status error", error.toString());
                } else {
                    Log.e("status success", "setValue success");
                }
            }
        });
        //type
        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("type");
        ref.setValue("desktop", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("type error", error.toString());
                } else {
                    Log.e("type success", "setValue success");
                }
            }
        });
        //electricity
        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("electricity");
        ref.setValue("0.2", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("ele error", error.toString());
                } else {
                    Log.e("ele success", "setValue success");
                }
            }
        });
        //monitor
        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("monitor");
        ref.setValue("true", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("mon error", error.toString());
                } else {
                    Log.e("mon success", "setValue success");
                }
            }
        });
        //fanState
        ref = WilddogSync.getInstance().getReference("robots");
        ref = ref.child(uid).child("fanState");
        ref.setValue("low", new SyncReference.CompletionListener() {
            @Override
            public void onComplete(SyncError error, SyncReference ref) {
                if (error != null) {
                    Log.e("fanState error", error.toString());
                } else {
                    Log.e("fanState success", "setValue success");
                }
            }
        });


///////////////////////////////////////////////////////////////////////////////////
        final SyncReference refairbox = WilddogSync.getInstance().getReference("robots").child(uid).child("airBox");
        final AirBox airBoxrev = new AirBox();
        ChildEventListener listener = refairbox.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot snapshot, String ref) {
                if (snapshot.getKey().equals("ch2o")) {
                    airBoxrev.setCh2o((String) snapshot.getValue());
                    Log.e("airBoxrev ch2o", airBoxrev.getCh2o());
                } else if (snapshot.getKey().equals("humidity")) {
                    airBoxrev.setHumidity((String) snapshot.getValue());
                    Log.e("airBoxrev humidity", airBoxrev.getHumidity());
                } else if (snapshot.getKey().equals("pm25")) {
                    airBoxrev.setPm25((String) snapshot.getValue());
                    Log.e("airBoxrev pm25", airBoxrev.getPm25());
                } else if (snapshot.getKey().equals("co2")) {
                    airBoxrev.setCo2((String) snapshot.getValue());
                    Log.e("airBoxrev co2", airBoxrev.getCo2());
                } else if (snapshot.getKey().equals("temperature")) {
                    airBoxrev.setTemperature((String) snapshot.getValue());
                    Log.e("airBoxrev temperature", airBoxrev.getTemperature());
                }


            }

            public void onChildChanged(DataSnapshot snapshot, String ref) {

                if (snapshot.getKey().equals("ch2o")) {
                    airBoxrev.setCh2o((String) snapshot.getValue());
                    Log.e("Changed ch2o", airBoxrev.getCh2o());
                } else if (snapshot.getKey().equals("humidity")) {
                    airBoxrev.setHumidity((String) snapshot.getValue());
                    Log.e("Changed humidity", airBoxrev.getHumidity());
                } else if (snapshot.getKey().equals("pm25")) {
                    airBoxrev.setPm25((String) snapshot.getValue());
                    Log.e("Changed pm25", airBoxrev.getPm25());
                } else if (snapshot.getKey().equals("co2")) {
                    airBoxrev.setCo2((String) snapshot.getValue());
                    Log.e("Changed co2", airBoxrev.getCo2());
                } else if (snapshot.getKey().equals("temperature")) {
                    airBoxrev.setTemperature((String) snapshot.getValue());
                    Log.e("Changed temperature", airBoxrev.getTemperature());
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(SyncError syncError) {

            }
        });
        mConnectedListener = mWilddogRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(PhonebookActivity.this, "Connected to Wilddog", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PhonebookActivity.this, "Disconnected from Wilddog", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(SyncError syncError) {

            }

        });

    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }
    private  void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        int uiOpions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOpions);

    }
    /**
     * 隐藏Android底部的虚拟按键
     */
    private void hideVirtualKey(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }
}