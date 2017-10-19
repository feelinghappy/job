package com.longcai.medical.rob.ui;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.rob.bean.AirBox;
import com.longcai.medical.rob.bean.Robot;
import com.longcai.medical.rob.logic.RobotData;
import com.longcai.medical.rob.logic.RobotDataListener;
import com.longcai.medical.rob.logic.RobotInit;
import com.longcai.medical.rob.utils.SharedPrefUtils;
import com.longcai.medical.service.VideoCallService;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.rob.ui.fragment.AlphaTransformer;
import com.longcai.medical.rob.ui.fragment.RobotFragment;
import com.longcai.medical.rob.ui.fragment.RobotViewPagerAdapter;
import com.longcai.medical.zxing.activity.CaptureActivity;


import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author : zbj on 2017/9/16 14:03.
 */

public class RobotHomeActivity extends BaseActivity implements View.OnClickListener, ViewPager
        .OnPageChangeListener {

    private ViewPager mViewPager;
    private LinearLayout mDotContainer;
    private ArrayList<Fragment> mList;
    private List<Robot> robots= new ArrayList<>();
    private Map<String, Robot> robotMap = new HashMap<>();
    private List<String> robotUids = new ArrayList<>();
    private Map<String, DataChangeListener> mapListener = new HashMap<>();
    //private DataChangeListener listener;
    private FrameLayout robotPage;
    private LinearLayout bindLayout;
    private boolean firstLoad = true;

    public interface DataChangeListener{
        void onData(Robot robot);
    }

    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                RobotInit.initRobotsData(robotUids, new RobotDataListener() {
                    @Override
                    public void onRobotSuccess(Map<String, Robot> robots) {
                        robotMap = robots;
                        RobotData.robotMap = robotMap;
                        if(firstLoad){
                            initRobotPage();
                            firstLoad = false;
                        }
                        else{
                            for(String key : robotMap.keySet()){
                                mapListener.get(key).onData(robotMap.get(key));
                            }
                        }
                    }


                    @Override
                    public void onIdsSuccess(List<String> ids) {

                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot_home);

        Intent intent = new Intent(RobotHomeActivity.this, VideoCallService.class);
        startService(intent);

        RobotInit.getRobotList(RobotHomeActivity.this, new RobotDataListener(){
            @Override
            public void onRobotSuccess(Map<String,Robot> robs) {

            }

            @Override
            public void onIdsSuccess(List<String> ids) {
                Log.d("liutao", "onIdsSuccess: " + ids.toString());
                robotUids = ids;
                handler.sendEmptyMessage(0);

            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        findViewById(R.id.toolbar_back).setOnClickListener(this);

        findViewById(R.id.img_link2QRCodeActivity).setOnClickListener(this);
        findViewById(R.id.btn_link2QRCodeActivity).setOnClickListener(this);

        robotPage = (FrameLayout)findViewById(R.id.robot_page);
        bindLayout = (LinearLayout)findViewById(R.id.unbindRobotView);

        if(RobotData.robotMap != null){
            robotMap = RobotData.robotMap;
            initRobotPage();
        }


    }

    private void initRobotPage(){
        Log.d("RobotHomeActivity", "initRobotPage: " + robotMap.entrySet());
        robotPage.setVisibility(View.VISIBLE);
        bindLayout.setVisibility(View.INVISIBLE);
        mViewPager = (ViewPager) findViewById(R.id.robot_viewpager);
        mDotContainer = (LinearLayout) findViewById(R.id.robot_dot_container);
        FrameLayout rootView = (FrameLayout) findViewById(R.id.robot_root_view);
        addLayoutListener(rootView,rootView);

        mViewPager.addOnPageChangeListener(this);

        mList = new ArrayList<>();


        for(String key : robotMap.keySet()){
            RobotFragment fragment = RobotFragment.newInstance(robotMap.get(key));
            mList.add(fragment);
            mapListener.put(key, fragment);
        }

        mDotContainer.removeAllViews();

        for (int i = 0; i < mList.size(); i++) {
            int width;
            int height;
            ImageView ivIndicator = new ImageView(this);
            if (i == 0) {
                width = 20;
                height = 20;
                ivIndicator.setImageResource(R.drawable.robot_checked);
            } else {
                width = 10;
                height = 10;
                ivIndicator.setImageResource(R.drawable.shape_robot_dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            if (i != 0) {
                params.leftMargin = 10;
            }
            mDotContainer.addView(ivIndicator, params);
        }

        int childCount = mDotContainer.getChildCount();
        Log.d("zbj0919", "childCount: " + childCount);

        RobotViewPagerAdapter adapter = new RobotViewPagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1, false);
        mViewPager.setPageMargin(30);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageTransformer(true,new AlphaTransformer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_link2QRCodeActivity:
            case R.id.btn_link2QRCodeActivity:
                Intent intent = new Intent(this, CaptureActivity.class);
                intent.putExtra(Constant.SCAN_MARK, Constant.ROBOT_BUDLING);
                startActivity(intent);
                break;
            case R.id.toolbar_back:
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int pos = position % mList.size();
        for (int i = 0; i < mList.size(); i++) {

            ImageView ivIndicator = (ImageView) mDotContainer.getChildAt(i);

            int width;
            int height;

            ivIndicator.setImageResource(R.drawable.shape_robot_dot_normal);

            if (pos == i) {
                width = 20;
                height = 20;
                ivIndicator.setImageResource(R.drawable.robot_checked);
            } else {
                width = 10;
                height = 10;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, height);
            if (i != 0) {
                params.leftMargin = 10;
            }

            ivIndicator.setLayoutParams(params);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }
}
