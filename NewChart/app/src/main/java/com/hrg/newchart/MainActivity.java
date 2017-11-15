package com.hrg.newchart;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity {
    private LineChart mLineChart;
    private TextView chart_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        hideVirtualKey();
        hideNavigationBar();
        mLineChart = (LineChart) findViewById(R.id.chart);
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

}
