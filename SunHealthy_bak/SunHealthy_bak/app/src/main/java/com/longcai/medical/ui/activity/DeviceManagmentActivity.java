package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.service.DeviceFragmentFactory;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.utils.LogUtils;


/**
 * 设备管理界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class DeviceManagmentActivity extends BaseActivity implements
        OnClickListener {

    private View view;
    private String TAG = "DeviceManagmentActivity";
   // PagerTab mPagerTab;
    ViewPager mViewPager;

    private MyAdapter mAdapter;
    TextView titleTv, title_ok;
    ImageView returIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题

        initView();
        initData();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }

    }

    public void initView() {
        setContentView(R.layout.device_managment);
        LogUtils.i(TAG, "初始化");
       // mPagerTab = (PagerTab) findViewById(R.id.device_pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.device_viewpager);
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(mAdapter);
        returIv = (ImageView) findViewById(R.id.title_back);
        titleTv = (TextView) findViewById(R.id.title_tv);
        title_ok = (TextView) findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);
        /*mPagerTab.setViewPager(mViewPager);// 将指针和viewpager绑定在一起
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = FragmentFactory
                        .createFragment(position);
                // 开始加载数据

                fragment.LoadData();

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/
        // FragmentFactory.createFragment(0).loadData(); // 手动加载第一条数据

    }

    public void initData() {
        titleTv.setText("设备管理");
        title_ok.setVisibility(View.GONE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }

    /**
     * FragmentPagerAdapter是PagerAdapter的子类, 如果viewpager的页面是fragment的话,就继承此类
     */
    class MyAdapter extends FragmentPagerAdapter {

        private String[] mTabNames;

        public MyAdapter(FragmentManager fm) {
            super(fm);
           // mTabNames = UIUtils.getStringArray(R.array.tab_names);// 加载页面标题数组
        }

        // 返回页签标题
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTabNames[position];
//        }
        // 返回当前页面位置的fragment对象
        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = DeviceFragmentFactory.createFragment(position);
            return fragment;
        }

        // fragment数量
        @Override
        public int getCount() {
            return 2;
        }

    }

}
