package com.longcai.medical.ui.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.utils.UIUtils;

import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by Administrator on 2017/8/3.
 */

public class TimePicker extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    private kankan.wheel.widget.WheelView mHourWheel;
    private kankan.wheel.widget.WheelView mMinuteWheel;
    private Activity activity;
    private Button mCancelBtn;
    private Button mOKBtn;
    private ITimePicker iTimePicker;
    private TextView mTitleTxt;

    public TimePicker(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_time, null);
//        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(w);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
//        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);

        mHourWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_hour);
        mMinuteWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_minute);
        mCancelBtn = (Button) conentView.findViewById(R.id.btnCancel);
        mCancelBtn.setOnClickListener(this);
        mOKBtn = (Button) conentView.findViewById(R.id.btnSubmit);
        mOKBtn.setOnClickListener(this);
        mTitleTxt = (TextView) conentView.findViewById(R.id.tvTitle);
        activity = context;

        NumericWheelAdapter hourAdapter = new NumericWheelAdapter(activity, 0, 23, "%d 时 ");
        mHourWheel.setViewAdapter(hourAdapter);
        mHourWheel.setVisibleItems(7);

        NumericWheelAdapter minuteAdapter = new NumericWheelAdapter(activity, 0, 59, "%d 分 ");
        mMinuteWheel.setViewAdapter(minuteAdapter);
        mMinuteWheel.setVisibleItems(7);
    }

    public void setTitle(String title) {
        this.mTitleTxt.setText(title);
    }

    public void setITimePicker(ITimePicker iTimePicker) {
        this.iTimePicker = iTimePicker;
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            UIUtils.backgroundAlpha(activity, 0.6f);
            this.setOnDismissListener(this);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mCancelBtn) {
            dismiss();
        } else if (view == mOKBtn) {
            int hourItem = mHourWheel.getCurrentItem();
            int minuteItem = mMinuteWheel.getCurrentItem();
            if (null != iTimePicker) {
                iTimePicker.timePicker(converDigit(hourItem) + ":" + converDigit(minuteItem));
            }
            dismiss();
        }
    }

    private String converDigit(int digit) {
        String result = "";
        if (digit / 10 == 0 ) {
            result = "0" + String.valueOf(digit);
        } else if (digit / 10 > 0) {
            result = String.valueOf(digit);
        }
        return result;
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface ITimePicker {
        void timePicker(String time);
    }
}
