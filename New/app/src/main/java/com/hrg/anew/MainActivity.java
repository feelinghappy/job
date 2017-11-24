package com.hrg.anew;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hrg.anew.bean.Affix;
import com.hrg.anew.bean.Blood_data;
import com.hrg.anew.bean.Family;
import com.hrg.anew.bean.Getmonitor;
import com.hrg.anew.bean.Health;
import com.hrg.anew.bean.Heart_data;
import com.hrg.anew.bean.Member;
import com.hrg.anew.bean.Oauth;
import com.hrg.anew.bean.Sleep;
import com.hrg.anew.bean.Sport_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends Activity {
    public static final int UPDATE_TOKEN = 1;
    public static final int UPDATE_FAMILY_INFO = 2;
    public static final int UPDATE_MEMBER_INFO = 3;
    public static final int UPDATE_GPS_INFO = 4;
    private Oauth oauth;
    final OkHttpClient client = new OkHttpClient();
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    // 包裹滑动图片LinearLayout
    private ViewGroup main;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    private int currIndex = 0;// 当前页卡编号
    private String strfromserver;
    private String Login_token;
    private Family family;
    private Member member;
    private Health health;
    private Affix affix;
    private Getmonitor getmonitor;
    private List<Member> member_list = new ArrayList();
    private List<String> family_member_id_list = new ArrayList();
    private Sleep sleep = new Sleep();
    private Sport_data sport_data = new Sport_data();
    private Blood_data blood_data = new Blood_data();
    private Heart_data heart_data = new Heart_data();
    private TextView item01_blood_value;
    private TextView item01_blood_create_time;
    private TextView item01_sleep_time;
    private TextView item01_walk_num;
    private TextView item01_walk_state;
    private RadioGroup item01_heart_rate_option;
    private TextView item01_heart_rate_value;
    private TextView item01_txtName;
    private TextView item01_txtHeight;
    private TextView item01_txtAge;
    private TextView item01_txtSex;
    private TextView item01_weight;
    private TextView item01_sleep_grade;
    private String lngData;
    private String latData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.item01, null));
        //pageViews.add(inflater.inflate(R.layout.item02, null));
        // pageViews.add(inflater.inflate(R.layout.item03, null));
        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup) inflater.inflate(R.layout.main, null);
        viewPager = (ViewPager) findViewById(R.id.guidePages);
        group = (ViewGroup) findViewById(R.id.viewGroup);
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(12, 12));
            imageViews[i] = imageView;
            if (i == 0) {
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(12, 12);
            layoutParams.leftMargin = 30;
            layoutParams.bottomMargin = 14;
            group.addView(imageViews[i], layoutParams);
        }

        hideVirtualKey();
        hideNavigationBar();
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        ImageView ImgBack = (ImageView) findViewById(R.id.back);

        ImgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        View pager1 = getLayoutInflater().inflate(R.layout.item01, null);
        ImageView ImgReport = (ImageView) pager1.findViewById(R.id.imgbtnreport);
        ImgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);
                finish();

            }
        });
        ImageView ImgLocation = (ImageView) pager1.findViewById(R.id.imgbtnposition);
        ImgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
                finish();


            }
        });
        sendRequestWithOkHttp();
        health = new Health(0,0,0.0,0.0);
        member = new Member("","",0,"","",health);
        affix = new Affix("",0);
        family = new Family();
        member_list.clear();
        family_member_id_list.clear();
        getmonitor = new Getmonitor();




    }
    private void UpdatePageData(int pageindex)
    {
        item01_txtName = (TextView)viewPager.findViewById(R.id.item01_txtName);
        item01_txtHeight = (TextView)viewPager.findViewById(R.id.item01_txtHeight);
        item01_txtAge = (TextView)viewPager.findViewById(R.id.item01_txtAge);
        item01_txtSex = (TextView)viewPager.findViewById(R.id.item01_txtSex);
        item01_weight = (TextView)viewPager.findViewById(R.id.item01_weight);
        item01_blood_value = (TextView)viewPager.findViewById(R.id.item01_blood_value);
        item01_blood_create_time = (TextView)viewPager.findViewById(R.id.item01_blood_create_time);
        item01_sleep_time = (TextView)viewPager.findViewById(R.id.item01_sleep_time);
        item01_sleep_grade = (TextView)viewPager.findViewById(R.id.item01_sleep_grade);
        item01_walk_num =  (TextView)viewPager.findViewById(R.id.item01_walk_num);
        item01_walk_state = (TextView)viewPager.findViewById(R.id.item01_walk_state);
        item01_heart_rate_option =(RadioGroup)viewPager.findViewById(R.id.item01_heart_rate_option);
        item01_heart_rate_value = (TextView)viewPager.findViewById(R.id.item01_heart_rate_value);
        String str;
        Member temp = member_list.get(pageindex);
        if(getmonitor==null||family==null||blood_data==null||temp==null)
        {
            return;
        }
        str = "数据获取于"+ blood_data.create_time;
        item01_txtName.setText(temp.member_name);
        String tempstr = temp.heath.member_height+"cm";
        item01_txtHeight.setText(tempstr);
        tempstr = temp.heath.member_age + getString(R.string.age_year);
        item01_txtAge.setText(tempstr);
        String sex;
        if(temp.heath.member_sex==1)
        {
            sex = "男";
        }
        else
        {
            sex = "女";
        }
        item01_txtSex.setText(sex);
        item01_weight.setText(temp.heath.GetMember_weight()+"kg");
        item01_blood_create_time.setText(str);
        int systolic = blood_data.systolic;
        int diastolic = blood_data.diastolic;
        str = systolic+"/"+ diastolic;
        item01_blood_value.setText(str);
        str = sleep.getSleep_time()+"";
        item01_sleep_time.setText(str);
        str = sport_data.step_num+"";
        item01_walk_num.setText(str);
        str = "消耗" + sport_data.calory + "卡路里";
        item01_walk_state.setText(str);
        //通过findViewById获得RadioGroup对象  
        String sleep_level_str;
        if (getmonitor.sleep.sleep_grade ==1)
        {
            sleep_level_str = "优秀";
        }
        else  if (getmonitor.sleep.sleep_grade ==2)
        {
            sleep_level_str = "良好";
        }
        else  if (getmonitor.sleep.sleep_grade ==3)
        {
            sleep_level_str = "一般";
        }
        else  if (getmonitor.sleep.sleep_grade ==4)
        {
            sleep_level_str = "不好";
        }
        else
        {
            sleep_level_str = "极不好";
        }
        item01_sleep_grade.setText(sleep_level_str);
        item01_heart_rate_value.setText(heart_data.getAvg_heart()+"");

        //添加事件监听器  
        item01_heart_rate_option.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                   if (checkedId == R.id.item_heart_rate_btn_0) {
                       item01_heart_rate_value.setText(heart_data.getAvg_heart()+"");

                   } else if (checkedId == R.id.item_heart_rate_btn_1) {
                       item01_heart_rate_value.setText(heart_data.max_heart+"");

                   } else if (checkedId == R.id.item_heart_rate_btn_2) {
                       item01_heart_rate_value.setText(heart_data.min_heart+"");
                   }
               }

          }
        );


    }

    private void init() {
        ImageView ImgBack = (ImageView) findViewById(R.id.back);
        ImgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);

            }
        });
        ImageView ImgCall = (ImageView) findViewById(R.id.imgbtncall);
        ImageView ImgReport = (ImageView) findViewById(R.id.imgbtnreport);
        ImgReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(intent);

            }
        });
        ImageView ImgPosition = (ImageView) findViewById(R.id.imgbtnposition);
        ImgReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);

            }
        });


    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOpions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOpions);

    }

    /**
     * 隐藏Android底部的虚拟按键
     */
    private void hideVirtualKey() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }

    // 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            switch (arg1) {
                case 0:
                    ImageView imgcall0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
                    imgcall0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            ComponentName componetName = new ComponentName(
                                    //这个是另外一个应用程序的包名  
                                     "com.hrg.robot",
                                    //这个是要启动的Activity  
                                    "com.hrg.robot.PhonebookActivity");
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(componetName);
                            startActivity(intent);
                        }
                    });
                    ImageView imgposition0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                    imgposition0.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            GetGpsInfo(family_member_id_list.get(0));

                        }
                    });
                    ImageView imghealth0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                    imghealth0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Intent intent  = new Intent(MainActivity.this,HealthActivity.class);
                            // startActivity(intent);
                            Toast.makeText(MainActivity.this, "该功能暂未开放，尽情期待。", Toast.LENGTH_LONG).show();
                        }
                    });
                    ImageView imgreport0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                    imgreport0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                            intent.putExtra("token",Login_token);
                            intent.putExtra("family_member_id",family_member_id_list.get(0));
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;
                case 1:
                    ImageView imgcall1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
                    imgcall1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            ComponentName componetName = new ComponentName(
                                    //这个是另外一个应用程序的包名  
                                    "com.hrg.robot",
                                    //这个是要启动的Activity  
                                    "com.hrg.robot.PhonebookActivity");
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(componetName);
                            startActivity(intent);
                        }
                    });
                    ImageView imgposition1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                    imgposition1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imghealth1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                    imghealth1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "该功能暂未开放，尽情期待。", Toast.LENGTH_LONG).show();
                        }
                    });
                    ImageView imgreport1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                    imgreport1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                            intent.putExtra("token",Login_token);
                            intent.putExtra("family_member_id",family_member_id_list.get(0));
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;
                case 2:
                    ImageView imgcall2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
                    imgcall2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            ComponentName componetName = new ComponentName(
                                    //这个是另外一个应用程序的包名  
                                    "com.hrg.robot",
                                    //这个是要启动的Activity  
                                    "com.hrg.robot.PhonebookActivity");
                            Intent intent = new Intent();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setComponent(componetName);
                            startActivity(intent);
                        }
                    });
                    ImageView imgposition2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                    imgposition2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View m) {
                            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imghealth2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                    imghealth2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this, "该功能暂未开放，尽情期待。", Toast.LENGTH_LONG).show();
                        }
                    });
                    ImageView imgreport2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                    imgreport2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                            intent.putExtra("token",Login_token);
                            intent.putExtra("family_member_id",family_member_id_list.get(0));
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;

                default:
                    break;
            }
            ((ViewPager) arg0).addView(pageViews.get(arg1), 0);
            return pageViews.get(arg1);

        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            Log.e("scrollStateChanged", arg0 + "");




        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);

                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);
                }
            }
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*****分割线 上面是界面处理 后面是网络数据通信的处理****/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void oauthInit() {
        oauth = new Oauth();
        oauth.robot_sn = "HSRasd0170400003";
        //获取当前时间戳
        long timeStamp = System.currentTimeMillis() / 1000;//获取系统时间的10位的时间戳
        Log.e("timeStamp", timeStamp + "");
        oauth._timestamp = timeStamp + "";
        Log.e("oauth._timestamp", oauth._timestamp);
        int num = ((int) ((Math.random() * 9 + 1) * 100000));
        oauth.random_str = num + "";
        String pre = "2zXlePhpzRJJlGsErEmcWZK2O96SefLI" + oauth.random_str + oauth._timestamp;
        Log.e("pre", pre);
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
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    private void sendRequestWithOkHttp() {
        Log.e("sendRequestWithOkHttp", "sendRequestWithOkHttp");

        oauthInit();
        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("robot_sn", oauth.Getrobot_sn());
        builder.add("random_str", oauth.Getrandom_str());
        builder.add("_timestamp", oauth.Gettimestamp());
        builder.add("secret_code", oauth.Getsecretcode());

        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody", requestBody.toString());


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
                        GetFamilyDetails();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_GPS_INFO:
                    try {
                        Log.e("strfromserver",strfromserver);
                        UpdateGPSInfo(strfromserver);
                        Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                        intent.putExtra("lngData",lngData);
                        intent.putExtra("latData",latData);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case UPDATE_FAMILY_INFO:
                    try {
                        Log.e("strfromserver",strfromserver);
                        UpdateFamilyDetails(strfromserver);
                        String family_member_id = family_member_id_list.get(0);
                        GetMemberDetails(family_member_id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case UPDATE_MEMBER_INFO:
                    try {
                        Log.e("strfromserver",strfromserver);
                        UpdateMemberDetail(strfromserver);
                        UpdatePageData(0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    /*{"code":"200","msg":"success","result":{"lng":"121.4876408","lat":"31.200772"}}*/
    private void  UpdateGPSInfo(String strData)  throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int code = Integer.parseInt(jsonObject.getString("code"));
        Log.e("code", code + "");
        String str = jsonObject.getString("result");
        JSONObject json = new JSONObject(str);
        lngData = json.getString("lng");
        Log.e("lng", str);
        latData = json.getString("lat");



    }
    /*
    {"code":"200","msg":"success","result":
    {"sleep_data":{"sleep_time":"0","sleep_grade":"5"},
    "heart_data":{"avg_heart":89,"min_heart":0,"max_heart":0},
    "blood_data":{"systolic":122,"diastolic":80,"create_time":"10 :50am"},
    "sport_data":{"step_num":803,"calory":30}}}*/
    private void UpdateMemberDetail(String strData)  throws JSONException{
        try
        {
            JSONObject jsonObject = new JSONObject(strData);
            int code = Integer.parseInt(jsonObject.getString("code"));
            Log.e("code", code + "");
            String str = jsonObject.getString("result");
            Log.e("UpdateMemberDetail",str);
            /*{"sleep_data":{"sleep_time":"0","sleep_grade":"5"},
            "heart_data":{"avg_heart":89,"min_heart":0,"max_heart":0},
            "blood_data":{"systolic":122,"diastolic":80,"create_time":"10 :50am"},
            "sport_data":{"step_num":803,"calory":30}}
            * */
            JSONObject jsonMember = new JSONObject(str);
            str = jsonMember.getString("sleep_data");
            JSONObject jsonSleep = new JSONObject(str);
            sleep.sleep_time = jsonSleep.getInt("sleep_time");
            sleep.sleep_grade = jsonSleep.getInt("sleep_grade");
            getmonitor.sleep = sleep;
            str = jsonMember.getString("heart_data");
            JSONObject jsonHeart = new JSONObject(str);
            heart_data.avg_heart = jsonHeart.getInt("avg_heart");
            heart_data.max_heart = jsonHeart.getInt("max_heart");
            heart_data.min_heart = jsonHeart.getInt("min_heart");
            getmonitor.heart_data = heart_data;
            str = jsonMember.getString("blood_data");
            JSONObject jsonBlood = new JSONObject(str);
            blood_data.create_time = jsonBlood.getString("create_time");
            blood_data.diastolic = jsonBlood.getInt("diastolic");
            blood_data.systolic = jsonBlood.getInt("systolic");
            getmonitor.blood_data = blood_data;
            str = jsonMember.getString("sport_data");
            JSONObject jsonSport = new JSONObject(str);
            sport_data.step_num = jsonSport.getInt("step_num");
            sport_data.calory = jsonSport.getInt("calory");
            getmonitor.sport_data = sport_data;
        }
        catch (JSONException ex){
            Log.e("UpdateFamilyDetails",ex.toString());
            ex.printStackTrace();
        }
    }
/*
{"code":"200","msg":"success","result":
{"family_info":{"family_id":230,"family_name":"\u996d\u5c0f\u7c92\u7684\u5bb6\u5ead","family_number":"000230","member_count":1,"robot_imei":"HSRasd0170400003","affix":{"file_path":"http:\/\/sun.healthywo.com\/uploads\/family\/59eee51897296.jpg","is_image":1}},
"member_list":[{"member_info":{"member_id":"292","family_member_id":306,"is_member":1,"member_name":"fan","member_avatar":""},
"health_info":{"member_sex":0,"member_age":0,"member_height":160,"member_weight":60}}]}}
 */

    private void UpdateFamilyDetails(String strData)  throws JSONException{
        try {
            JSONObject jsonObject = new JSONObject(strData);
            int code = Integer.parseInt(jsonObject.getString("code"));
            Log.e("code", code + "");
            String strresult = jsonObject.getString("result");
            Log.e("strresult", strresult);
            JSONObject jsonresult = new JSONObject(strresult);
            String str = jsonresult.getString("family_info");
            Log.e("str", str);
            JSONObject familyresult = new JSONObject(str);
            str = familyresult.getString("family_id");
            Log.e("family_id", str);
            family.family_id = str;
            Log.e("family", family.getFamily_id());
            str = familyresult.getString("family_name");
            family.setFamily_name(str);
            Log.e("family getFamily_name", family.getFamily_name());
            str = familyresult.getString("family_number");
            family.setFamily_number(str);
            str = familyresult.getString("member_count");
            family.setMember_count(str);
            str = familyresult.getString("robot_imei");
            family.setRobot_imei(str);
            Log.e("setRobot_imei", family.getRobot_imei());
            //str = familyresult.getString("family_member_id");
            //family.setFamily_member_id(str);
            //Log.e("setFamily_member_id",family.getFamily_member_id());
            str = familyresult.getString("affix");
            JSONObject jsonAffix = new JSONObject(str);
            str = jsonAffix.getString("file_path");
            affix.setFile_path(str);
            int num = jsonAffix.getInt("is_image");
            affix.setIs_image(num);
            family.setAffix(affix);
            Log.e("setAffix", family.getAffix().toString());
            ////////////////////////////family//////////////////////////////
            /////////////////////////////member///////////////////////////////////
            String strmember = jsonresult.getString("member_list");
            Log.e("strmember", strmember);
            JSONArray jsonmember = new JSONArray(strmember);
            for (int i=0;i<jsonmember.length();i++)
            {
                JSONObject jsonObject1 = jsonmember.getJSONObject(i);
                String member_info = jsonObject1.getString("member_info");
                String health_info = jsonObject1.getString("health_info");
                Log.e("health_info",health_info);
                JSONObject memberJSONObject = new JSONObject(member_info);
                member.member_id = memberJSONObject.getString("member_id");
                Log.e("member.member_id",member.member_id);
                member.member_avatar = memberJSONObject.getString("member_avatar");
                Log.e("member.member_avatar",member.member_avatar);
                member.member_name = memberJSONObject.getString("member_name");
                Log.e("member.member_name",member.member_name);
                member.family_member_id = memberJSONObject.getString("family_member_id");
                Log.e("family_member_id",member.family_member_id );
                family_member_id_list.add(i,member.family_member_id);
                member.is_member = memberJSONObject.getInt("is_member");
                Log.e("member.is_member ",member.is_member+"");
                JSONObject healthJSONObject = new JSONObject(health_info);
                health.member_age = healthJSONObject.getInt("member_age");
                Log.e("health.member_age",health.member_age+"");
                health.member_weight = healthJSONObject.getDouble("member_weight");
                Log.e("health.member_weight",health.member_weight+"");
                health.member_height = healthJSONObject.getDouble("member_height");
                Log.e("health.member_height",health.member_height+"");
                health.member_sex = healthJSONObject.getInt("member_sex");
                Log.e("health.member_sex",health.member_sex+"");
                member.heath = health;
                if(member.equals(null))
                {
                    Log.e("member.equals(null)","member.equals(null)");
                }
                member_list.add(i,member);


            }
            //str = jsonmember.getString("member_info");
           // Log.e("str", str);
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
            Log.e("JSONException",ex.toString());
        }

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

    private void GetFamilyDetails() {
        Log.e("GetFamilyDetails", "GetFamilyDetails");


        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token",Login_token);
        builder.add("robot_sn", oauth.Getrobot_sn());


        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody", requestBody.toString());


        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://sun.healthywo.com/robot/family/family_info")
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
                        msg.what = UPDATE_FAMILY_INFO;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }

    private void GetMemberDetails(String family_member_id) {
        Log.e("GetMemberDetails", "GetMemberDetails");


        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token",Login_token);
        builder.add("family_member_id", family_member_id);


        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody", requestBody.toString());


        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://sun.healthywo.com/robot/family/get_monitor")
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
                        msg.what = UPDATE_MEMBER_INFO;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }

    private void GetGpsInfo(String family_member_id) {
        Log.e("GetMemberDetails", "GetMemberDetails");
        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token",Login_token);
        builder.add("family_member_id", family_member_id);


        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody", requestBody.toString());


        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://sun.healthywo.com/robot/family/get_location")
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
                        msg.what = UPDATE_GPS_INFO;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }
}
