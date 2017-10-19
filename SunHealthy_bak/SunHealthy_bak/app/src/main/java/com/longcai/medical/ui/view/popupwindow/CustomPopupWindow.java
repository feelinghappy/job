package com.longcai.medical.ui.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.utils.UIUtils;

/**
 * Created by Administrator on 2017/8/16.
 */

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    private Activity activity;
    private TextView cancelBtn;
    private TextView okBtn;
    private TextView titleView;
    private IOKBack iOKBack;

    public CustomPopupWindow(final Activity context) {
        super(context);
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popop_delete_member, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
//        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);

        cancelBtn = (TextView) conentView.findViewById(R.id.delete_cancel);
        okBtn = (TextView) conentView.findViewById(R.id.delete_ok);
        titleView = (TextView) conentView.findViewById(R.id.tv_title);

        cancelBtn.setOnClickListener(this);
        okBtn.setOnClickListener(this);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
            UIUtils.backgroundAlpha(activity, 0.6f);
            this.setOnDismissListener(this);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == cancelBtn) {
            dismiss();
        } else if (view == okBtn) {
            if (null != iOKBack) {
                iOKBack.okBack();
            }
            dismiss();
        }
    }

    public interface IOKBack {
        void okBack();
    }

    public void setIOKBack(IOKBack iLogout) {
        this.iOKBack = iLogout;
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }
}
