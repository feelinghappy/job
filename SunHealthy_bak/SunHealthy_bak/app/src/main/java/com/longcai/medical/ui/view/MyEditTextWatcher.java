package com.longcai.medical.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.longcai.medical.R;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MyEditTextWatcher implements TextWatcher {
    private EditText editText = null;
    private TextView titleRightTv = null;
    private final Resources resources;

    public MyEditTextWatcher(TextView titleRightTv,Context context) {
        this.editText = editText;
        this.titleRightTv = titleRightTv;
        resources = context.getResources();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        titleRightTv.setTextColor(resources.getColor(R.color.light_gray));
        titleRightTv.setClickable(false);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        titleRightTv.setTextColor(resources.getColor(R.color.black));
        titleRightTv.setClickable(true);
    }

    @Override
    public void afterTextChanged(Editable s) {
        titleRightTv.setTextColor(resources.getColor(R.color.black));
        titleRightTv.setClickable(true);
    }
}
