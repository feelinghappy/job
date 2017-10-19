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

public class PhotoPicker extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private Activity activity;
    private TextView cameraView;
    private TextView albumView;
    private TextView cancelView;
    private IPhotoPicker iPhotoPicker;

    public PhotoPicker(final Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.person_information_pop, null);
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

        cameraView = (TextView) conentView.findViewById(R.id.person_information_pop_text1);
        albumView = (TextView) conentView.findViewById(R.id.person_information_pop_text2);
        cancelView = (TextView) conentView.findViewById(R.id.person_information_pop_text3);
        activity = context;

        cameraView.setOnClickListener(this);
        albumView.setOnClickListener(this);
        cancelView.setOnClickListener(this);
    }

    public void setIPhotoPicker(IPhotoPicker iPhotoPicker) {
        this.iPhotoPicker = iPhotoPicker;
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
        if (view == cancelView) {
            dismiss();
        } else if (view == cameraView) {
            if (null != iPhotoPicker) {
                iPhotoPicker.cameraPhoto();
            }
            dismiss();
        } else if (view == albumView) {
            if (null != iPhotoPicker) {
                iPhotoPicker.albumPhoto();
            }
            dismiss();
        }
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface IPhotoPicker {
        void cameraPhoto();
        void albumPhoto();
    }
}
