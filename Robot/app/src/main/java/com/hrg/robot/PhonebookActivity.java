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
import com.zhikang.zklibrary.api.ZKRequest;
import com.zhikang.zklibrary.bean.AirData;
import com.zhikang.zklibrary.bean.MoveDirection;
import com.zhikang.zklibrary.bean.ResultData;
import com.zhikang.zklibrary.bean.RobotData;
import com.zhikang.zklibrary.callBack.ZKCallback;
import com.zhikang.zklibrary.config.ZKConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
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
import static com.hrg.robot.MediaApplication.mContext;

public class PhonebookActivity extends Activity implements ZKCallback {
    private ImageView img;
    private Oauth oauth;
    private static final int REQUEST_CODE = 0; // 请求码
    private WilddogAuth auth;
    private boolean isInlogin =false;
    final OkHttpClient client = new OkHttpClient();
    String wilddogcustomtoken = null;
    private boolean isOnline = false;
    public static final  int UPDATE_TOKEN = 1;
    public static final  int UPDATE_CUSTOM_TOKEN = 2;
    public static final  int UPDATE_SECRET = 3;
    public static final  int UPDATE_UNBIND = 4;
    public static final  int UPDATE_OTA = 5;
    public static final  int UPDATE_SET_NAME = 6;
    public static final  int UPDATE_GET_NAME = 7;
    public static final  int UPDATE_SET_CONTROLABLE = 8;
    public static final  int UPDATE_GET_CONTROLABLE = 9;

    public static final  int UPDATE_ROBOT_MSG = 20;
    public static final  int UPDATE_AIRBOX_MSG = 21;
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
    private com.zhikang.zklibrary.api.ZKRequest zkRequest;
    private ValueEventListener mConnectedListener;
    private SyncReference mWilddogRef;
    private RobotData robotDataloc;
    private AirData airData;
    private String uid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_phonebook);
        zkRequest = new ZKRequest(this, this);
        hideNavigationBar();
        hideVirtualKey();


        ImageView imgback = (ImageView)findViewById(R.id.video_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        GetRobotMsg();

/*        img = (ImageView)findViewById(R.id.imgyeye);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  //              callVideo("111111");
            }
        });*/
        Button call_liutao = (Button)findViewById(R.id.call_liutao);
        call_liutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(PhonebookActivity.this, CallingActivity.class);
                    intent.putExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, VideoCallUA.CALL_OUTGOING);
                    intent.putExtra("monitor", false);
                    intent.putExtra("callUid", "c33bebf2-b4c1-45ca-b256-7f095ec42eeb");
                    startActivity(intent);
                    Toast.makeText(PhonebookActivity.this, "发起视频", Toast.LENGTH_SHORT).show();
                }
                catch(Exception ex)
                {
                    Toast.makeText(PhonebookActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    /**
     *
     * @param command
     * @param value
     */
    private void sendCommand(int command, int value){
        if (zkRequest != null) {
            zkRequest.sendRequest(command, value);
        }
    }

    public void GetRobotMsg()
    {
        sendCommand(com.zhikang.zklibrary.config.ZKConfig.GET_ROBOT_MSG, com.zhikang.zklibrary.config.ZKConfig.GET_ROBOT_MSG);

    }
    public void GetAirMsg()
    {
        sendCommand(ZKConfig.GET_AIR_MSG, ZKConfig.GET_AIR_MSG);
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
                case UPDATE_ROBOT_MSG:
                    String DevSN = robotDataloc.getDevSN();
                    Toast.makeText(getApplicationContext(), DevSN, Toast.LENGTH_SHORT).show();
                    sendRequestWithOkHttp();
                    break;
                case UPDATE_AIRBOX_MSG:
                    writeAirbox(uid);
                    break;

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
            Toast.makeText(getApplicationContext(), "strcustomtoken登录失败!", Toast.LENGTH_SHORT).show();
            isInlogin = false;
            return;
        }
        auth = WilddogAuth.getInstance();
        auth.signInWithCustomToken(strcustomtoken).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //身份认证成功
                    uid = auth.getCurrentUser().getUid();
                    SharedPrefUtils.saveConfigInfo(PhonebookActivity.this, Constants.WILDDOG_UID, uid);
                    Log.e("uid",uid);
                    mWilddogRef = WilddogSync.getInstance().getReference().child("roots");
                    //writeRobotInfo(uid);
                    isOnline = true;
                    GetAirMsg();
                    writeRobot(uid);
                    //写在线不在线信息
                    SyncReference ref = WilddogSync.getInstance().getReference("robots");
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
                    Intent intent = new Intent(PhonebookActivity.this, VideoCallService.class);
                    startService(intent);


                } else {
                    Log.d("result", "认证失败" + task.getException().toString());
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    Log.e("getException()",task.getException().toString());
                    Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void writeRobot(String uid)
    {
        if((robotDataloc==null)||(uid==null))
        {
            return;
        }
        else
        {
            SyncReference ref = WilddogSync.getInstance().getReference("robots");
            ref = ref.child(uid).child("name");
            String name = robotDataloc.getRobotName();
            //写机器人名称
            ref.setValue(name, new SyncReference.CompletionListener() {
                @Override
                public void onComplete(SyncError error, SyncReference ref) {
                    if (error != null) {
                        Log.e("error", error.toString());
                    } else {
                        Log.e("success", "setValue success");
                    }
                }
            });
            String  status = (robotDataloc.getStatus()> 0)?"offline":"online";
            //写在线不在线信息
            ref = WilddogSync.getInstance().getReference("robots");
            ref = ref.child(uid).child("status");
            ref.setValue(status, new SyncReference.CompletionListener() {
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

            ref.setValue("ground", new SyncReference.CompletionListener() {
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
            double electricity = robotDataloc.getElectricity();
            ref.setValue(electricity, new SyncReference.CompletionListener() {
                @Override
                public void onComplete(SyncError error, SyncReference ref) {
                    if (error != null) {
                        Log.e("ele error", error.toString());
                    } else {
                        Log.e("ele success", "setValue success");
                    }
                }
            });

            ref = WilddogSync.getInstance().getReference("robots");
            ref = ref.child(uid).child("currentUser");
            ref.setValue("undefine", new SyncReference.CompletionListener() {
                @Override
                public void onComplete(SyncError error, SyncReference ref) {
                    if (error != null) {
                        Log.e("fanState error", error.toString());
                    } else {
                        Log.e("fanState success", "setValue success");
                    }
                }
            });
            ref = WilddogSync.getInstance().getReference("robots");
            ref = ref.child(uid).child("monitor");
            ref.setValue("undefine", new SyncReference.CompletionListener() {
                @Override
                public void onComplete(SyncError error, SyncReference ref) {
                    if (error != null) {
                        Log.e("fanState error", error.toString());
                    } else {
                        Log.e("fanState success", "setValue success");
                    }
                }
            });



        }
    }
    public void writeAirbox(String uid)
    {
        if(uid==null) {
            return;
        }
        else
        {
            //写空气净化器
            Log.e("writeAirbox","writeAirbox");
            SyncReference ref = WilddogSync.getInstance().getReference("robots").child(uid).child("airBox");
            String fanStatus = (airData.getFanStatus()>0)?"on":"off";
            int anionStatus = airData.getAnionStatus();
            String airDataElectricity = airData.getElectricity();
            boolean isCharging = airData.isCharging();
            String co = airData.getCo();
            String co_decimal = airData.getCo_decimal();
            String ch2o = airData.getCh2o();
            String ch2o_decimal = airData.getCh2o_decimal();
            String pm = airData.getPm();
            String humidity = airData.getHumidity();
            String humidity_decimal = airData.getHumidity_decimal();
            String temperature = airData.getTemperature();
            String temperature_decimal = airData.getTemperature_decimal();
            final AirBox airBox = new AirBox();
            airBox.setPm25(pm);
            String wendu = temperature +"."+temperature_decimal;
            airBox.setTemperature(wendu);
            String strCh2o = ch2o +"."+ ch2o_decimal;
            airBox.setCh2o(strCh2o);
            airBox.setGas(airData.getGas()+"");
            String humidityall = humidity + "."+humidity_decimal;
            airBox.setHumidity(humidityall);
            String coall = co+"."+ co_decimal;
            airBox.setCo2(coall);
                /*  public int fanStatus;//风扇状态 1:开  0:关
                public int anionStatus;//负离子状态
                public String electricity;
                public boolean isCharging;
                public String co;
                public String co_decimal;
                public String ch2o;//甲醛，整数部分
                public String ch2o_decimal;//甲醛，小数部分
                public String pm;
                public String humidity;
                public String humidity_decimal;
                public String temperature;
                public String temperature_decimal;
                public int gas;*/
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
            ref = WilddogSync.getInstance().getReference("robots").child(uid).child("fanState");
            ref.setValue(fanStatus, new SyncReference.CompletionListener() {
                @Override
                public void onComplete(SyncError error, SyncReference ref) {
                    if (error != null) {
                        Log.e("error", error.toString());
                    } else {
                        Log.e("success", "setValue success");
                    }
                }
            });
        }

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
        zkRequest.unregister();
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

    @Override
    public void resultMsg(ResultData resultData) {
     //   resultMsg: resultData:ResultData{type=1, obj=RobotData{status=1111, robotName='', devSN=HSRasd0170400003, electricity=0}}
     //   resultMsg: resultData:ResultData{type=2,
        // obj=AirData{fanStatus=1, anionStatus=1, electricity='0',
        // isCharging=false, co='0', co_decimal='0', ch2o='0', ch2o_decimal='43', pm='23',
        // humidity='49', humidity_decimal='9', temperature='25', temperature_decimal='8', gas=1}}
       int type =  resultData.getType();
        Message msg = new Message();
        switch (type)
        {
 /*           public int status;//在线:0, 离线:1
            public String robotName;//机器人名字
            public String devSN;//机器人SN
            public int electricity;//机器人电量*/
            case 1:
                String str = resultData.toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();

                robotDataloc =(RobotData)resultData. getObj();
                if(robotDataloc == null)
                {
                    Log.e("UPDATE_ROBOT_MSG","robotDataloc == null");
                    Toast.makeText(getApplicationContext(), "robotDataloc == null", Toast.LENGTH_SHORT).show();

                }
       /*         int status = robotData.getStatus();
                String robotName = robotData.getRobotName();
                Log.e("robotName",robotName);
                String devSN = robotData.getDevSN();
                Log.e("devSN",devSN);
                int electricity = robotData.getElectricity();*/
                msg = new Message();
                msg.what = UPDATE_ROBOT_MSG;
                handler.sendMessage(msg);
                Log.e("UPDATE_ROBOT_MSG","robotDataloc");
                Toast.makeText(getApplicationContext(), "robotDataloc", Toast.LENGTH_SHORT).show();
                break;
            /*  public int fanStatus;//风扇状态 1:开  0:关
                public int anionStatus;//负离子状态
                public String electricity;
                public boolean isCharging;
                public String co;
                public String co_decimal;
                public String ch2o;//甲醛，整数部分
                public String ch2o_decimal;//甲醛，小数部分
                public String pm;
                public String humidity;
                public String humidity_decimal;
                public String temperature;
                public String temperature_decimal;
                public int gas;*/
            case 2:
                airData = (AirData)resultData.getObj();
                if(airData==null)
                {
                    Log.e("UPDATE_AIRBOX_MSG","airData == null");
                    Toast.makeText(getApplicationContext(), "airData == null", Toast.LENGTH_SHORT).show();
                }
                int gas = airData.getGas();
                Log.e("gas",gas+"");
                msg.what = UPDATE_AIRBOX_MSG;
                handler.sendMessage(msg);
                Log.e("UPDATE_AIRBOX_MSG","airData");
                Toast.makeText(getApplicationContext(), "airData", Toast.LENGTH_SHORT).show();
                break;







        }


    }

    @Override
    public void error(String msg) {

    }
}