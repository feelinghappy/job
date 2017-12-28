package com.hrg.anew;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class MyMarkerView extends MarkerView {
    private TextView mContentTv;
    private String function;

    public MyMarkerView(Context context, int layoutResource,String Repfunction) {
        super(context, layoutResource);
        mContentTv = (TextView) findViewById(R.id.tv_content_marker_view);
        function = Repfunction;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        Log.e("refreshContent",e.toString());
        if(function.equals("blood")) {
            mContentTv.setText(Math.round(e.getVal()) + "mmgh");
        }
        else if(function.equals("rate"))
        {
            mContentTv.setText(Math.round(e.getVal()) + "bpm");
        }
        else if(function.equals("walk"))
        {
            mContentTv.setText(Math.round(e.getVal()) + "²½");
        }
        else
        {
            mContentTv.setText(Math.round(e.getVal()) + "h");
        }
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        Log.e("ypos",ypos+"");
        Log.e("getHeight",getHeight()+"");
        return  -(getHeight() + 8);

    }
}