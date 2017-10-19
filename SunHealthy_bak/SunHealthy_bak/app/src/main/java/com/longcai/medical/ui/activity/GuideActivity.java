package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private Button welcomeBtn;
    private List<ImageView> imageList;
    private int[] ims;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        setContentView(R.layout.activity_guide);
        initView();
    }

    //初始化控件
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
        welcomeBtn = (Button) findViewById(R.id.welcome_btn);
        welcomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSplash();
            }
        });
        initData();
        initCtrl();
        welcomeBtn.setClickable(false);
        viewPager.addOnPageChangeListener(this);
    }

    private void initCtrl() {
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return ims.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(imageList.get(position));
                return imageList.get(position);
            }
        };
        viewPager.setAdapter(adapter);
    }

    private void initData() {
        imageList = new ArrayList<ImageView>();
        ims = new int[]{R.drawable.splash1, R.drawable.splash2, R.drawable.splash3};
        for (int i = 0; i < ims.length; i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(ims[i]).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageList.add(imageView);
        }
    }

    private void startSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position < imageList.size() - 1) {
            welcomeBtn.setClickable(false);
            welcomeBtn.setVisibility(View.GONE);
        } else {
            welcomeBtn.setClickable(true);
            welcomeBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(this);
    }
}
