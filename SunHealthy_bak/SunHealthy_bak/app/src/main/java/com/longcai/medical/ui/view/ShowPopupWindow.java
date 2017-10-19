package com.longcai.medical.ui.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.longcai.medical.R;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ShowPopupWindow {
    public Activity activity;

    public ShowPopupWindow(Activity activity) {
        this.activity = activity;
    }

    private PopupWindow popWindow;

    public PopupWindow showPopup(View view) {

        backgroundAlpha(0.5f);
        if (popWindow == null) {
            int w = activity.getWindowManager().getDefaultDisplay().getWidth();
            popWindow = new PopupWindow();
            popWindow.setContentView(view);
            popWindow.setWidth(w);
            popWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            // 点击事件
            // initPop(view);
        }
        popWindow.setTouchable(true);
        // 获取popwindow焦点
        popWindow.setFocusable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popWindow.setOutsideTouchable(true);
        popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popWindow.update();
        // 实现软键盘不自动弹出,ADJUST_RESIZE属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
//        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.setAnimationStyle(R.style.AnimationPreview);
//        popWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        return popWindow;
    }

    /**
     * 设置popup的背景 半透明
     *
     * @param bgAlpha //0.0f-1.0f
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    public void setDismiss() {
        popWindow.dismiss();
    }

}
