package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class CustomerPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitle ;
    private List<Fragment> mViewList;
    private Context context;

    public CustomerPagerAdapter(FragmentManager fm,Context context, List<Fragment> mViewList,String[] mTitle) {
        super(fm);
        this.context = context;
        this.mViewList = mViewList;
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}

