package com.longcai.medical.ui.view.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.longcai.medical.R;
import com.longcai.medical.utils.UIUtils;

/**
 * Created by Administrator on 2017/8/3.
 */

public class ServicePopupWindow extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {
    private Activity activity;
    private ImageView cancelBtn;
    private Button callBtn;
    private ICallPhoneBack iCallPhoneBack;

    public ServicePopupWindow(final Activity context) {
        super(context);
        this.activity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.kefu_view2, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        this.setContentView(conentView);
        this.setWidth(w);
        this.setHeight(h);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
//        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(android.R.style.Animation_Translucent);

        cancelBtn = (ImageView) conentView.findViewById(R.id.xx_btn);
        callBtn = (Button) conentView.findViewById(R.id.call_btn);

        cancelBtn.setOnClickListener(this);
        callBtn.setOnClickListener(this);
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
        } else if (view == callBtn) {
            if (null != iCallPhoneBack) {
                iCallPhoneBack.callPhoneBack("4001002918");
            }
            dismiss();
        }
    }

    public interface ICallPhoneBack {
        void callPhoneBack(String tel);
    }

    public void setICallPhoneBack(ICallPhoneBack iCallPhoneBack) {
        this.iCallPhoneBack = iCallPhoneBack;
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }
}
