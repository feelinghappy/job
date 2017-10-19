package com.longcai.medical.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.R;


/**
 * 版权所有：XXX有限公司
 * <p/>
 * SelectPicturePopupWindow
 *
 * @author zhou.wenkai ,Created on 2016-5-5 12:49:29
 *         Major Function：<b>图片选择PopupWindow</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */
public class SelectPicturePopupWindow extends PopupWindow implements View.OnClickListener {

    private TextView takePhotoBtn, pickPictureBtn, cancelBtn;
    private View mMenuView;
    private PopupWindow popupWindow;
    private OnSelectedListener mOnSelectedListener;

    public SelectPicturePopupWindow(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.person_information_pop, null);
        takePhotoBtn = (TextView) mMenuView.findViewById(R.id.person_information_pop_text1);
        pickPictureBtn = (TextView) mMenuView.findViewById(R.id.person_information_pop_text2);
        cancelBtn = (TextView) mMenuView.findViewById(R.id.person_information_pop_text3);
        // 设置按钮监听
        takePhotoBtn.setOnClickListener(this);
        pickPictureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    /**
     * 把一个View控件添加到PopupWindow上并且显示
     *
     * @param activity
     */
    public void showPopupWindow(final Activity activity) {
        if (null != popupWindow && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        backgroundAlpha(0.5f, activity);
        popupWindow = new PopupWindow(mMenuView,    // 添加到popupWindow
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // ☆ 注意： 必须要设置背景，播放动画有一个前提 就是窗体必须有背景
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);   // 设置窗口显示的动画效果
        popupWindow.update();
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        // 实现软键盘不自动弹出,ADJUST_RESIZE属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f, activity);
            }
        });
    }

    /**
     * 设置popup的背景 半透明
     *
     * @param bgAlpha //0.0f-1.0f
     */
    public void backgroundAlpha(float bgAlpha, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 移除PopupWindow
     */
    public void dismissPopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_information_pop_text1:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 0);
                }
                break;
            case R.id.person_information_pop_text2:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 1);
                }
                break;
            case R.id.person_information_pop_text3:
                if (null != mOnSelectedListener) {
                    mOnSelectedListener.OnSelected(v, 2);
                }
                break;
        }
    }

    /**
     * 设置选择监听
     *
     * @param l
     */
    public void setOnSelectedListener(OnSelectedListener l) {
        this.mOnSelectedListener = l;
    }

    /**
     * 选择监听接口
     */
    public interface OnSelectedListener {
        void OnSelected(View v, int position);
    }

}