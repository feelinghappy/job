package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/12.
 */

public class RemindContentActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.edit_delete)
    ImageView editDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("提醒内容");
        titleRightTv.setText("保存");
        String preContent = getIntent().getStringExtra("remind_content");
        if (!TextUtils.isEmpty(preContent)) {
            etContent.setText(preContent);
        }
        etContent.addTextChangedListener(new TextWatcher() {
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
                if (length > 0) {
                    editDelete.setVisibility(View.VISIBLE);
                } else {
                    editDelete.setVisibility(View.GONE);
                }
                if (length == 30) {
                    ToastUtils.showToast(RemindContentActivity.this, "提醒内容不超过30个字");
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.title_right_tv, R.id.edit_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_tv:
                save();
                break;
            case R.id.edit_delete:
                etContent.setText("");
                break;
        }
    }

    private void save() {
        String content = etContent.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            Intent data = new Intent();
            data.putExtra("remind_content", content);
            setResult(RESULT_OK, data);
            finish();
        } else {
            ToastUtils.showToast(this, "提醒内容不能为空");
        }
    }
}
