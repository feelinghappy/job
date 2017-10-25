package com.hrg.robotproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * author : zbj on 2017/9/16 14:14.
 */

public class RobotViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public RobotViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {

        return mList.get(position);
    }

    @Override
    public int getCount() {

        return mList == null ? 0 : mList.size();
    }


}