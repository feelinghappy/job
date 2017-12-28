package com.hrg.anew;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendDirection;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hrg.anew.bean.Blood_data;
import com.hrg.anew.bean.Heart_data;
import com.hrg.anew.bean.History;
import com.hrg.anew.bean.Sleep;
import com.hrg.anew.bean.Sport_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static java.lang.Float.parseFloat;

public class ReportActivity extends Activity {
    private LineChart mLineChart;
    private TextView chart_value;
    private TextView report_title_1;
    private TextView month_index;
    private TextView report_title_2;
    private TextView report_title_3;
    private TextView report_value_1;
    private TextView report_value_2;
    private TextView report_value_3;
    private TextView report_value_uint_1;
    private TextView report_value_uint_2;
    private TextView report_value_uint_3;
    private TextView report_select_1;
    private TextView report_select_2;
    private TextView report_select_3;
    private ImageView report_img1;
    private ImageView report_img2;
    private ImageView report_img3;
    private ImageView chart_icon;
    public  String  function ="blood";
    private String  time_select = "day";
    public static final  int UPDATE_BLOOD_DAY = 1;
    public static final  int UPDATE_RATE_DAY = 2;
    public static final  int UPDATE_WALK_DAY = 3;
    public static final  int UPDATE_SLEEP_DAY = 4;
    public static final  int UPDATE_BLOOD_MONTH = 5;
    public static final  int UPDATE_RATE_MONTH = 6;
    public static final  int UPDATE_WALK_MONTH = 7;
    public static final  int UPDATE_SLEEP_MONTH = 8;
    public static final  int UPDATE_BLOOD_YEAR = 9;
    public static final  int UPDATE_RATE_YEAR = 10;
    public static final  int UPDATE_WALK_YEAR = 11;
    public static final  int UPDATE_SLEEP_YEAR = 12;
    public static final  int UPDATE_HISTORY_INFO =30;
    private Message msg = new Message();
    private String token;
    private String family_member_id;
    private String strfromserver;
    final OkHttpClient client = new OkHttpClient();
    private int data_time;
    private Bundle bundle;
    private int content;
    private ImageView  report_back;
    History.Sleep_history sleep_history = new History.Sleep_history();
    History.Heart_data_history  heart_data_history = new History.Heart_data_history ();
    Blood_data blood_data = new Blood_data();
    History.Sport_data_history sport_data_history = new History.Sport_data_history();
    private List<History.Heart_data_history> heart_data_list = new ArrayList<>();
    private List<String> heart_avg_list = new ArrayList<>();
    private List<String> heart_time_list = new ArrayList<>();
    private List<Blood_data> blood_data_list = new ArrayList<>();
    private List<String>blood_systolic_list = new ArrayList<>();
    private List<String>blood_diastolic_list = new ArrayList<>();
    private List<History.Sport_data_history> sport_data_list = new ArrayList<>();
    private List<History.Sleep_history> sleep_list = new ArrayList<>();
    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<Entry> yValues = new ArrayList<Entry>();
    ArrayList<LineDataSet> dataSets = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.report_layout);
        hideVirtualKey();
        hideNavigationBar();
        Intent intent = new Intent();
        this.bundle = getIntent().getExtras();
        token = this.bundle.getString("token");
        Log.e("token", token);
        family_member_id = this.bundle.getString("family_member_id");
        Log.e("family_member_id", family_member_id);
        try {
            String s = getBeforeMonth();
            dateToStamp(s);
            GetHistoryData();
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("InitData",e.toString());
        }
        month_index = (TextView)findViewById(R.id.month_index);
        chart_icon = (ImageView)findViewById(R.id.chart_icon);
        report_back = (ImageView)findViewById(R.id.report_back);
        report_select_1 = (TextView) findViewById(R.id.report_select_1);
        report_select_2 = (TextView) findViewById(R.id.report_select_2);
        report_select_3 = (TextView) findViewById(R.id.report_select_3);
        report_title_1 = (TextView) findViewById(R.id.report_title_1);
        report_title_2 = (TextView) findViewById(R.id.report_title_2);
        report_title_3 = (TextView) findViewById(R.id.report_title_3);
        report_value_1 = (TextView) findViewById(R.id.report_value_1);
        report_value_2 = (TextView) findViewById(R.id.report_value_2);
        report_value_3 = (TextView) findViewById(R.id.report_value_3);
        report_value_uint_1 = (TextView) findViewById(R.id.report_value_unit_1);
        report_value_uint_2 = (TextView) findViewById(R.id.report_value_unit_2);
        report_value_uint_3 = (TextView) findViewById(R.id.report_value_unit_3);
        report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
        report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
        report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
        report_img1 = (ImageView) findViewById(R.id.report_img1);
        report_img2 = (ImageView) findViewById(R.id.report_img2);
        report_img3 = (ImageView) findViewById(R.id.report_img3);
        mLineChart = (LineChart) findViewById(R.id.chart);
        report_img1.setImageResource(R.drawable.select_blood);
        report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
        if(function.equals("blood"))
        {
            report_title_1.setText("高血压");
            report_title_2.setText("血压");
            report_title_3.setText("低血压");
            report_value_1.setText("120");
            report_value_2.setText("正常");
            report_value_3.setText("80");
            report_value_uint_1.setText("mmgh");
            report_value_uint_2.setText("    ");
            report_value_uint_3.setText("mmgh");
            function = "blood";
            report_img1.setImageResource(R.drawable.select_blood);
            report_img2.setImageResource(R.drawable.select_blood);
            report_img3.setImageResource(R.drawable.select_blood);
            report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
            report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
            report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
            content = UPDATE_BLOOD_DAY;
        }
        //通过findViewById获得RadioGroup对象  
        RadioGroup raGrouphis = (RadioGroup) findViewById(R.id.history);
        //添加事件监听器  
        raGrouphis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                  if (checkedId == R.id.btn_0) {
                      report_title_1.setText("高血压");
                      report_title_2.setText("血压");
                      report_title_3.setText("低血压");
                      report_value_1.setText("120");
                      report_value_2.setText("正常");
                      report_value_3.setText("80");
                      report_value_uint_1.setText("mmgh");
                      report_value_uint_2.setText("    ");
                      report_value_uint_3.setText("mmgh");
                      function = "blood";
                      report_img1.setImageResource(R.drawable.select_blood);
                      report_img2.setImageResource(R.drawable.select_blood);
                      report_img3.setImageResource(R.drawable.select_blood);
                      report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                      report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                      report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
                      content = UPDATE_BLOOD_DAY;
                      chart_icon.setVisibility(View.VISIBLE);
                      ShowNewChart(content);

                  } else if (checkedId == R.id.btn_1) {
                      report_title_1.setText("最高心率");
                      report_title_2.setText("平均心率");
                      report_title_3.setText("最低心率");
                      report_value_1.setText("120");
                      report_value_2.setText("100");
                      report_value_3.setText("80");
                      report_value_uint_1.setText("bpm");
                      report_value_uint_2.setText("bpm");
                      report_value_uint_3.setText("bmp");
                      function = "rate";
                      chart_icon.setVisibility(View.GONE);
                      report_img1.setImageResource(R.drawable.select_rate);
                      report_img2.setImageResource(R.drawable.select_rate);
                      report_img3.setImageResource(R.drawable.select_rate);
                      report_select_1.setTextColor(getResources().getColor(R.color.colorRate));
                      report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                      report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                      content = UPDATE_RATE_DAY;

                      ShowNewChart(content);
                      mLineChart.invalidate();
                  } else if (checkedId == R.id.btn_2) {
                      //LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                      //layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.sport));
                      chart_icon.setVisibility(View.GONE);
                      report_title_1.setText("累计行走");
                      report_title_2.setText("运动步数");
                      report_title_3.setText("消耗");
                      report_value_1.setText("12");
                      report_value_2.setText("20145");
                      report_value_3.setText("458");
                      report_value_uint_1.setText("km");
                      report_value_uint_2.setText("");
                      report_value_uint_3.setText("cal");
                      function = "walk";
                      report_img1.setImageResource(R.drawable.select_walk);
                      report_select_1.setTextColor(getResources().getColor(R.color.colorWalk));
                      report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                      report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                      content = UPDATE_WALK_DAY;
                      ShowNewChart(content);
                      mLineChart.invalidate();
                  } else if (checkedId == R.id.btn_3) {
                      chart_icon.setVisibility(View.GONE);
                      report_title_1.setText("睡眠时长");
                      report_title_2.setText("睡眠质量");
                      report_title_3.setText("深睡时长");
                      report_value_1.setText("9.0");
                      report_value_2.setText("一般");
                      report_value_3.setText("4.2");
                      report_value_uint_1.setText("h");
                      report_value_uint_2.setText("  ");
                      report_value_uint_3.setText("h");
                      function = "sleep";
                      report_img1.setImageResource(R.drawable.select_sleep);
                      report_select_1.setTextColor(getResources().getColor(R.color.colorSleep));
                      report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                      report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                      content = UPDATE_SLEEP_DAY;
                      ShowNewChart(content);
                      mLineChart.invalidate();
                  }

              }
          }
        );
        report_value_uint_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_img1.setVisibility(View.VISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "day";
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                if (function.equals("blood")) {
                    content = UPDATE_BLOOD_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
                    report_img1.setImageResource(R.drawable.select_blood);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                    //report_img1.setVisibility(View.VISIBLE);
                    //report_img2.setVisibility(View.INVISIBLE);
                    //report_img3.setVisibility(View.INVISIBLE);

                } else if (function.equals("rate")) {
                    content = UPDATE_RATE_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorRate));
                    report_img1.setImageResource(R.drawable.select_rate);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("walk")) {
                    content = UPDATE_WALK_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorWalk));
                    report_img1.setImageResource(R.drawable.select_walk);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("sleep")) {
                    content = UPDATE_SLEEP_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorSleep));
                    report_img1.setImageResource(R.drawable.select_sleep);
                    Log.e("report_value_uint_1","UPDATE_SLEEP_DAY");
                    ShowNewChart(content);
                    mLineChart.invalidate();
                }
            }

        });
        report_value_uint_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img2.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "month";
                if(function.equals("blood"))
                {
                    content = UPDATE_BLOOD_MONTH;
                    report_select_2.setTextColor(getResources().getColor(R.color.colorBlood));
                    report_img2.setImageResource(R.drawable.select_blood);
                    ShowNewChart(content);
                    mLineChart.invalidate();

                }
                else if(function.equals("rate"))
                {
                    content= UPDATE_RATE_MONTH;
                    report_select_2.setTextColor(getResources().getColor(R.color.colorRate));
                    report_img2.setImageResource(R.drawable.select_rate);
                    ShowNewChart(content);
                    mLineChart.invalidate();

                }
                else if(function.equals("walk"))
                {
                    content = UPDATE_WALK_MONTH;
                    report_select_2.setTextColor(getResources().getColor(R.color.colorWalk));
                    report_img2.setImageResource(R.drawable.select_walk);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                }
                else if(function.equals("sleep"))
                {
                    content = UPDATE_SLEEP_MONTH;
                    report_img2.setImageResource(R.drawable.select_sleep);
                    report_select_2.setTextColor(getResources().getColor(R.color.colorSleep));
                    ShowNewChart(content);
                    mLineChart.invalidate();

                }

            }
        });
        report_value_uint_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img3.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                time_select = "year";
                if (function.equals("blood")) {
                    content = UPDATE_BLOOD_YEAR;
                    report_img3.setImageResource(R.drawable.select_blood);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorBlood));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("rate")) {
                    content = UPDATE_RATE_YEAR;
                    report_img3.setImageResource(R.drawable.select_rate);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorBlood));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("walk")) {
                    content = UPDATE_WALK_YEAR;
                    report_img3.setImageResource(R.drawable.select_blood);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorBlood));
                    ShowNewChart(content);
                } else if (function.equals("sleep")) {
                    content = UPDATE_SLEEP_YEAR;
                    report_img3.setImageResource(R.drawable.select_sleep);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorSleep));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                }
            }
        });
        report_select_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_img1.setVisibility(View.VISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "day";
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                if (function.equals("blood"))
                {
                    content =  UPDATE_BLOOD_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorBlood));
                    report_img1.setImageResource(R.drawable.select_blood);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                    //report_img1.setVisibility(View.VISIBLE);
                   //report_img2.setVisibility(View.INVISIBLE);
                    //report_img3.setVisibility(View.INVISIBLE);

                 }
                 else if (function.equals("rate")) {
                    content = UPDATE_RATE_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorRate));
                    report_img1.setImageResource(R.drawable.select_rate);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                 }
                 else if (function.equals("walk"))
                 {
                     content =UPDATE_WALK_DAY;
                     report_select_1.setTextColor(getResources().getColor(R.color.colorWalk));
                     report_img1.setImageResource(R.drawable.select_walk);
                     ShowNewChart(content);
                     mLineChart.invalidate();
                 }
                else if (function.equals("sleep"))
                {
                    content = UPDATE_SLEEP_DAY;
                    report_select_1.setTextColor(getResources().getColor(R.color.colorSleep));
                    report_img1.setImageResource(R.drawable.select_sleep);
                    ShowNewChart(content);
                    mLineChart.invalidate();
                }

            }
        });
        report_select_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img2.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "month";
                        if(function.equals("blood"))
                        {
                            content = UPDATE_BLOOD_MONTH;
                            report_select_2.setTextColor(getResources().getColor(R.color.colorBlood));
                            report_img2.setImageResource(R.drawable.select_blood);
                            ShowNewChart(content);

                        }
                        else if(function.equals("rate"))
                        {
                            content= UPDATE_RATE_MONTH;
                            report_select_2.setTextColor(getResources().getColor(R.color.colorRate));
                            report_img2.setImageResource(R.drawable.select_rate);
                            ShowNewChart(content);

                        }
                        else if(function.equals("walk"))
                        {
                            content = UPDATE_WALK_MONTH;
                            report_select_2.setTextColor(getResources().getColor(R.color.colorWalk));
                            report_img2.setImageResource(R.drawable.select_walk);
                            ShowNewChart(content);
                        }
                        else if(function.equals("sleep"))
                        {
                            content = UPDATE_SLEEP_MONTH;
                            report_img2.setImageResource(R.drawable.select_sleep);
                            report_select_2.setTextColor(getResources().getColor(R.color.colorSleep));
                            ShowNewChart(content);

                        }

            }
        });
        report_select_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img3.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                time_select = "year";
                if (function.equals("blood")) {
                    content = UPDATE_BLOOD_YEAR;
                    report_img3.setImageResource(R.drawable.select_blood);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorBlood));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("rate")) {
                    content = UPDATE_RATE_YEAR;
                    report_img3.setImageResource(R.drawable.select_rate);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorRate));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("walk")) {
                    content = UPDATE_WALK_YEAR;
                    report_img3.setImageResource(R.drawable.select_walk);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorWalk));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                } else if (function.equals("sleep")) {
                    content = UPDATE_SLEEP_YEAR;
                    report_img3.setImageResource(R.drawable.select_sleep);
                    report_select_3.setTextColor(getResources().getColor(R.color.colorSleep));
                    ShowNewChart(content);
                    mLineChart.invalidate();
                }
            }
        });
        report_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ShowNewChart(content);
    }
    private void InitData() throws JSONException, ParseException {

        UpdateHistoryData(strfromserver);
    }


    private void ShowUPDATE_RATE_DAY()
    {
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);
        XAxis xl = mLineChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(30f); // 设置字体大小
        xl.setSpaceBetweenLabels(0); // 设置数据之间的间距'
        ArrayList<String> xValues = new ArrayList<String>();
        // create a dataset and give it a type    
        // y轴的数据集合 
        // y轴的数据    
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        String strRate;
        String strTime;
        for(int i = 0; i< heart_avg_list.size(); i++)
        {
            strRate =  heart_avg_list.get(i);
            strTime = heart_time_list.get(i);
            DateFormat fmt =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            xValues.add((i+1)+"");
            Log.e(i+"heart_data.avg_heart",heart_data_history.avg_heart);
            yValues.add(new Entry(parseFloat(strRate), i));

            try {
                Date date = fmt.parse(strTime);
                int year = date.getYear();
                int month = date.getMonth();
                month_index.setText((month+1)+"月");
                int day = date.getDate();
                Log.e("ShowUPDATE_RATE_DAY day",day+"");
                Log.e("UPDATE_RATE_DAY month",month+"");
            }
            catch(ParseException ex)
            {
                ex.printStackTrace();
                Log.e("ex.printStackTrace()",ex.toString());
            }
        }
        LineDataSet lineDataSet = new LineDataSet(yValues, "");
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(2.0f);// 线宽    
        lineDataSet.setCircleSize(2);// 显示的圆形大小       
        lineDataSet.setDrawCubic(true);
        lineDataSet.setCircleColor(Color.parseColor("#FF693E"));
        lineDataSet.setDrawHighlightIndicators(true);
        // 圆球的颜色  
        //点击后，十字交叉线的颜色  +
        lineDataSet.setColor(Color.parseColor("#FF693E"));// 显示颜色 
        lineDataSet.setHighLightColor(Color.parseColor("#00FFFFFF"));
        lineDataSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });//  
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet);
        LineData lineData = new LineData(xValues, lineDataSets);

        //设置是否可以触摸，如为false，则不能拖动，缩放等
        LineData mLineData = lineData;
        mLineChart.setTouchEnabled(true);
        showChart(mLineChart, mLineData, Color.rgb(255, 105,62));
        //隐藏左边坐标轴横网格线
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);// 隐藏右边 的坐标轴
        mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);// 让x轴在下面
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.colorTM));
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setAxisLineColor(getResources().getColor(R.color.colorRate));
        mLineChart.getXAxis().setAxisLineWidth(1f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value,function);

        mLineChart.setMarkerView(mv);

    }
    private void  ShowNewChart(int content)
    {
        switch (content)
        {
            case UPDATE_BLOOD_DAY:
                Log.e("UPDATE_BLOOD_DAY","UPDATE_BLOOD_DAY");
                ShowBloodDayChart();
                break;
            case UPDATE_BLOOD_MONTH:
                Log.e("UPDATE_BLOOD_MONTH","UPDATE_BLOOD_MONTH");
                ShowBloodDayChart();
                break;
            case UPDATE_BLOOD_YEAR:
                ShowBloodDayChart();
                break;
            case UPDATE_RATE_DAY:
                Log.e("UPDATE_RATE_DAY","UPDATE_RATE_DAY");
                ShowUPDATE_RATE_DAY();
                break;
            case UPDATE_RATE_MONTH:
                ShowUPDATE_RATE_DAY();
                break;
            case UPDATE_RATE_YEAR:
                ShowUPDATE_RATE_DAY();
                break;
            case UPDATE_SLEEP_DAY:
                ShowSleepDayChart();
                break;
            case UPDATE_SLEEP_MONTH:
                ShowSleepDayChart();
                break;
            case UPDATE_SLEEP_YEAR:
                ShowSleepDayChart();
                break;
            case UPDATE_WALK_DAY:
                Log.e("UPDATE_WALK_DAY","UPDATE_WALK_DAY");
                ShowWalkDayChart();

                break;
            case UPDATE_WALK_MONTH:
                ShowWalkDayChart();
                break;
            case UPDATE_WALK_YEAR:
                ShowWalkDayChart();
                break;



        }
    }

    // 设置显示的样式    
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);//是否在折线图上添加边框    

        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background    
        lineChart.setDrawGridBackground(false);// 是否显示表格颜色    

        // enable touch gestures    
        lineChart.setTouchEnabled(true);// 设置是否可以触摸    

        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    

        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//   
        //取消legend



        // add data    
        lineChart.setData(lineData);// 设置数据    
    }
    /**  
    * 生成一个数据  
     * * @param count 表示图表中有多少个坐标点  
     * @param range 用来生成range以内的随机数  
     * @return  
     */
     private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++)
         {
            // x轴显示的数据，这里默认使用数字下标显示    
            xValues.add("" + i);
         }
         // create a dataset and give it a type    
         // y轴的数据集合 
          // y轴的数据    
          ArrayList<Entry> yValues = new ArrayList<Entry>();
          for(int i = 0; i< count; i++)
          {
              float value =(float)(Math.random()* range) + 3;
              yValues.add(new Entry(value, i));
          }
         LineDataSet lineDataSet = new LineDataSet(yValues, "");
         //用y轴的集合来设置参数    
         lineDataSet.setLineWidth(2.0f);// 线宽    
         lineDataSet.setCircleSize(4);// 显示的圆形大小       
         lineDataSet.setDrawCubic(true);

         lineDataSet.setCircleColor(Color.parseColor("#FF693E"));
         lineDataSet.setDrawHighlightIndicators(true);
         // 圆球的颜色  
         //点击后，十字交叉线的颜色  +
        lineDataSet.setColor(Color.parseColor("#FF693E"));// 显示颜色 
        lineDataSet.setHighLightColor(Color.parseColor("#00FFFFFF"));

        lineDataSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });

         ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
         lineDataSets.add(lineDataSet);// add the datasets    

         // create a data object with the datasets    
         LineData lineData = new LineData(xValues, lineDataSets);

         return lineData;
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
    /*
* 将时间戳转换为时间
*/
    public String stampToDate(long timeMillis) {
        String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                format(new java.util.Date(timeMillis * 1000));
      return date;
    }

        /*
         * 将时间转换为时间戳
         */
    public void dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        data_time =(int) (ts/1000);
        Log.e("data",data_time +"");
    }

    public String getBeforeDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
       //过去1天
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去1天："+day);
        return day;
    }

    public String getBeforeMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去1天
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去1月："+day);
        return day;
    }

    public  String getBeforeYear() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        //过去1天
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date d = c.getTime();
        String day = format.format(d);
        System.out.println("过去1年："+day);
        return day;
    }
/*
 {"code":"200","msg":"success","result":
 {"sleep_data":[{"sleep_time":"0","sleep_grade":"5","start_time":"0 :00"}],
 "heart_data":[{"avg_heart":89,"min_heart":0,"max_heart":0,"create_time":1508208529},
 {"avg_heart":97,"min_heart":0,"max_heart":0,"create_time":1508208590},
 {"avg_heart":83,"min_heart":0,"max_heart":0,"create_time":1508208570},
 {"avg_heart":89,"min_heart":0,"max_heart":0,"create_time":1508208405},
 {"avg_heart":87,"min_heart":0,"max_heart":0,"create_time":1508208554},
 {"avg_heart":91,"min_heart":0,"max_heart":0,"create_time":1508208606},
 {"avg_heart":102,"min_heart":0,"max_heart":0,"create_time":1508208374},
 {"avg_heart":118,"min_heart":0,"max_heart":0,"create_time":1508208431},
 {"avg_heart":105,"min_heart":0,"max_heart":0,"create_time":1508208493},
 {"avg_heart":106,"min_heart":0,"max_heart":0,"create_time":1508208463},
 {"avg_heart":107,"min_heart":0,"max_heart":0,"create_time":1508208447},
 {"avg_heart":75,"min_heart":0,"max_heart":0,"create_time":1508208510},
 {"avg_heart":66,"min_heart":0,"max_heart":0,"create_time":1508228790},
 {"avg_heart":72,"min_heart":0,"max_heart":0,"create_time":1510819493}],
 "blood_data":[{"systolic":122,"diastolic":80,"create_time":1508208648},
 {"systolic":117,"diastolic":79,"create_time":1510819686}],
 "sport_data":[{"step_num":215,"calory":9,"create_time":1508169600},
 {"step_num":0,"calory":0,"create_time":1508256000},
 {"step_num":18,"calory":0,"create_time":1510070400},
 {"step_num":803,"calory":30,"create_time":1510761600},
 {"step_num":2933,"calory":113,"create_time":1510848000}]}}
* */

    private void UpdateHistoryData(String strData) throws JSONException {
        JSONObject jsonObject = new JSONObject(strData);
        int  code =Integer.parseInt(jsonObject.getString("code"));
        Log.e("code",code+"");
        String strresult = jsonObject.getString("result");
        Log.e("strresult",strresult);
        JSONObject jsonresult = new JSONObject(strresult);
        String strSleep = jsonresult.getString("sleep_data");
        Log.e("sleep_data",strSleep);
        String strheart_data = jsonresult.getString("heart_data");
        Log.e("strexpires_in",strheart_data);
        String strblood_data = jsonresult.getString("blood_data");
        String strSport_data = jsonresult.getString("sport_data");
        JSONArray sleeparray = new JSONArray(strSleep);
        for (int i = 0;i<sleeparray.length();i++)
        {
            JSONObject object =sleeparray.getJSONObject(i);
            sleep_history.sleep_grade = object.getInt("sleep_grade");
            sleep_history.sleep_time = object.getInt("sleep_time");
   //         sleep_history.create_time =  stampToDate((long)object.getInt("create_time"));
            sleep_list.add(i,sleep_history);
        }
        JSONArray bloodarray = new JSONArray(strblood_data);
        for(int i=0;i<bloodarray.length();i++)
        {
            JSONObject object = bloodarray.getJSONObject(i);
            blood_data.diastolic = object.getInt("diastolic");
            blood_data.create_time = stampToDate((long)(object.getInt("create_time")));
            Log.e("blood_data.create_time",blood_data.create_time+"");
            blood_data.systolic = object.getInt("systolic");
            blood_data_list.add(i,blood_data);
            blood_diastolic_list.add(object.getInt("diastolic")+"");
            blood_systolic_list.add(object.getInt("systolic")+"");
        }
        JSONArray sportarray = new JSONArray(strSport_data);
        for(int i=0;i<sportarray.length();i++)
        {
            JSONObject object = sportarray.getJSONObject(i);
            sport_data_history.calory = object.getInt("calory");
            Log.e("sport_data_.calory",sport_data_history.calory+"" );
            sport_data_history.create_time = stampToDate((long)object.getInt("create_time"));
            Log.e("sport_data_history",sport_data_history.create_time);
            sport_data_history.step_num = object.getInt("step_num");
            sport_data_list.add(i,sport_data_history);
        }
        for (int i=0;i<sport_data_list.size();i++)
        {
            sport_data_history  = sport_data_list.get(i);
            Log.e("sport_data_.calory",sport_data_history.calory+"");
        }
        JSONArray heartarray = new JSONArray(strheart_data);
        Log.e("strheart_data",strheart_data);
        Log.e("heartarray",heartarray.length()+"");

        for(int i=0;i<heartarray.length();i++)
        {
            JSONObject object = heartarray.getJSONObject(i);
            heart_data_history.avg_heart = object.getString("avg_heart");
            Log.e("avg_heart",heart_data_history.avg_heart+"");
            heart_data_history.max_heart = object.getInt("max_heart");
            heart_data_history.min_heart = object.getInt("min_heart");
            heart_data_history.create_time = stampToDate(((long)object.getInt("create_time")));
            Log.e("heart_data_history",heart_data_history.create_time);
            heart_data_list.add(heart_data_history);
            heart_avg_list.add(object.getString("avg_heart"));
            heart_time_list.add(stampToDate(((long)object.getInt("create_time"))));
        }
        for (int i=0;i<heart_avg_list.size();i++)
        {
            String str =  heart_avg_list.get(i);
            String time = heart_data_history.create_time;
            DateFormat fmt =new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            xValues.add((i+1)+"");
            Log.e(i+" heart_avg_list",str);
            yValues.add(new Entry(parseFloat(heart_data_history.avg_heart), i));

            try {
                Date date = fmt.parse(time);
                int year = date.getYear();
                int month = date.getMonth();
                month_index.setText((month+1)+"月");
                int day = date.getDate();
                Log.e("UpdateHistoryData  day",day+"");
                Log.e("UpdateHistoryData month",month+"");
            }
            catch(ParseException ex)
            {
                ex.printStackTrace();
                Log.e("ex.printStackTrace()",ex.toString());
            }
        }
    }

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BLOOD_DAY:
                /*    try {
                        Log.e("UPDATE_BLOOD_DAY","UPDATE_BLOOD_DAY");
                        String s = getBeforeMonth();
                        dateToStamp(s);
                        GetHistoryData();
                        UpdateHistoryData(strfromserver);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UPDATE_BLOOD_DAY", e.toString() );
                    }*/
                    break;
                case UPDATE_RATE_DAY:
                    try {
                        Log.e("UPDATE_RATE_DAY","UPDATE_RATE_DAY");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_WALK_DAY:
                    try {
                        ShowWalkDayChart();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_SLEEP_DAY:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_BLOOD_MONTH:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_RATE_MONTH:
                    try {

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_WALK_MONTH:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_SLEEP_MONTH:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_WALK_YEAR:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_SLEEP_YEAR:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_BLOOD_YEAR:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_RATE_YEAR:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_HISTORY_INFO:
                    try {
                        Log.e("UPDATE_HISTORY_INFO",strfromserver);
                        UpdateHistoryData(strfromserver);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UPDATE_HISTORY_INFO",e.toString());
                    }
                    break;

                default:
                    break;
            }
        }

    };
    // 设置显示的样式    
    private void showWalkDayChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);//是否在折线图上添加边框    

        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background    
        lineChart.setDrawGridBackground(false);// 是否显示表格颜色    

        // enable touch gestures    
        lineChart.setTouchEnabled(true);// 设置是否可以触摸    

        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    

        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//   
        //取消legend



        // add data    
        lineChart.setData(lineData);// 设置数据    
    }
    private LineData getWalkDayLineData(int count, float range)
    {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 1; i < count+1; i++)
        {
            // x轴显示的数据，这里默认使用数字下标显示    
            xValues.add("" + i);
        }
        // create a dataset and give it a type    
        // y轴的数据集合 
        // y轴的数据    
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for(int i = 0; i< count; i++)
        {
            float value =(float)(Math.random()* range)/3 + 7000;
            yValues.add(new Entry(value, i));
        }
        LineDataSet lineDataSet = new LineDataSet(yValues, "");
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(2.0f);// 线宽    
        lineDataSet.setCircleSize(4);// 显示的圆形大小       
        lineDataSet.setDrawCubic(true);
        lineDataSet.setCircleColor(Color.parseColor("#1BB284"));
        lineDataSet.setDrawHighlightIndicators(true);
        // 圆球的颜色  
        //点击后，十字交叉线的颜色  +
        lineDataSet.setColor(Color.parseColor("#1BB284"));// 显示颜色 
        lineDataSet.setHighLightColor(Color.parseColor("#00FFFFFF"));

        lineDataSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet);// add the datasets    

        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }
    private void ShowWalkDayChart()
    {
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);
        XAxis xl = mLineChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(30f); // 设置字体大小
        xl.setSpaceBetweenLabels(0); // 设置数据之间的间距'

        LineData mLineData = getWalkDayLineData(15, 20000);
        showWalkDayChart(mLineChart, mLineData, Color.rgb(255, 105,62));
        //隐藏左边坐标轴横网格线
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);// 隐藏右边 的坐标轴
        mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);// 让x轴在下面
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.colorTM));
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setAxisLineColor(Color.parseColor("#1BB284"));
        mLineChart.getXAxis().setAxisLineWidth(1f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value,function);
        mLineChart.setMarkerView(mv);
        mLineChart.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.e("dataSetIndex",dataSetIndex+"");

                Log.e("Entry",e.toString());
                int xindex = e.getXIndex();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }
    private void GetHistoryData() {
        Log.e("GetMemberDetails", "GetMemberDetails");


        /**
         * 创建请求的参数body
         */
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token",token);
        builder.add("data_time",data_time+"");
        builder.add("family_member_id", family_member_id);


        /**
         * 遍历key
         */
        RequestBody requestBody = builder.build();
        Log.e("requestBody", requestBody.toString());


        //设置编码
        //发送Post,并返回一个HttpResponse对象


        final Request request = new Request.Builder()
                .url("http://sun.healthywo.com/robot/family/get_monitor_history")
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
                        msg.what = UPDATE_HISTORY_INFO;
                        handler.sendMessage(msg);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();


    }
    /////////////////////////血压///////////////////////////////////
    // 设置显示的样式    
    private void showBloodDayChart(LineChart lineChart, LineData lineData1,LineData lineData2) {
        lineChart.setDrawBorders(false);//是否在折线图上添加边框    

        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background    
        lineChart.setDrawGridBackground(false);// 是否显示表格颜色    

        // enable touch gestures    
        lineChart.setTouchEnabled(true);// 设置是否可以触摸    

        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    

        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//   
        //取消legend




        // add data    
        lineChart.setData(lineData1);// 设置数据  
        // // add data    
        lineChart.setData(lineData2);// 设置数据   
    }

    private void ShowBloodDayChart()
    {
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);
        XAxis xl = mLineChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(30f); // 设置字体大小
        xl.setSpaceBetweenLabels(0); // 设置数据之间的间距'

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 1; i < 16; i++)
        {
            // x轴显示的数据，这里默认使用数字下标显示    
            xValues.add("" + i);
        }
        // create a dataset and give it a type    
        // y轴的数据集合 
        // y轴的数据    
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        ArrayList<Entry> yAddValues = new ArrayList<Entry>();
        for(int i = 1; i< 16; i++)
        {
            float value =(float)(Math.random()* 130/3+50) + 30;
            float valueAdd =(float)(Math.random()* 130/3+50) + 3-30;
            yValues.add(new Entry(value, i));
            yAddValues.add(new Entry(valueAdd, i));
        }

        LineDataSet lineDataSet = new LineDataSet(yValues, "");
        LineDataSet lineDataAddSet = new LineDataSet(yAddValues, "");
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(2.0f);// 线宽  
        lineDataAddSet.setLineWidth(2.0f);// 线宽 
        lineDataSet.setCircleSize(4);// 显示的圆形大小  
        lineDataAddSet.setCircleSize(4);// 显示的圆形大小//      
        lineDataSet.setDrawCubic(true);
        lineDataAddSet.setDrawCubic(true);
        lineDataSet.setCircleColor(Color.parseColor("#1B87F3"));
        lineDataAddSet.setCircleColor(Color.parseColor("#FEA501"));
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataAddSet.setDrawHighlightIndicators(true);
        // 圆球的颜色  
        //点击后，十字交叉线的颜色  +
        lineDataSet.setColor(Color.parseColor("#1B87F3"));// 显示颜色 
        lineDataSet.setHighLightColor(Color.parseColor("#00FFFFFF"));
        //点击后，十字交叉线的颜色  +
        lineDataAddSet.setColor(Color.parseColor("#FEA501"));// 显示颜色 
        lineDataAddSet.setHighLightColor(Color.parseColor("#00FFFFFF"));

        lineDataSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });
        lineDataAddSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        ArrayList<LineDataSet> lineAddDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet);// add the datasets    
        lineAddDataSets.add(lineDataSet);// add the datasets  
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);
        LineData lineAddData = new LineData(xValues, lineAddDataSets);


        showBloodDayChart(mLineChart, lineData,lineAddData);
        //隐藏左边坐标轴横网格线
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);// 隐藏右边 的坐标轴
        mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);// 让x轴在下面
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.colorTM));
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setAxisLineColor(Color.parseColor("#FEA501"));
        mLineChart.getXAxis().setAxisLineWidth(1f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value,function);
        mLineChart.setMarkerView(mv);
        mLineChart.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.e("dataSetIndex",dataSetIndex+"");

                Log.e("Entry",e.toString());
                int xindex = e.getXIndex();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }
    ////////////////////////////////////////////////////////////////睡眠///////////////////////////////////////
    // 设置显示的样式    
    private void showSleepDayChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);//是否在折线图上添加边框    

        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background    
        lineChart.setDrawGridBackground(false);// 是否显示表格颜色    

        // enable touch gestures    
        lineChart.setTouchEnabled(true);// 设置是否可以触摸    

        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    

        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//   
        //取消legend



        // add data    
        lineChart.setData(lineData);// 设置数据    
    }

    private void ShowSleepDayChart()
    {
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);
        XAxis xl = mLineChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(30f); // 设置字体大小
        xl.setSpaceBetweenLabels(2); // 设置数据之间的间距'

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < 16; i++)
        {
            // x轴显示的数据，这里默认使用数字下标显示    
            xValues.add("" + (i+1));
        }
        // create a dataset and give it a type    
        // y轴的数据集合 
        // y轴的数据    
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for(int i = 0; i< 16; i++)
        {
            float value =(float)(Math.random()* 12/3+7);
            yValues.add(new Entry(value, i));
        }
        LineDataSet lineDataSet = new LineDataSet(yValues, "");
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(2.0f);// 线宽  
        lineDataSet.setCircleSize(4);// 显示的圆形大小      
        lineDataSet.setDrawCubic(true);
        lineDataSet.setCircleColor(Color.parseColor("#1CC1FB"));
        lineDataSet.setDrawHighlightIndicators(true);
        // 圆球的颜色  
        //点击后，十字交叉线的颜色  +
        lineDataSet.setColor(Color.parseColor("#1CC1FB"));// 显示颜色 
        lineDataSet.setHighLightColor(Color.parseColor("#00FFFFFF"));
        //点击后，十字交叉线的颜色  +

        lineDataSet.setValueFormatter(new ValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                            ViewPortHandler viewPortHandler) {
                int num = (int)value;
                return "";
            }
        });

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet);// add the datasets    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);
        showSleepDayChart(mLineChart, lineData, Color.rgb(28, 193,21));
        //隐藏左边坐标轴横网格线
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);// 隐藏右边 的坐标轴
        mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);// 让x轴在下面
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.colorTM));
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setAxisLineColor(Color.parseColor("#1CC1FB"));
        mLineChart.getXAxis().setAxisLineWidth(1f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value,function);
        mLineChart.setMarkerView(mv);
        mLineChart.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        mLineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.e("dataSetIndex",dataSetIndex+"");

                Log.e("Entry",e.toString());
                int xindex = e.getXIndex();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        mLineChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }

}