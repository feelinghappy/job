package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.RemindTargetAdapter;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.HttpUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/12.
 */

public class RemindTargetActivity extends BaseActivity implements RemindTargetAdapter.ITargetSelect {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.remind_target_select1)
    CheckBox remindTargetSelect1;
    @BindView(R.id.remind_target_select2)
    CheckBox remindTargetSelect2;
//    @BindView(R.id.remind_target_select3)
//    CheckBox remindTargetSelect3;
    @BindView(R.id.remind_target_lv)
    ListView remindTargetLv;

    private String familyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_target);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("选择提醒对象");
        titleRightTv.setText("保存");
        familyId = getIntent().getStringExtra("family_id");
        initTargetList();
    }

    private void initTargetList() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", familyId);
        HttpUtils.xOverallHttpPost(this, MyUrl.FAMILY_INFO, map, GetFamilyInfoBean.class, new HttpUtils.OnxHttpCallBack<GetFamilyInfoBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetFamilyInfoBean getFamilyInfoBean) {
                List<GetFamilyInfoBean.MemberListBean> memberList = getFamilyInfoBean.getMember_list();
                if (null != memberList) {
                    RemindTargetAdapter remindTargetAdapter = new RemindTargetAdapter(RemindTargetActivity.this, memberList);
                    remindTargetLv.setAdapter(remindTargetAdapter);
                    remindTargetAdapter.setiTargetSelect(RemindTargetActivity.this);
                }
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
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
                resetMember();
                remindTargetSelect2.setChecked(false);
//                remindTargetSelect3.setChecked(false);
                break;
            case R.id.remind_target_select2:
                resetMember();
                remindTargetSelect1.setChecked(false);
//                remindTargetSelect3.setChecked(false);
                break;
//            case R.id.remind_target_select3:
//                resetMember();
//                remindTargetSelect2.setChecked(false);
//                remindTargetSelect1.setChecked(false);
//                break;
        }
    }

    private void save() {
        String warnerName = "";
        String warnerId = "";
//        if (remindTargetSelect3.isChecked()) {
//            warnerName = "自己";
//            warnerId = "1";
//        }
        if (remindTargetSelect2.isChecked()) {
            warnerName = "机器人提醒";
            warnerId = "-2";
        }
        if (remindTargetSelect1.isChecked()) {
            warnerName = "家庭提醒";
            warnerId = "-3";
        }
        int count = remindTargetLv.getAdapter().getCount();
        RemindTargetAdapter adapter = (RemindTargetAdapter) remindTargetLv.getAdapter();
        for (int i = 0; i < count; i++) {
            GetFamilyInfoBean.MemberListBean member = adapter.getItem(i);
            GetFamilyInfoBean.MemberListBean.MemberInfoBean memberInfo = member.getMember_info();
            if (member.isChecked()) {
                warnerName = memberInfo.getMember_name();
                warnerId = memberInfo.getFamily_member_id();
            }
        }
        Intent data = new Intent();
        data.putExtra("warner_name", warnerName);
        data.putExtra("warner_id", warnerId);
        setResult(RESULT_OK, data);
        finish();
    }

    private void resetMember() {
        int count = remindTargetLv.getAdapter().getCount();
        RemindTargetAdapter adapter = (RemindTargetAdapter) remindTargetLv.getAdapter();
        for (int i = 0; i < count; i++) {
            GetFamilyInfoBean.MemberListBean member = adapter.getItem(i);
            member.setChecked(false);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void targetSelect(int position) {
        remindTargetSelect1.setChecked(false);
        remindTargetSelect2.setChecked(false);
//        remindTargetSelect3.setChecked(false);
        int count = remindTargetLv.getAdapter().getCount();
        RemindTargetAdapter adapter = (RemindTargetAdapter) remindTargetLv.getAdapter();
        for (int i = 0; i < count; i++) {
            GetFamilyInfoBean.MemberListBean member = adapter.getItem(i);
            if (position == i) {
                member.setChecked(true);
            } else {
                member.setChecked(false);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
