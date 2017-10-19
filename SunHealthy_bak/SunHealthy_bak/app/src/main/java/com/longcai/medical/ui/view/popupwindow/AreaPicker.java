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

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.AreaResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.UIUtils;

import java.util.HashMap;
import java.util.List;

import kankan.wheel.widget.*;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AreaPicker extends PopupWindow implements OnWheelChangedListener, View.OnClickListener, PopupWindow.OnDismissListener {

    private kankan.wheel.widget.WheelView mProvinceWheel;
    private kankan.wheel.widget.WheelView mCityWheel;
    private kankan.wheel.widget.WheelView mAreaWheel;
    private String[] mCityDatas;
    private String[] mAreaDatas;
    private List<AreaResult> mProvinceList;
    private List<AreaResult> mCityList;
    private List<AreaResult> mAreaList;
    private Activity activity;
    private IAreaPickerBack iAreaPickerBack;
    private Button mCancelBtn;
    private Button mOKBtn;
    private TextView mTitleTxt;

    public AreaPicker(final Activity context, List<AreaResult> provinceList, List<AreaResult> cityList, List<AreaResult> areaList) {
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

        mProvinceWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_province);
        mCityWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_city);
        mAreaWheel = (kankan.wheel.widget.WheelView) conentView.findViewById(R.id.id_area);
        mCancelBtn = (Button) conentView.findViewById(R.id.btnCancel);
        mCancelBtn.setOnClickListener(this);
        mOKBtn = (Button) conentView.findViewById(R.id.btnSubmit);
        mOKBtn.setOnClickListener(this);
        mTitleTxt = (TextView) conentView.findViewById(R.id.tvTitle);
        activity = context;
        this.mProvinceList = provinceList;
        this.mCityList = cityList;
        this.mAreaList = areaList;

        int provinceSize = mProvinceList.size();
        String[] mProvinceDatas = new String[provinceSize];
        for (int i = 0; i < provinceSize; i++) {
            mProvinceDatas[i] = mProvinceList.get(i).getArea_name();
        }
        int citySize = mCityList.size();
        mCityDatas = new String[citySize];
        for (int i = 0; i < citySize; i++) {
            mCityDatas[i] = mCityList.get(i).getArea_name();
        }
        int areaSize = mAreaList.size();
        mAreaDatas = new String[areaSize];
        for (int i = 0; i < areaSize; i++) {
            mAreaDatas[i] = mAreaList.get(i).getArea_name();
        }
        mProvinceWheel.setViewAdapter(new ArrayWheelAdapter<String>(context, mProvinceDatas));
        mProvinceWheel.setCurrentItem(0);
        mCityWheel.setViewAdapter(new ArrayWheelAdapter<String>(activity, mCityDatas));
        mCityWheel.setCurrentItem(0);
        mAreaWheel.setViewAdapter(new ArrayWheelAdapter<String>(activity, mAreaDatas));
        mAreaWheel.setCurrentItem(0);
        mProvinceWheel.addChangingListener(this);
        mCityWheel.addChangingListener(this);
        mAreaWheel.addChangingListener(this);

        mProvinceWheel.setVisibleItems(7);
        mCityWheel.setVisibleItems(7);
        mAreaWheel.setVisibleItems(7);
    }

    public void setTitle(String title) {
        this.mTitleTxt.setText(title);
    }

    public void setAreaPickerBack(IAreaPickerBack iAreaPickerBack) {
        this.iAreaPickerBack = iAreaPickerBack;
    }

    private void getArea(final int type, String parent_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("parent_id", parent_id);
        HttpUtils.xOverallHttpPost(activity, MyUrl.GET_AREA, map, AreaResult.class, new
                HttpUtils.OnxHttpWithListResultCallBack<AreaResult>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {

                    }

                    @Override
                    public void onSuccess(List<AreaResult> t) {
                        LogUtils.i("getAreaData", t.toString());
                        if (type == 1) {
                            int citySize = t.size();
                            if (!mCityList.isEmpty()) {
                                mCityList.clear();
                            }
                            mCityList.addAll(t);
                            mCityDatas = null;
                            mCityDatas = new String[citySize];
                            for (int i = 0; i < citySize; i++) {
                                AreaResult city = t.get(i);
                                mCityDatas[i] = city.getArea_name();
                            }
                            mCityWheel.setViewAdapter(new ArrayWheelAdapter<String>(activity, mCityDatas));
                            mCityWheel.setCurrentItem(0);
                            getArea(2, t.get(0).getArea_id());
                        } else if (type == 2) {
                            int countrySize = t.size();
                            if (!mAreaList.isEmpty()) {
                                mAreaList.clear();
                            }
                            mAreaList.addAll(t);
                            mAreaDatas = null;
                            mAreaDatas = new String[countrySize];
                            for (int i = 0; i < countrySize; i++) {
                                AreaResult country = t.get(i);
                                mAreaDatas[i] = country.getArea_name();
                            }
                            mAreaWheel.setViewAdapter(new ArrayWheelAdapter<String>(activity, mAreaDatas));
                            mAreaWheel.setCurrentItem(0);
                        }
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        if (type == 2 && code == 10030) {
                            if (!mAreaList.isEmpty()) {
                                mAreaList.clear();
                            }
                            mAreaDatas = new String[]{};
                            mAreaWheel.setViewAdapter(new ArrayWheelAdapter<String>(activity, mAreaDatas));
                            mAreaWheel.setCurrentItem(0);
                        }
                    }
                });
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
        if (wheel == mProvinceWheel) {
            getArea(1, mProvinceList.get(newValue).getArea_id());
        } else if (wheel == mCityWheel) {
            getArea(2, mCityList.get(newValue).getArea_id());
        }
//        else if (wheel == mAreaWheel) {
//
//        }
    }

    @Override
    public void onClick(View view) {
        if (view == mCancelBtn) {
            dismiss();
            removeListener();
        } else if (view == mOKBtn) {
            int provinceItem = mProvinceWheel.getCurrentItem();
            int cityItem = mCityWheel.getCurrentItem();
            int areaItem = mAreaWheel.getCurrentItem();
            String pickAreaId = mCityList.get(cityItem).getArea_id();
            String pickAreaName = mProvinceList.get(provinceItem).getArea_name() + " " + mCityList.get(cityItem).getArea_name();
            if (!mAreaList.isEmpty()) {
                pickAreaId = mAreaList.get(areaItem).getArea_id();
                pickAreaName += " " + mAreaList.get(areaItem).getArea_name();
            }
            if (null != iAreaPickerBack) {
                iAreaPickerBack.areaPicker(pickAreaId, pickAreaName);
            }
            dismiss();
            removeListener();
        }
    }

    private void removeListener() {
        mProvinceWheel.removeChangingListener(this);
        mCityWheel.removeChangingListener(this);
        mAreaWheel.removeChangingListener(this);
    }

    @Override
    public void onDismiss() {
        UIUtils.backgroundAlpha(activity, 1f);
    }

    public interface IAreaPickerBack {
        void areaPicker(String areaId, String areaName);
    }
}
