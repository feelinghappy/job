package com.longcai.medical.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.zhy.autolayout.AutoRelativeLayout;

/**
 * Created by Administrator on 2017/8/7.
 */

public class HealthDataView extends AutoRelativeLayout {
    private ImageView ivData;
    private TextView tvName;
    private TextView tvValue;
    private TextView tvUnit;

    public HealthDataView(Context context) {
        this(context, null);
    }

    public HealthDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HealthDataView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_index_healthdata, this, true);
        ivData = (ImageView) view.findViewById(R.id.iv_health_data);
        tvName = (TextView) view.findViewById(R.id.tv_health_name);
        tvValue = (TextView) view.findViewById(R.id.tv_health_value);
        tvUnit = (TextView) view.findViewById(R.id.tv_health_unit);
    }

    public void setIcon(int resId) {
        ivData.setImageResource(resId);
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setValue(String value) {
        tvValue.setText(value);
    }

    public void setUnit(String unit) {
        tvUnit.setText(unit);
    }
}
