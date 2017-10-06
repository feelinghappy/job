package com.hrg.anew;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
public class tietlelayout extends LinearLayout
{
    public tietlelayout(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
        LayoutInflater.from(paramContext).inflate(R.layout.title, this);
        ((Button)findViewById(R.id.title_back)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                ((Activity)tietlelayout.this.getContext()).finish();
            }
        });
    }
}