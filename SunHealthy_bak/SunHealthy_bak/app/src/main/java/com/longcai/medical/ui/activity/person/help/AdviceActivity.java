package com.longcai.medical.ui.activity.person.help;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.popupwindow.AdviceTypePicker;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/4.
 */

public class AdviceActivity extends BaseActivity implements AdviceTypePicker.IAdviceTypePicker {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.tv_advice_type)
    TextView tvAdviceType;
    @BindView(R.id.rl_advice_type)
    RelativeLayout rlAdviceType;
    @BindView(R.id.et_advice_content)
    EditText etAdviceContent;
    @BindView(R.id.et_advice_contact)
    EditText etAdviceContact;
    @BindView(R.id.tv_submit)
    TextView tvFeedback;

    private Unbinder unbinder;
    private int item = -1;
    private String adviceContent;
    private String adviceContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleTv.setText("意见反馈");
        etAdviceContent.setHintTextColor(ContextCompat.getColor(this, R.color.grey));
        etAdviceContact.setHintTextColor(ContextCompat.getColor(this, R.color.grey));
        etAdviceContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                int length = content.length();
                if (length == 100) {
                    ToastUtils.showToast(AdviceActivity.this, "提醒内容不超过100个字");
                }
            }
        });
    }

    private boolean checkQQ(String mobile) {
        try {
            return mobile.matches("^[1-9][0-9]{4,14}$");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkData() {
        adviceContent = etAdviceContent.getText().toString();
        adviceContact = etAdviceContact.getText().toString().trim();
        if (item < 0 || item > 3) {
            ToastUtils.showToast(this, "请选择问题类型");
            return false;
        } else if (TextUtils.isEmpty(adviceContent.trim())) {
            ToastUtils.showToast(this, "请输入您的问题或建议");
            return false;
        } else if (!TextUtils.isEmpty(adviceContact) && !checkQQ(adviceContact) && !RegexUtils.checkMobile(adviceContact)) {
            ToastUtils.showToast(this, "无效的联系方式");
            return false;
        }
        return true;
    }

    @OnClick({R.id.title_back, R.id.rl_advice_type, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_advice_type:
                AdviceTypePicker adviceTypePicker = new AdviceTypePicker(this);
                adviceTypePicker.showPopupWindow(rlAdviceType);
                adviceTypePicker.setIAdviceTypePicker(this);
                break;
            case R.id.tv_submit:
                if (checkData()) {
                    feedback();
                }
                break;
        }
    }

    private void feedback() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("back_type", String.valueOf(item));
        map.put("content", adviceContent);
        if (!TextUtils.isEmpty(adviceContact)) {
            map.put("contact", adviceContact);
        } else {
            map.put("contact", "");
        }
        HttpUtils.xOverallHttpPost(this, MyUrl.FEED_BACK, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
                ToastUtils.showToast(AdviceActivity.this, "感谢您的宝贵意见，我们会在第一时间回复您！");
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10071) {
                    ToastUtils.showToast(AdviceActivity.this, "内容不能为空或内容无效");
                } else if (code == 10072) {
                    ToastUtils.showToast(AdviceActivity.this, "无效的联系方式");
                } else if (code == 10073) {
                    ToastUtils.showToast(AdviceActivity.this, "保存数据失败");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void adviceTypePicker(int item, String itemName) {
        LogUtils.d("adviceTypePicker", item + " " + itemName);
        this.item = item;
        tvAdviceType.setText(itemName);
    }
}
