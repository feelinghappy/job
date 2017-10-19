package com.longcai.medical.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.EditInfoBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.view.popupwindow.BirthdayPicker;
import com.longcai.medical.ui.view.popupwindow.HeightPicker;
import com.longcai.medical.ui.view.popupwindow.WeightPicker;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/15.
 */

public class PersonInfo2Fragment extends BaseFragment implements HeightPicker.IHeightPicker,
        WeightPicker.IWeightPicker, BirthdayPicker.IBirthdayPicker {

    Unbinder unbinder;
    @BindView(R.id.txt_height)
    TextView txtHeight;
    @BindView(R.id.txt_weight)
    TextView txtWeight;
    @BindView(R.id.txt_birthday)
    TextView txtBirthday;
    @BindView(R.id.btn_check)
    Button btnCheck;

    private boolean isHaveImg = false;

    private String height;
    private String weight;
    private String birthday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_info2, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.title_back, R.id.linear_height, R.id.linear_weight, R.id.linear_birthday, R.id.btn_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.linear_height:
                HeightPicker heightPicker = new HeightPicker(getActivity());
                heightPicker.showPopupWindow(txtHeight);
                heightPicker.setTitle("请选择身高");
                heightPicker.setIHeightPicker(this);
                break;
            case R.id.linear_weight:
                WeightPicker weightPicker = new WeightPicker(getActivity());
                weightPicker.showPopupWindow(txtWeight);
                weightPicker.setTitle("请选择体重");
                weightPicker.setIWeightPicker(this);
                break;
            case R.id.linear_birthday:
                BirthdayPicker birthdayPicker = new BirthdayPicker(getActivity());
                birthdayPicker.showPopupWindow(txtBirthday);
                birthdayPicker.setTitle("请选择出生年月");
                birthdayPicker.setIBirthdayPicker(this);
                break;
            case R.id.btn_check:
                if (checkNull()) {
                    return;
                }
                editInfo();
                break;
        }
    }

    private void editInfo() {
        Bundle data = getArguments();
        final String token = data.getString("token", "");
        String member_name = data.getString("member_name", "");
        String mobile = data.getString("mobile", "");
        String imagePath = data.getString("avatar", "");
        String inviteCode = data.getString("invite_code", "");
        final boolean weixin_login = data.getBoolean("weixin_login", false);
        if (!TextUtils.isEmpty(imagePath)) {
            isHaveImg = true;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("member_name", member_name);
        map.put("mobile", mobile);//MyApplication.myPreferences.readPhone()
        map.put("member_height", height);
        map.put("member_weight", weight);
        map.put("birthday", birthday);
        if (!TextUtils.isEmpty(inviteCode)) {
            map.put("invite_code", inviteCode);
        }
        if (!isHaveImg) {//判断用户是否上传了照片
            HttpUtils.xOverallHttpPost(getActivity(), MyUrl.USER_REGISTER, map, EditInfoBean.class, new HttpUtils.OnxHttpCallBack<EditInfoBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                }

                @Override
                public void onSuccess(EditInfoBean editInfoBean) {
                    try {
                        MyApplication.myPreferences.saveToken(token);
                        MyApplication.myPreferences.saveNickName(editInfoBean.getMember_name());
                        MyApplication.myPreferences.savePhone(editInfoBean.getMobile());
                        MyApplication.myPreferences.saveavatar(editInfoBean.getMember_avatar());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Constant.PHONE_NUMBER = editInfoBean.getMobile();
                        startActivity(new Intent(getActivity(), PersonSaleActivity.class).putExtra("source", MyApplication.source)
                                .putExtra("login_success", true).putExtra("weixin_login", weixin_login));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    }
                }

                @Override
                public void onFail(int code, String msg) {

                }
            });
        } else {//imgList添加了一张图片
            HashMap<String, String> filemap = new HashMap<>();
            filemap.put("avatar", imagePath);
            HttpUtils.xOverallHttpPost(getActivity(), MyUrl.USER_REGISTER, filemap, map, EditInfoBean.class, new HttpUtils.OnxHttpCallBack<EditInfoBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {

                }

                @Override
                public void onSuccess(EditInfoBean editInfoBean) {
                    try {
                        MyApplication.myPreferences.saveToken(token);
                        MyApplication.myPreferences.saveNickName(editInfoBean.getMember_name());
                        MyApplication.myPreferences.savePhone(editInfoBean.getMobile());
                        MyApplication.myPreferences.saveavatar(editInfoBean.getMember_avatar());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Constant.PHONE_NUMBER = editInfoBean.getMobile();
                        startActivity(new Intent(getActivity(), PersonSaleActivity.class).putExtra("source", MyApplication.source)
                                .putExtra("login_success", true).putExtra("weixin_login", weixin_login));
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    }
                }

                @Override
                public void onFail(int code, String msg) {

                }
            });
        }
    }

    private boolean checkNull() {
        if (TextUtils.isEmpty(height)) {
            ToastUtils.showToast(getActivity(), "请选择身高");
            return true;
        } else if (TextUtils.isEmpty(weight)) {
            ToastUtils.showToast(getActivity(), "请选择体重");
            return true;
        } else if (TextUtils.isEmpty(birthday)) {
            ToastUtils.showToast(getActivity(), "请选择出生年月");
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void weightPicker(int item) {
        weight = String.valueOf(item + 20);
        txtWeight.setText(String.valueOf(item + 20) + " kg");
        checkEnabled();
    }

    @Override
    public void heightPicker(int item) {
        height = String.valueOf(item + 100);
        txtHeight.setText(String.valueOf(item + 100) + " cm");
        checkEnabled();
    }

    @Override
    public void birthdayPicker(String dateStr, String date) {
        birthday = date;
        txtBirthday.setText(dateStr);
        checkEnabled();
    }

    private void checkEnabled() {
        if (TextUtils.isEmpty(height)) {
            btnCheck.setEnabled(false);
        } else if (TextUtils.isEmpty(weight)) {
            btnCheck.setEnabled(false);
        } else if (TextUtils.isEmpty(birthday)) {
            btnCheck.setEnabled(false);
        } else {
            btnCheck.setEnabled(true);
        }
    }
}
