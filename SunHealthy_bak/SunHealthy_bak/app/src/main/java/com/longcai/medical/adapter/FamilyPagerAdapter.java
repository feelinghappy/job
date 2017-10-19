package com.longcai.medical.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.longcai.medical.ui.activity.family.FamilyPageFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 * 家庭首页adapter
 */

public class FamilyPagerAdapter extends FragmentPagerAdapter {
    private List<FamilyPageFragment> mList;

    public FamilyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public FamilyPagerAdapter(FragmentManager fm, List<FamilyPageFragment> mList) {
        super(fm);
        this.mList = mList;
    }

    @Override
    public FamilyPageFragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
