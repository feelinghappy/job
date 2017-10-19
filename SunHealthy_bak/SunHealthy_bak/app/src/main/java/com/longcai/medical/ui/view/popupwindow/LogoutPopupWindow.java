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
 * Created by Administrator on 2017/8/3.
 */

public class LogoutPopupWindow extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    private Activity activity;
    private TextView cancelBtn;
    private TextView logoutBtn;
    private ILogout iLogout;

    public LogoutPopupWindow(final Activity context) {
        super(context);
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.dialog_text_view, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(h);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
//        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview);

        cancelBtn = (TextView) conentView.findViewById(R.id.negativeButton);
        logoutBtn = (TextView) conentView.findViewById(R.id.positiveButton);

        cancelBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
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
        } else if (view == logoutBtn) {
            if (null != iLogout) {
                iLogout.logout();
            }
            dismiss();
        }
    }

    public interface ILogout {
        void logout();
    }

    public void setILogout(ILogout iLogout) {
        this.iLogout = iLogout;
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }
}
