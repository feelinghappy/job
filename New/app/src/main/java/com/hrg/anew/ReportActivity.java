package com.hrg.anew;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class ReportActivity extends Activity {
    private LineChart mLineChart;
    private TextView chart_value;
    private TextView report_title_1;
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
    private String  function;
    private String  time_select;
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
    Message msg = new Message();

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
        report_select_1 = (TextView)findViewById(R.id.report_select_1);
        report_select_2 = (TextView)findViewById(R.id.report_select_2);
        report_select_3 = (TextView)findViewById(R.id.report_select_3);
        report_title_1 = (TextView)findViewById(R.id.report_title_1);
        report_title_2 = (TextView)findViewById(R.id.report_title_2);
        report_title_3 = (TextView)findViewById(R.id.report_title_3);
        report_value_1 = (TextView)findViewById(R.id.report_value_1);
        report_value_2 = (TextView)findViewById(R.id.report_value_2);
        report_value_3 = (TextView)findViewById(R.id.report_value_3);
        report_value_uint_1 = (TextView)findViewById(R.id.report_value_unit_1);
        report_value_uint_2 = (TextView)findViewById(R.id.report_value_unit_2);
        report_value_uint_3 = (TextView)findViewById(R.id.report_value_unit_3);
        report_img1 = (ImageView)findViewById(R.id.report_img1);
        report_img2 = (ImageView)findViewById(R.id.report_img2);
        report_img3 = (ImageView)findViewById(R.id.report_img3);
        mLineChart = (LineChart) findViewById(R.id.chart);
        //通过findViewById获得RadioGroup对象  
        RadioGroup raGrouphis =(RadioGroup)findViewById(R.id.history);
        //添加事件监听器  
        raGrouphis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if(checkedId == R.id.btn_0)
            {
               // LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
               // layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.xueya));
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

            }
            else if(checkedId == R.id.btn_1)
            {
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
            }
            else if(checkedId == R.id.btn_2)
            {
                //LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                //layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.sport));
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
            }
            else if(checkedId == R.id.btn_3)
            {
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
            }

           }
         }
        );
        report_select_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_1.setTextColor(getResources().getColor(R.color.colorChart));
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img1.setVisibility(View.VISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "day";
                new Thread(new Runnable() {
                    public void run() {
                        if (function.equals("blood")) {
                            msg.what = UPDATE_BLOOD_DAY;
                            handler.sendMessage(msg);
                        } else if (function.equals("rate")) {
                            msg.what = UPDATE_RATE_DAY;
                            handler.sendMessage(msg);
                        } else if (function.equals("walk")) {
                            msg.what = UPDATE_WALK_DAY;
                            handler.sendMessage(msg);
                        } else if (function.equals("sleep")) {
                            msg.what = UPDATE_SLEEP_DAY;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();
            }
        });
        report_select_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_2.setTextColor(getResources().getColor(R.color.colorChart));
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_3.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img2.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img3.setVisibility(View.INVISIBLE);
                time_select = "month";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(function.equals("blood"))
                        {
                            msg.what = UPDATE_BLOOD_MONTH;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("rate"))
                        {
                            msg.what = UPDATE_RATE_MONTH;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("walk"))
                        {
                            msg.what = UPDATE_WALK_MONTH;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("sleep"))
                        {
                            msg.what = UPDATE_SLEEP_MONTH;
                            handler.sendMessage(msg);
                        }
                    }
                }).start();


            }
        });
        report_select_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_select_3.setTextColor(getResources().getColor(R.color.colorChart));
                report_select_2.setTextColor(getResources().getColor(R.color.colorBlack));
                report_select_1.setTextColor(getResources().getColor(R.color.colorBlack));
                report_img3.setVisibility(View.VISIBLE);
                report_img1.setVisibility(View.INVISIBLE);
                report_img2.setVisibility(View.INVISIBLE);
                time_select = "year";
                new Thread(new Runnable() {
                    public void run()
                    {
                        if(function.equals("blood"))
                        {
                            msg.what = UPDATE_BLOOD_YEAR;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("rate"))
                        {
                            msg.what = UPDATE_RATE_YEAR;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("walk"))
                        {
                            msg.what = UPDATE_WALK_YEAR;
                            handler.sendMessage(msg);
                        }
                        else if(function.equals("sleep"))
                        {
                            msg.what = UPDATE_SLEEP_YEAR;
                            handler.sendMessage(msg);
                        }

                    }
                }).start();


            }
        });



    }
    private void ShowHeartRateChart()
    {
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);


        XAxis xl = mLineChart.getXAxis();
        xl.setPosition(XAxisPosition.BOTTOM); // 设置X轴的数据在底部显示
        xl.setTextSize(30f); // 设置字体大小
        xl.setSpaceBetweenLabels(0); // 设置数据之间的间距'

        LineData mLineData = getLineData(36, 100);
        showChart(mLineChart, mLineData, Color.rgb(255, 105,62));
        //隐藏左边坐标轴横网格线
        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getAxisRight().setEnabled(false);// 隐藏右边 的坐标轴
        mLineChart.getXAxis().setPosition(XAxisPosition.BOTTOM);// 让x轴在下面
        mLineChart.getXAxis().setGridColor(getResources().getColor(R.color.colorTM));
        mLineChart.getLegend().setEnabled(false);
        mLineChart.getXAxis().setAxisLineColor(Color.parseColor("#FF693E"));
        mLineChart.getXAxis().setAxisLineWidth(2f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value);
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

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BLOOD_DAY:
                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATE_RATE_DAY:
                    try {
                        Log.e("UPDATE_RATE_DAY","UPDATE_RATE_DAY");
                        ShowHeartRateChart();
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
        lineDataSet.setCircleSize(0);// 显示的圆形大小       
        lineDataSet.setDrawCubic(true);
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
        mLineChart.getXAxis().setAxisLineWidth(2f);
        mLineChart.getAxisLeft().setEnabled(false);
        mLineChart.setDragEnabled(true);// 是否可以拖拽  
        mLineChart.setScaleEnabled(true);// 是否可以缩放 x和y轴, 默认是true  
        mLineChart.setScaleXEnabled(true);//是否可以缩放 仅x轴  
        mLineChart.setScaleYEnabled(true);//是可以缩放 仅y轴 
        mLineChart.setDoubleTapToZoomEnabled(true);////  
        // 设置MarkerView
        MarkerView mv = new MyMarkerView(this,R.layout.markerview_value);
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