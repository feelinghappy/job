package com.longcai.medical.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.R;
import com.longcai.medical.adapter.ClassAdapter;
import com.longcai.medical.bean.MineRest;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.MyTimePickerView;
import com.longcai.medical.utils.AlarmManagerUtil;
import com.longcai.medical.utils.DbUtils;
import com.longcai.medical.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑作息
 */
public class EditRestActivity extends BaseActivity {


    @BindView(R.id.bank_btn)
    RelativeLayout bankBtn;
    @BindView(R.id.title_container)
    RelativeLayout titleContainer;
    @BindView(R.id.time_picker_tx1)
    MyTimePickerView timePickerTx1;
    @BindView(R.id.time_picker_tx2)
    MyTimePickerView timePickerTx2;
    @BindView(R.id.time_container)
    RelativeLayout timeContainer;
    @BindView(R.id.class_tx1)
    TextView classTx1;
    @BindView(R.id.fl_tx1)
    RelativeLayout flTx1;
    @BindView(R.id.class_tx2)
    TextView classTx2;
    @BindView(R.id.fl_tx2)
    RelativeLayout flTx2;
    @BindView(R.id.class_tx3)
    TextView classTx3;
    @BindView(R.id.fl_tx3)
    RelativeLayout flTx3;
    @BindView(R.id.class_tx4)
    TextView classTx4;
    @BindView(R.id.fl_tx4)
    RelativeLayout flTx4;
    @BindView(R.id.position01)
    LinearLayout position01;
    @BindView(R.id.class_tx5)
    EditText classTx5;
    @BindView(R.id.fl_tx5)
    RelativeLayout flTx5;
    @BindView(R.id.zuoxiang_xq)
    LinearLayout zuoxiangXq;
    @BindView(R.id.quxiao_btn)
    RelativeLayout quxiaoBtn;
    @BindView(R.id.tijiao_btn)
    RelativeLayout tijiaoBtn;
    @BindView(R.id.activity_edit_rest)
    RelativeLayout activityEditRest;
    private int status = -1;

    private String hour;
    private String minute;
    private int edit;
    //数据查看
    private String shour="";
    private String smin="";
    private String stext="";
    //数据库
    private String DB_NAME = "minerest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_rest);
        ButterKnife.bind(this);
        edit=getIntent().getIntExtra("type",0);
        shour=getIntent().getStringExtra("hour");
        smin=getIntent().getStringExtra("min");
        stext=getIntent().getStringExtra("text");

        setTimeData();

    }


    private List<String> data = new ArrayList<>();


    @Override
    protected void onStop() {
        super.onStop();
        //   vibrator.cancel();
    }
    //搜索的东西
    private String CURRENTDATE="";
    List<MineRest> mineRestsBoo;
    Calendar calendar=Calendar.getInstance();


    private void setTimeData() {
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            data.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++) {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        if (edit==-1){
            //添加闹钟
            DbUtils.createDb(this, DB_NAME);
            CURRENTDATE="all";
            mineRestsBoo=DbUtils.getQueryByWhere(MineRest.class,"text",new String[]{CURRENTDATE});
            hour=""+calendar.get(Calendar.HOUR_OF_DAY);
            minute=""+calendar.get(Calendar.MINUTE);
            timePickerTx1.setData(data,"1","0");
            timePickerTx2.setData(seconds,"1","0");
        }else {
            //添加闹钟
            DbUtils.createDb(this, DB_NAME);
            CURRENTDATE=stext;
            mineRestsBoo=DbUtils.getQueryByWhere(MineRest.class,"title",new String[]{CURRENTDATE});
            for (int i=0;i<mineRestsBoo.size();i++){
                if (mineRestsBoo.get(i).getHour().equals(shour)&&mineRestsBoo.get(i).getMinute().equals(smin)){
                    Log.d("1555","接收"+shour+"---"+smin+"---"+stext);
                    hour=""+mineRestsBoo.get(i).getHour();
                    minute=""+mineRestsBoo.get(i).getMinute();
                    timePickerTx1.setData(data,"2",hour);
                    timePickerTx2.setData(seconds,"2",minute);
                    classTx1.setText(mineRestsBoo.get(i).getTitle());
                    if (mineRestsBoo.get(i).getFlag().equals("0")){
                        classTx3.setText("永不");
                    }else {
                        classTx3.setText("每天");
                    }
                    if (mineRestsBoo.get(i).getType().equals("0")){
                        classTx4.setText("铃声");
                    }else if (mineRestsBoo.get(i).getType().equals("1")){
                        classTx4.setText("振动");
                    }else {
                        classTx4.setText("铃声振动");
                    }

                    classTx5.setText(mineRestsBoo.get(0).getContent());
                }
            }

        }

        timePickerTx1.setOnSelectListener(new MyTimePickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
//                Toast.makeText(EditRestActivity.this, "选择了 " + text + " 时",
//                        Toast.LENGTH_SHORT).show();
                hour=text;
            }
        });

        timePickerTx2.setOnSelectListener(new MyTimePickerView.onSelectListener() {

            @Override
            public void onSelect(String text) {
//                Toast.makeText(EditRestActivity.this, "选择了 " + text + " 分",
//                        Toast.LENGTH_SHORT).show();
                minute=text;
            }
        });
    }
    @OnClick({R.id.bank_btn, R.id.fl_tx1,
            R.id.fl_tx3, R.id.fl_tx4, R.id.fl_tx2, R.id.quxiao_btn, R.id.tijiao_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_btn:
                this.finish();
                break;
            case R.id.fl_tx1:
                //下来框
                status = 1;
                setPop1(flTx1,"1");
                break;
            case R.id.fl_tx2:
                status = 2;
                setPop1(flTx3,"");
                break;
            case R.id.fl_tx3:
                status = 3;
                setPop1(flTx3,"3");

                break;
            case R.id.fl_tx4:
                status = 4;
                setPop1(flTx4,"4");
                break;
            case R.id.quxiao_btn:
                break;
            case R.id.tijiao_btn:
                //判断
                if(classTx1.getText().toString().equals(getString(R.string.rest_text1))){
                    ToastUtils.show(EditRestActivity.this,getString(R.string.rest_text1));
                    return;
                }
                if(classTx3.getText().toString().equals(getString(R.string.rest_text2))){
                    ToastUtils.show(EditRestActivity.this,getString(R.string.rest_text2));
                    return;
                }
                if(classTx4.getText().toString().equals(getString(R.string.rest_text3))){
                    ToastUtils.show(EditRestActivity.this,getString(R.string.rest_text3));
                    return;
                }

                myalarm();
                break;
        }
    }
    private PopupWindow productWindow;
    String type,id,flag;

    private RecyclerView classRecyclerView;
    private ClassAdapter classAdapter;

    private void setPop1(RelativeLayout view,String type) {
        data.clear();
        //TODO 添加判断文字
        if (type.equals("1")){
            data.add("起床");
            data.add("午休");
            data.add("晚睡");
            data.add("吃药");
            data.add("茶饮");
            data.add("就餐");
            data.add("水果");
            data.add("购物");
            data.add("锻炼");
            data.add("出行");
        }else if (type.equals("3")){
            data.add("永不");
            data.add("每天");
        }else if (type.equals("4")){
            data.add("铃声");
            data.add("振动");
            data.add("铃声振动");
        }
//        for (int i = 0; i < 8; i++) {
//            data.add("数据" + i);
//        }
        View childView = LayoutInflater.from(this).inflate(R.layout.child_view, null);
//        MyApplication.ScaleScreenHelper.loadView((ViewGroup) childView);
        setPopData(childView, data);
        productWindow = new PopupWindow(childView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        productWindow.setTouchable(true);
        productWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        productWindow.setBackgroundDrawable(new BitmapDrawable());
        productWindow.showAsDropDown(view);
        productWindow.setAnimationStyle(R.style.Animation_CustomPopup);
    }
    private void setPopData(View childView, List<String> data) {
        classRecyclerView = (RecyclerView) childView.findViewById(R.id.classRecyclerView);
        classRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        classAdapter = new ClassAdapter(this);
        classRecyclerView.setAdapter(classAdapter);
        classRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration
                .VERTICAL_LIST, R.drawable.class_bkg));
        classAdapter.setData(data);
        classAdapter.setOnItemClickLitener(new ClassAdapter.ItemClickListener() {
            @Override
            public void OnClick(String id, String tx) {
                switch (status) {
                    case 1:
                        classTx1.setText(tx);
                        break;
                    case 2:
                        classTx2.setText(tx);
                        break;
                    case 3:
                        classTx3.setText(tx);
                        break;
                    case 4:
                        classTx4.setText(tx);
                        break;
                }
                productWindow.dismiss();
            }
        });
    }
    private void myalarm() {
        DbUtils.createDb(this, DB_NAME);
        List<MineRest> mineRestsBoo=DbUtils.getQueryByWhere(MineRest.class,"restboolean",new String[]{"true"});
        MineRest data = new MineRest();
        id=""+calendar.getTimeInMillis();
        if (mineRestsBoo.size()==0 || mineRestsBoo.isEmpty()){
            data.setRestboolean(true);
        }else {
//            MineRest data = new MineRest();
            data.setRestboolean(true);
//            data.setTime(hour+"-"+minute);
//            data.setHour(hour);
//            data.setMinute(minute);
//            data.setStr("all");
//            data.setTitle(classTx2.getText().toString());
//            data.setContent(classTx5.getText().toString());
//            DbUtils.insert(data);
//            EditRestActivity.this.finish();
            Log.d("1555","数据库插入"+data.getTime()+data.isRestboolean());
//            for (int i=0;i<mineRestsBoo.size();i++){
//                if (mineRestsBoo.get(i).getTime().equals(hour+"-"+minute)){
//                    ToastUtils.show(EditRestActivity.this,"已经添加这个闹钟");
//                    Log.d("1555","已经添加这个闹钟");
//                }else {
//
//                    return;
//                }
//
//            }
        }
        data.setTime(""+calendar.getTimeInMillis());
        data.setHour(hour);
        data.setMinute(minute);
        data.setStr("all");
        //标题
        data.setTitle(classTx1.getText().toString());
        //内容
        data.setContent(classTx5.getText().toString());

        if (classTx3.getText().toString().equals("每天")){
            data.setWeek("0");
            data.setFlag("1");
            flag="1";
        }else if (classTx3.getText().toString().equals("永不")){
            data.setWeek("0");
            data.setFlag("0");
            flag="0";
        }

        if (classTx4.getText().toString().equals("铃声")){
            data.setType("1");
            type="1";
        }else if (classTx4.getText().toString().equals("振动")){
            data.setType("0");
            type="0";
        }else if (classTx4.getText().toString().equals("铃声振动")){
            data.setType("2");
            type="2";
        }
        Log.d("1555","数据库插入"+data.getId());
        if (edit==-1){
            DbUtils.insert(data);
        }else {
            data.setId(edit);
            Log.d("1555","修改"+edit);
            Log.d("1555","修改"+mineRestsBoo.get(edit).getId());
            Log.d("1555","修改"+data.getId());
            CURRENTDATE=""+mineRestsBoo.get(edit).getId();
            DbUtils.deleteWhere(MineRest.class,"id",new String[]{CURRENTDATE});
            DbUtils.insert(data);
        }
//        myrest();
        //TODO
        /**
         *
         */
        if (calendar.get(Calendar.HOUR_OF_DAY)<=Integer.parseInt(hour)&&
                calendar.get(Calendar.MINUTE)<=Integer.parseInt(minute)){
            AlarmManagerUtil.setAlarm(EditRestActivity.this,
                    Integer.parseInt(flag),
                    Integer.parseInt(hour),
                    Integer.parseInt(minute),
                    id,
                    0,
                    classTx1.getText().toString(),
                    Integer.parseInt(type),true);
        }

        EditRestActivity.this.finish();
    }
}
