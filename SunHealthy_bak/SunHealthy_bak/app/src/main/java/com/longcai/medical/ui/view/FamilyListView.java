package com.longcai.medical.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/8/16.
 * FamilyManagerActity成员列表
 */

public class FamilyListView extends ListView {
    public FamilyListView(Context context) {
        super(context);
    }

    public FamilyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FamilyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
