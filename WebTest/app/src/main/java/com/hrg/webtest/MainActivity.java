package com.hrg.webtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.net.Proxy.Type.HTTP;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button btnsend,btnrev;
    Oauth oauth;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    final OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnrev = (Button)findViewById(R.id.rev);
        btnsend = (Button)findViewById(R.id.send);

        btnrev.setOnClickListener(this);
        btnsend.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                getRequest();
                break;

            case R.id.rev:
                postRequest();
                break;

        }
    }
    private void getRequest() {

        final Request request=new Request.Builder()
                .get()
                .tag(this)
                .url("http://sun.healthywo.com/robot/family/get_monitor_history")
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Log.e("WY","打印GET响应的数据：" + response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void postRequest() {

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


        Log.e("oauthToJson() = ",oauthToJson());
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
                        Log.e("WY","打印POST响应的数据：" + response.body().string());
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public void oauthInit()
    {
        oauth = new Oauth();
        oauth.robot_sn = "HSRasd0170400001";
        //获取当前时间戳
//获取当前时间戳
        long timeStamp = System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        Log.e("xxxxx", timeStamp+"");
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
    public String oauthToJson() {
         oauthInit();
         String jsonresult = "";//定义返回字符串  
         JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串  
         try {
                  JSONArray jsonarray = new JSONArray();//json数组，里面包含的内容为pet的所有对象  
                  JSONObject jsonObj = new JSONObject();//pet对象，json形式  
                  jsonObj.put("robot_sn",oauth.Getrobot_sn());//向pet对象里面添加值  
                  jsonObj.put("random_str", oauth.Getrandom_str());
                  jsonObj.put("_timestamp",oauth.Gettimestamp());
                  jsonObj.put("secret_code",oauth.Getsecretcode());
                  // 把每个数据当作一对象添加到数组里  
                  jsonarray.put(jsonObj);//向json数组里面添加pet对象  
                  object.put("oauth", jsonarray);//向总对象里面添加包含pet的数组  
                  jsonresult = object.toString();//生成返回字符串  
                  }
                  catch (JSONException e)
                  {
                  // TODO Auto-generated catch block  
                    e.printStackTrace();
                  }
                  Log.e("生成的json串为:",jsonresult);
                  return jsonresult;
     }




}
