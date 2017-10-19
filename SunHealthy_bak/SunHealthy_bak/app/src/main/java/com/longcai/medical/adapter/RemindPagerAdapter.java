package com.longcai.medical.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.longcai.medical.R;
import com.longcai.medical.utils.UIUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class RemindPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabNames;
    private List<Fragment> list_fragment;

    public RemindPagerAdapter(FragmentManager fm, List<Fragment> list_fragment) {
        super(fm);
        mTabNames = UIUtils.getStringArray(R.array.tab_remind_names);// 加载页面标题数组
        this.list_fragment = list_fragment;
    }

    // 返回页签标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }

    // 返回当前页面位置的fragment对象
    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    // fragment数量
    @Override
    public int getCount() {
        return mTabNames.length;
    }

}
