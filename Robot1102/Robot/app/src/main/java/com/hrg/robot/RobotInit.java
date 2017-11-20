package com.hrg.robot;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RobotInit
{
    public static final  int UPDATE_TOKEN = 1;
    public static final  int UPDATE_CUSTOM_TOKEN = 2;
    public static final  int UPDATE_SECRET = 3;
    public static final  int UPDATE_UNBIND = 4;
    public static final  int UPDATE_OTA = 5;
    public static final  int UPDATE_SET_NAME = 6;
    public static final  int UPDATE_GET_NAME = 7;
    public static final  int UPDATE_SET_CONTROLABLE = 8;
    public static final  int UPDATE_GET_CONTROLABLE = 9;
    private Oauth oauth;
    private String strSecret;
    private String strfromserver;
    private String Login_token;
    public String strcustomtoken;
    private String strunbindfromsever;
    private String strOTAfromsever;
    private String strgetnamefromsever;
    private String strsetnamefromsever;
    private String strgetcontrolfromsever;
    private String strsetcontrolfromsver;
    public static Context context;
    final OkHttpClient client = new OkHttpClient();

        //修改机器人监控状态
        public void set_controlableWithOkHttp()
        {
            Log.e("set_nameWithOkHttp","set_nameWithOkHttp");
            String  hidden = "2zXlePhpzRJJlGsErEmcWZK2O96SefLI";
            String strbind_code =md5(md5("HSRasd0170400003")+hidden);
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("bind_code",strbind_code);
            builder.add("controlable","0");
            RequestBody requestBody = builder.build();
            Log.e("requestBody",requestBody.toString());
            //设置编码
            //发送Post,并返回一个HttpResponse对象


            final Request request = new Request.Builder()
                    .url("http://robot.healthywo.cn/api/robot/set_controlable")
                    .post(requestBody)
                    .build();
                    //发送请求获取响应
                    new Thread(new Runnable()
                    {
                            @Override
                            public void run() {
                                Response response = null;
                                try {
                                response = client.newCall(request).execute();
                                if (response.isSuccessful()) {
                                strfromserver = null;
                                strfromserver = response.body().string();
                                Log.e("strfromserver", strfromserver);
                                Message msg = new Message();
                                msg.what = UPDATE_SET_CONTROLABLE;
                                handler.sendMessage(msg);
                                }
                                } catch (IOException e1) {
                                e1.printStackTrace();
                                }
                                }
                                }).start();
                    }

        public void get_controlableWithOkHttp()
        {

        }
        public void set_nameWithOkHttp()
        {
                Log.e("set_nameWithOkHttp","set_nameWithOkHttp");
                String  hidden = "2zXlePhpzRJJlGsErEmcWZK2O96SefLI";
                String strbind_code =md5(md5("HSRasd0170400003")+hidden);
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("bind_code",strbind_code);
                builder.add("name","fanxiaoli");
                RequestBody requestBody = builder.build();
                Log.e("requestBody",requestBody.toString());
        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://robot.healthywo.cn/api/robot/set_name")
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
                Log.e("strfromserver", strfromserver);
                Message msg = new Message();
                msg.what = UPDATE_SET_NAME;
                handler.sendMessage(msg);
                }
                } catch (IOException e1) {
                e1.printStackTrace();
                }
                }
                }).start();
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
        public String stampToDate(long timeMillis)
        {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(timeMillis);
            return simpleDateFormat.format(date);
        }

    public void GetSecretRequestWithOkHttp()
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
                    try
                    {
                        response = client.newCall(request).execute();
                        if (response.isSuccessful())
                        {
                            strfromserver = null;
                            strfromserver = response.body().string();
                            Log.e("strfromserver", strfromserver);
                            Message msg = new Message();
                            msg.what = UPDATE_SECRET;
                            handler.sendMessage(msg);
                        }
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    }
                    }).start();

        }

        public void sendRequestWithOkHttp()
        {
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
        private Handler handler = new Handler() {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                case UPDATE_TOKEN:
                try {
                        GetLoginToken(strfromserver);
                        getCustomTokenRequest();
                     }
                     catch (JSONException e)
                     {
                        e.printStackTrace();
                     }
                break;
                case UPDATE_CUSTOM_TOKEN:
                try
                {
                    GetCustomToken(strfromserver);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                break;
                case UPDATE_SECRET:
                try
                {
                     GetSecret(strfromserver);
                 }
                 catch (JSONException e)
                {
                    e.printStackTrace();
                }
                break;
                case UPDATE_SET_NAME:
                try
                {
                    GetSetname(strfromserver);
                 }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                break;
                case UPDATE_SET_CONTROLABLE:
                try
                {
                    GetSetcontrolable(strfromserver);
                }
                    catch (JSONException e)
                {
                    e.printStackTrace();
                }
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
//    {"code":"200","msg":"Success","result":""}
        private void GetSetcontrolable(String strData) throws JSONException
        {
                Log.e("GetSetcontrolable",strData);
                JSONObject jsonObject = new JSONObject(strData);
                int  code =Integer.parseInt(jsonObject.getString("code"));
                Log.e("code",code+"");
                if(code==200) {

                }

         }

//    {"code":"200","msg":"Success","result":""}
        private void GetSetname(String strData) throws JSONException
        {
                Log.e("GetSetname",strData);
                JSONObject jsonObject = new JSONObject(strData);
                int  code =Integer.parseInt(jsonObject.getString("code"));
                Log.e("code",code+"");
                if(code==200) {

                }

        }

        //"code":"100003","msg":"Robot SN is bound","result":""
        private void GetSecret(String strData) throws JSONException
        {
                JSONObject jsonObject = new JSONObject(strData);
                int  code =Integer.parseInt(jsonObject.getString("code"));
                Log.e("code",code+"");
                if(code==200) {
                String strmsg = jsonObject.getString("msg");
                Log.e("strmsg", strmsg);
                String strresult = jsonObject.getString("result");
                Log.e("strresult", strresult);
                JSONObject jsonresult = new JSONObject(strresult);
                String strID = jsonresult.getString("robot_sn");
                strSecret = jsonresult.getString("secret_code");
                Log.e("secret_code", strSecret);
                }

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

       private void getCustomTokenRequest()
       {

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
        }