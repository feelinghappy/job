package com.longcai.medical.ui.activity.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.EditInfoBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
import com.longcai.medical.utils.HttpUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/4.
 * 个人信息修改昵称
 */

public class PersonEditNameActivity extends BaseActivity implements CustomPopupWindow.IOKBack {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.person_edit_name)
    EditText personEditName;
//    @BindView(R.id.person_edit_delete)
//    ImageView personEditDelete;
    @BindView(R.id.title_left_tv)
    TextView titleLeftTv;
    private Unbinder unbinder;

    private String memberHeight;
    private String memberWeight;
    private String birthday;
    private String mobile;
    private String nickname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_edit_name);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        personEditName.requestFocus();
        personEditName.setHint(R.string.name);
        new Timer().schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 500);
    }

    private void initView() {
        titleTv.setText("修改昵称");
        titleRightTv.setText("保存");
        titleRightTv.setClickable(false);
        titleBack.setVisibility(View.GONE);
        titleLeftTv.setVisibility(View.VISIBLE);
        nickname = getIntent().getStringExtra("member_name");
        mobile = getIntent().getStringExtra("mobile");
        memberHeight = getIntent().getStringExtra("member_height");
        memberWeight = getIntent().getStringExtra("member_weight");
        birthday = getIntent().getStringExtra("birthday");
        personEditName.setText(nickname);
        personEditName.setSelection(nickname.length());
        titleRightTv.setTextColor(getResources().getColor(R.color.light_gray));
        personEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (personEditName.getText().toString() != null) {
//                    titleRightTv.setTextColor(getResources().getColor(R.color.black));
//                    titleRightTv.setClickable(true);
//                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (personEditName.getText().toString() != null) {
//                    titleRightTv.setTextColor(getResources().getColor(R.color.black));
//                    titleRightTv.setClickable(true);
//                } else {
//                    titleRightTv.setTextColor(getResources().getColor(R.color.light_gray));
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String changedName = s.toString();
                if (!TextUtils.isEmpty(changedName) && !changedName.equals(nickname)) {
                    titleRightTv.setTextColor(getResources().getColor(R.color.black));
                    titleRightTv.setClickable(true);
                } else {
                    titleRightTv.setTextColor(getResources().getColor(R.color.light_gray));
                    titleRightTv.setClickable(false);
                }
            }
        });
    }

    @OnClick({R.id.title_left_tv, R.id.title_right_tv, R.id.person_edit_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_tv:
                personEditName.clearFocus();
                new Timer().schedule(new TimerTask() {
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(personEditName.getWindowToken(), 0); //强制隐藏键盘
                    }
                }, 100);
                Constant.isEditName = false;
//                finish();
                back();
                break;
            case R.id.title_right_tv:
//                if (StringUtils.isEmpty(personEditName.getText().toString())) {
//                    ToastUtils.showToast(this, "昵称不能为空");
//                    return;
//                }
                Constant.isEditName = true;
                editInfo();
                break;
            case R.id.person_edit_name:
                break;
//            case R.id.person_edit_delete:
//                personEditName.setText("");
//                titleRightTv.setTextColor(getResources().getColor(R.color.light_gray));
//                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        String name = personEditName.getText().toString();
        //!TextUtils.isEmpty(name) || !TextUtils.isEmpty(imagePath) || !TextUtils.isEmpty(MyApplication.myPreferences.readavatar())
        if (!TextUtils.isEmpty(name) && !name.equals(nickname)) {
            CustomPopupWindow customPopupWindow = new CustomPopupWindow(this);
            customPopupWindow.showPopupWindow(titleRightTv);
            customPopupWindow.setIOKBack(this);
            customPopupWindow.setTitle("您的修改还未提交，确认现在返回吗？");
        } else {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }

    //编辑会员资料
    private void editInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("member_name", personEditName.getText().toString());
        map.put("mobile", mobile);//MyApplication.myPreferences.readPhone()
        map.put("member_height", memberHeight);
        map.put("member_weight", memberWeight);
        map.put("birthday", birthday);
            HttpUtils.xOverallHttpPost(PersonEditNameActivity.this, MyUrl.USER_REGISTER, map, EditInfoBean.class, new HttpUtils.OnxHttpCallBack<EditInfoBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                }

                @Override
                public void onSuccess(EditInfoBean editInfoBean) {
                    MyApplication.myPreferences.saveNickName(editInfoBean.getMember_name());
                    MyApplication.myPreferences.savePhone(editInfoBean.getMobile());
                    Constant.PHONE_NUMBER = editInfoBean.getMobile();
                    Intent intent = new Intent();
                    intent.putExtra("nickname", personEditName.getText().toString());
                    setResult(Constant.PERSON_NAME, intent);
                    finish();
                    Constant.isCommit = true;
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }

                @Override
                public void onFail(int code, String msg) {

                }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void okBack() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
