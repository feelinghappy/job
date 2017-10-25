package com.hrg.robotproject.ui.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * author : zbj on 2017/9/19 19:34.
 */

public class AlphaTransformer implements ViewPager.PageTransformer {

    private static final float MIN_ALPHA = 0.5f;

    private static final String TAG = "AlphaTransformer";
    @Override
    public void transformPage(View page, float position) {
        Log.d(TAG, "transformPage,position: " + position);
        if (position < -1 || position > 1) {
            page.setAlpha(MIN_ALPHA);
        } else {
            //不透明->半透明
            if (position < 0) {//[0,-1]
                page.setAlpha(MIN_ALPHA + (1 + position) * (1 - MIN_ALPHA));
            } else {//[1,0]
                //半透明->不透明
                page.setAlpha(MIN_ALPHA + (1 - position) * (1 - MIN_ALPHA));
            }
        }
    }
}
