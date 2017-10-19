package com.longcai.medical.ui.activity.person.healthdoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/4.
 * 个人信息修改昵称
 */

public class HealthEditNameActivity extends BaseActivity implements View.OnTouchListener {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.person_edit_name)
    ClearEditText personEditName;
//    @BindView(R.id.person_edit_delete)
//    ImageView personEditDelete;
    @BindView(R.id.title_left_tv)
    TextView titleLeftTv;

    private Unbinder unbinder;
    private String truename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_edit_name);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("基本资料");
        titleRightTv.setText("保存");
        titleRightTv.setClickable(false);
        titleBack.setVisibility(View.GONE);
        titleLeftTv.setVisibility(View.VISIBLE);
        truename = getIntent().getStringExtra("true_name");
        personEditName.setHint(R.string.name2);
        personEditName.setText(truename);
        personEditName.setSelection(truename.length());
        personEditName.setOnTouchListener(this);
        personEditName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (personEditName.getText().toString() != null) {
                    titleRightTv.setTextColor(getResources().getColor(R.color.black));
                    titleRightTv.setClickable(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (personEditName.getText().toString() != null) {
                    titleRightTv.setTextColor(getResources().getColor(R.color.black));
                    titleRightTv.setClickable(true);
                } else {
                    titleRightTv.setTextColor(getResources().getColor(R.color.light_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                titleRightTv.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }

    @OnClick({R.id.title_left_tv, R.id.title_right_tv, R.id.person_edit_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left_tv:
                finish();
                break;
            case R.id.title_right_tv:
                if (StringUtils.isEmpty(personEditName.getText().toString())) {
                    ToastUtils.showToast(this, "姓名不能为空");
                    return;
                }
                if (personEditName.getText().toString().length() < 2) {
                    ToastUtils.showToast(this, "姓名最少2个字符");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("update_true_name", personEditName.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int max_text_input_length = 4;
        //判断昵称限制：英文、中文及数字
        StringUtils.isNameLimit(personEditName);
        personEditName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_text_input_length)});
        return false;
    }
}
