package com.longcai.medical.ui.activity.remind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/12.
 */

public class RemindRepeatActivity extends BaseActivity {


    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.remind_target_select1)
    CheckBox remindTargetSelect1;
    @BindView(R.id.remind_target_select2)
    CheckBox remindTargetSelect2;
    @BindView(R.id.remind_target_select3)
    CheckBox remindTargetSelect3;
    @BindView(R.id.remind_target_select4)
    CheckBox remindTargetSelect4;
    @BindView(R.id.remind_target_select5)
    CheckBox remindTargetSelect5;
    @BindView(R.id.remind_target_select6)
    CheckBox remindTargetSelect6;
    @BindView(R.id.remind_target_select7)
    CheckBox remindTargetSelect7;

    private ArrayList<String> repeat = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_repeat);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("重复");
        titleRightTv.setText("保存");
    }

    @OnClick({R.id.title_back, R.id.title_right_tv, R.id.remind_target_select1, R.id.remind_target_select2, R.id.remind_target_select3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_tv://保存
                save();
                break;
            case R.id.remind_target_select1:

                break;
            case R.id.remind_target_select2:

                break;
            case R.id.remind_target_select3:

                break;
            case R.id.remind_target_select4:

                break;
            case R.id.remind_target_select5:

                break;
            case R.id.remind_target_select6:

                break;
            case R.id.remind_target_select7:

                break;
        }
    }

    private void save() {
        if (repeat.size() > 0) {
            repeat.clear();
        }
        if (remindTargetSelect1.isChecked()) {
            repeat.add("周一");
        }
        if (remindTargetSelect2.isChecked()) {
            repeat.add("周二");
        }
        if (remindTargetSelect3.isChecked()) {
            repeat.add("周三");
        }
        if (remindTargetSelect4.isChecked()) {
            repeat.add("周四");
        }
        if (remindTargetSelect5.isChecked()) {
            repeat.add("周五");
        }
        if (remindTargetSelect6.isChecked()) {
            repeat.add("周六");
        }
        if (remindTargetSelect7.isChecked()) {
            repeat.add("周日");
        }
        if (repeat.size() == 0) {
            ToastUtils.showToast(this, "重复不能为空");
            return;
        }
        Intent data = new Intent();
        data.putExtra("remind_repeat", repeat);
        setResult(RESULT_OK, data);
        finish();
    }
}
