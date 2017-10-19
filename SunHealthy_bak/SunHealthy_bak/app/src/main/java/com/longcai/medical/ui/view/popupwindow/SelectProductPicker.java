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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longcai.medical.R;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.utils.UIUtils;

import static com.longcai.medical.utils.UIUtils.getString;

/**
 * Created by Administrator on 2017/8/3.
 */

public class SelectProductPicker extends PopupWindow implements View.OnClickListener, PopupWindow.OnDismissListener {

    private Activity activity;
    private TextView moneyTxt;
    private TextView numTxt;
//    private TextView specTxt;
//    private TextView specCateTxt;
    private RelativeLayout addBtn;
    private RelativeLayout reduceBtn;
    private ImageView telImg;
    private LinearLayout deleteImg;
    private TextView telTxt;
    private Button submitBtn;
    private ISubmitCallback iSubmitCallback;
    private double unitPrice;
//    private boolean isSpec = true;

    public SelectProductPicker(final Activity context, String goodsName, String gcId, String image, String goodsPrice) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.popup_select_product, null);
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

        ImageView robotImg = (ImageView) conentView.findViewById(R.id.product_img_icon);
        TextView nameTxt = (TextView) conentView.findViewById(R.id.product_txt_robotname);
        moneyTxt = (TextView) conentView.findViewById(R.id.product_txt_money);
        numTxt = (TextView) conentView.findViewById(R.id.sales_txt_number);
        addBtn = (RelativeLayout) conentView.findViewById(R.id.sales_btn_add);
        reduceBtn = (RelativeLayout) conentView.findViewById(R.id.sales_btn_reduce);
        telImg = (ImageView) conentView.findViewById(R.id.sales_img_service);
        deleteImg = (LinearLayout) conentView.findViewById(R.id.delete_img);
        telTxt = (TextView) conentView.findViewById(R.id.sales_txt_contact_service);
        submitBtn = (Button) conentView.findViewById(R.id.sales_btn_submit);
//        specTxt = (TextView) conentView.findViewById(R.id.txt_spec);
//        specCateTxt = (TextView) conentView.findViewById(R.id.txt_spec_cate);
        activity = context;

        Glide.with(activity).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.chanpinxiangqing_zhanweitu).into(robotImg);
        nameTxt.setText(goodsName);
        if (gcId.equals("1")) {
            moneyTxt.setText("押金：￥" + goodsPrice);
        } else {
            moneyTxt.setText("金额：￥" + goodsPrice);
//            specTxt.setVisibility(View.GONE);
//            specCateTxt.setVisibility(View.GONE);
//            isSpec = true;
        }
        addBtn.setOnClickListener(this);
        reduceBtn.setOnClickListener(this);
        telImg.setOnClickListener(this);
        telTxt.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        deleteImg.setOnClickListener(this);
//        specTxt.setOnClickListener(this);
        unitPrice = Double.parseDouble(goodsPrice);
    }

    public void setISubmitCallback(ISubmitCallback iSubmitCallback) {
        this.iSubmitCallback = iSubmitCallback;
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
        if (view == addBtn) {
            add();
            if (null != iSubmitCallback) {
//                iSubmitCallback.isSpec(isSpec);
                iSubmitCallback.goodsNum(numTxt.getText().toString());
            }
        } else if (view == reduceBtn) {
            reduce();
            if (null != iSubmitCallback) {
//                iSubmitCallback.isSpec(isSpec);
                iSubmitCallback.goodsNum(numTxt.getText().toString());
            }
        } else if (view == submitBtn) {
            if (null != iSubmitCallback) {
//                iSubmitCallback.isSpec(isSpec);
                iSubmitCallback.goodsNum(numTxt.getText().toString());
                iSubmitCallback.submit();
            }
        } else if (view == telImg || view == telTxt) {
            if (null != iSubmitCallback) {
                iSubmitCallback.contactService();
            }
        } else if (view == deleteImg) {
            dismiss();
        }
//        else if (view == specTxt) {
//            if (isSpec) {
//                specTxt.setBackgroundResource(R.drawable.robot_spec_unselect);
//                isSpec = false;
//            } else {
//                specTxt.setBackgroundResource(R.drawable.robot_spec_select);
//                isSpec = true;
//            }
//            if (null != iSubmitCallback) {
//                iSubmitCallback.isSpec(isSpec);
//                iSubmitCallback.goodsNum(numTxt.getText().toString());
//            }
//        }
    }

    public void reduce() {
        int num = getNumber();
        if (num > 0) {
            num--;
        }
        numTxt.setText(String.valueOf(num));
        moneyTxt.setText(String.format(getString(R.string.sales_margin), String.valueOf(num * unitPrice)));
    }

    public void add() {
        int num = getNumber();
        if (num == 10) {
            ToastUtils.showToast(activity, "商品购买上限10个~");
            return;
        }
        if (num >= 0) {
            num++;
        }
        numTxt.setText(String.valueOf(num));
        moneyTxt.setText(String.format(getString(R.string.sales_margin), String.valueOf(num * unitPrice)));
    }

    private int getNumber() {
        int i = 1;
        String num = numTxt.getText().toString();
        i = StringUtils.parseInt(num);
        return i;
    }

    @Override
    public void onDismiss() {
        if (iSubmitCallback != null) {
            iSubmitCallback.goodsNum(numTxt.getText().toString());
        }
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface ISubmitCallback {
        void goodsNum(String num);

        void contactService();

        void submit();

//        void isSpec(boolean isSpec);
    }
}
