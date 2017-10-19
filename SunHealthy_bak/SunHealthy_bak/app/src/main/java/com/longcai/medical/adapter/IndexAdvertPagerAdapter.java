package com.longcai.medical.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.longcai.medical.ui.view.HealthDataView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class IndexAdvertPagerAdapter extends PagerAdapter {
    private List<ImageView> views;

    public IndexAdvertPagerAdapter(List<ImageView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
