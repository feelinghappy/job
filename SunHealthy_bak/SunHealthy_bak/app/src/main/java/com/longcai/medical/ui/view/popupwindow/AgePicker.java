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

public class AgePicker extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    private kankan.wheel.widget.WheelView mContentWheel;
    private Activity activity;
    private Button mCancelBtn;
    private Button mOKBtn;
    private IAgePicker iAgePicker;
    private TextView mTitleTxt;

    public AgePicker(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_profession_medical, null);
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

        mContentWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_content);
        mCancelBtn = (Button) conentView.findViewById(R.id.btnCancel);
        mCancelBtn.setOnClickListener(this);
        mOKBtn = (Button) conentView.findViewById(R.id.btnSubmit);
        mOKBtn.setOnClickListener(this);
        mTitleTxt = (TextView) conentView.findViewById(R.id.tvTitle);
        activity = context;

        NumericWheelAdapter numericAdapter = new NumericWheelAdapter(activity, 1, 120);
        mContentWheel.setViewAdapter(numericAdapter);
        mContentWheel.setCurrentItem(20);
        mContentWheel.setVisibleItems(7);
    }

    public void setTitle(String title) {
        this.mTitleTxt.setText(title);
    }

    public void setIAgePicker(IAgePicker iAgePicker) {
        this.iAgePicker = iAgePicker;
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
            int selectedItem = mContentWheel.getCurrentItem();
            if (null != iAgePicker) {
                iAgePicker.agePicker(selectedItem + 1);
            }
            dismiss();
        }
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface IAgePicker {
        void agePicker(int item);
    }
}
