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

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by Administrator on 2017/7/25.
 */

public class BirthdayPicker extends PopupWindow implements OnWheelChangedListener, View.OnClickListener, PopupWindow.OnDismissListener {

    private WheelView mYearWheel;
    private WheelView mMonthWheel;
    private WheelView mDayWheel;
    private Activity activity;
    private Button mCancelBtn;
    private Button mOKBtn;
    private IBirthdayPicker iBirthdayPicker;
    private TextView mTitleTxt;

    public void setIBirthdayPicker(IBirthdayPicker iBirthdayPicker) {
        this.iBirthdayPicker = iBirthdayPicker;
    }

    public BirthdayPicker(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_area, null);
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

        mYearWheel = (WheelView) conentView.findViewById(R.id.id_province);
        mMonthWheel = (WheelView) conentView.findViewById(R.id.id_city);
        mDayWheel = (WheelView) conentView.findViewById(R.id.id_area);
        mCancelBtn = (Button) conentView.findViewById(R.id.btnCancel);
        mCancelBtn.setOnClickListener(this);
        mOKBtn = (Button) conentView.findViewById(R.id.btnSubmit);
        mOKBtn.setOnClickListener(this);
        mTitleTxt = (TextView) conentView.findViewById(R.id.tvTitle);
        activity = context;

        mYearWheel.setViewAdapter(new NumericWheelAdapter(activity, 1900, 2018, "%d 年"));
        mYearWheel.setCurrentItem(90);
        mMonthWheel.setViewAdapter(new NumericWheelAdapter(activity, 1, 12, "%d 月"));
        mMonthWheel.setCurrentItem(0);
        mDayWheel.setViewAdapter(new NumericWheelAdapter(activity, 1, 31, "%d 日"));
        mDayWheel.setCurrentItem(0);
        mYearWheel.addChangingListener(this);
        mMonthWheel.addChangingListener(this);
        mDayWheel.addChangingListener(this);

        mYearWheel.setVisibleItems(7);
        mMonthWheel.setVisibleItems(7);
        mDayWheel.setVisibleItems(7);
    }

    public void setTitle(String title) {
        this.mTitleTxt.setText(title);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
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
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mYearWheel) {
            resetDaysWheel();
        } else if (wheel == mMonthWheel) {
            resetDaysWheel();
        }
//        else if (wheel == mDayWheel) {
//
//        }
    }

    private void resetDaysWheel() {
        int currentYear = mYearWheel.getCurrentItem() + 1900;
        int currentMonth = mMonthWheel.getCurrentItem();
        int days = getDaysOfMonth(currentYear, currentMonth);
        mDayWheel.setViewAdapter(new NumericWheelAdapter(activity, 1, days, "%d 日"));
        mDayWheel.setCurrentItem(0);
    }

    private int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DATE);
    }

    @Override
    public void onClick(View view) {
        if (view == mCancelBtn) {
            dismiss();
            removeListener();
        } else if (view == mOKBtn) {
            if (iBirthdayPicker != null) {
                int currentYear = mYearWheel.getCurrentItem() + 1900;
                int currentMonth = mMonthWheel.getCurrentItem() + 1;
                int currentDay = mDayWheel.getCurrentItem() + 1;
                String dateStr = currentYear + "年" + currentMonth + "月" + currentDay + "日";
                String date = currentYear + "-" + getDecimal(currentMonth) + "-" + getDecimal(currentDay);
                iBirthdayPicker.birthdayPicker(dateStr, date);
            }
            dismiss();
            removeListener();
        }
    }

    private String getDecimal(int num) {
        String decimal;
        if (num / 10 >= 1) {
            decimal = String.valueOf(num);
        } else {
            decimal = "0" + num;
        }
        return decimal;
    }
    private void removeListener() {
        mYearWheel.removeChangingListener(this);
        mMonthWheel.removeChangingListener(this);
        mDayWheel.removeChangingListener(this);
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface IBirthdayPicker {
        void birthdayPicker(String dateStr, String date);
    }
}
