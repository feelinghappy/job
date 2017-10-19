package com.longcai.medical.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.bean.GetThingWarns;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.remind.RemindRepeatActivity;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
import com.longcai.medical.ui.view.popupwindow.RemindSyncPicker;
import com.longcai.medical.ui.view.popupwindow.TimePicker;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/10.
 */

public class AddRemindsActivity extends BaseActivity implements TimePicker.ITimePicker, CustomPopupWindow.IOKBack, RemindSyncPicker.IRemindSyncPicker {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.title_right_iv)
    ImageView titleRightIv;
    @BindView(R.id.remind_item_content)
    TextView remindItemContent;
    @BindView(R.id.remind_item_time)
    TextView remindItemTime;
    @BindView(R.id.remind_item_repeat)
    TextView remindItemRepeat;
    @BindView(R.id.remind_item_target)
    TextView remindItemTarget;
    @BindView(R.id.remind_item_layout2)
    RelativeLayout remindItemLayout2;

    Unbinder unbinder;
    private final int REQUEST_CODE_CONTENT = 302;
    private final int REQUEST_CODE_REPEAT = 303;
    private final int REQUEST_CODE_TARGET = 304;

    private String familyId;
    private String familyMemberId;
    private String warnerId = "-3";
    private boolean addRemind;
    private String warnsId;
    private String repeatTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_remind_item);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    void initView() {
        addRemind = getIntent().getBooleanExtra("add_remind", false);
        titleTv.setText("添加提醒");
        titleRightTv.setVisibility(View.GONE);
        repeatTime = remindItemRepeat.getText().toString();
        familyMemberId = getIntent().getStringExtra("family_member_id");
        if (!addRemind) {
            titleRightIv.setVisibility(View.VISIBLE);
            titleRightIv.setImageResource(R.mipmap.icon_remind_delete);
            GetThingWarns warn = (GetThingWarns) getIntent().getSerializableExtra("thing_warn");
            String sendTime = warn.getSend_time();
            repeatTime = warn.getRepeat_time();
            String warnsContent = warn.getWarns_content();
            String acceptMemberName = warn.getAccept_member_name();
            warnsId = warn.getWarns_id();
            familyId = warn.getFamily_id();
            String warnWarner = warn.getWarner();
            if (!TextUtils.isEmpty(warnsContent)) {
                remindItemContent.setText(warnsContent);
            }
            if (!TextUtils.isEmpty(sendTime)) {
                remindItemTime.setText(sendTime);
            }
            if (!TextUtils.isEmpty(repeatTime)) {
                if (repeatTime.contains("周一,周二,周三,周四,周五,周六,周日")) {
                    remindItemRepeat.setText(repeatTime.replace("周一,周二,周三,周四,周五,周六,周日", "每天"));
                } else if (repeatTime.contains("周一,周二,周三,周四,周五")) {
                    remindItemRepeat.setText(repeatTime.replace("周一,周二,周三,周四,周五", "工作日"));
                } else {
                    remindItemRepeat.setText(repeatTime);
                }
            }
            if (!TextUtils.isEmpty(acceptMemberName)) {

                if (warnWarner.equals("2")) {
                    warnerId = "-2";
                    remindItemTarget.setText("机器人");
                } else if (warnWarner.equals("3")) {
                    warnerId = "-3";
                    remindItemTarget.setText("家庭");
                } else {
                    warnerId = warn.getAccept_member();
                    remindItemTarget.setText(acceptMemberName);
                }
            }
        } else {
            familyId = getIntent().getStringExtra("family_id");
        }
    }

    @OnClick({R.id.title_back, R.id.title_right_iv, R.id.remind_item_content, R.id.remind_item_time, R.id.remind_item_repeat, R.id.remind_item_target, R.id.remind_item_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_iv://删除
                CustomPopupWindow customPopupWindow = new CustomPopupWindow(this);
                customPopupWindow.showPopupWindow(titleRightIv);
                customPopupWindow.setIOKBack(this);
                break;
            case R.id.remind_item_content:
                startActivityForResult(new Intent(this, RemindContentActivity.class).putExtra("remind_content", remindItemContent.getText().toString()), REQUEST_CODE_CONTENT);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.remind_item_time:
                TimePicker timePicker = new TimePicker(this);
                timePicker.showPopupWindow(remindItemLayout2);
                timePicker.setITimePicker(this);
                break;
            case R.id.remind_item_repeat:
                startActivityForResult(new Intent(this, RemindRepeatActivity.class), REQUEST_CODE_REPEAT);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.remind_item_target://选择提醒对象
                startActivityForResult(new Intent(this, RemindTargetActivity.class).putExtra("family_id", familyId), REQUEST_CODE_TARGET);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.remind_item_save://保存
                if (warnerId.equals(familyMemberId) && MyApplication.myPreferences.readHavaWatch()) {
                    RemindSyncPicker remindSyncPicker = new RemindSyncPicker(this);
                    remindSyncPicker.showPopupWindow(remindItemRepeat);
                    remindSyncPicker.setIRemindSyncPicker(this);
                } else {
                    if (addRemind) {
                        save("0");
                    } else {
                        edit("0");
                    }
                }
                break;
        }
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
                    for (GetFamilyInfoBean.MemberListBean memberListBean : memberList) {
                        GetFamilyInfoBean.MemberListBean.MemberInfoBean memberInfoBean = memberListBean.getMember_info();
                        String memberId = memberInfoBean.getFamily_member_id();
                        if (!TextUtils.isEmpty(memberId) && warnerId.equals(memberId)) {
                            String memberName = memberInfoBean.getMember_name();
                            if (!TextUtils.isEmpty(memberName)) {
                                remindItemTarget.setText(memberName);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    private void delete() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("warns_id", warnsId);
        map.put("family_id", familyId);
        map.put("accept_member", warnerId);
        HttpUtils.xOverallHttpPost(this, MyUrl.DELETE_WARNS, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showToast(AddRemindsActivity.this, "删除成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    private void save(String isStep) {
        String content = remindItemContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(this, "提醒内容不能为空");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", familyId);
        map.put("warns_content", content);
        map.put("is_step", isStep);
        String time = remindItemTime.getText().toString();
        if (time.equals("即刻推送")) {
            map.put("send_time", "0");
        } else {
            map.put("send_time", time);
        }
//        String repeat = remindItemRepeat.getText().toString();
        if (repeatTime.equals("永不")) {
            map.put("repeat_time", "0");
        } else {
            map.put("repeat_time", repeatTime);
        }
        map.put("warner", warnerId);
        HttpUtils.xOverallHttpPost(this, MyUrl.ADD_THING_WARNS, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showToast(AddRemindsActivity.this, "添加成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    private void edit(String isStep) {
        String content = remindItemContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showToast(this, "提醒内容不能为空");
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", familyId);
        map.put("warns_id", warnsId);
        map.put("warns_content", content);
        map.put("is_step", isStep);
        String time = remindItemTime.getText().toString();
        if (time.equals("即刻推送")) {
            map.put("send_time", "0");
        } else {
            map.put("send_time", time);
        }
//        String repeat = remindItemRepeat.getText().toString();
        if (repeatTime.equals("永不")) {
            map.put("repeat_time", "0");
        } else {
            map.put("repeat_time", repeatTime);
        }
        map.put("warner", warnerId);
        HttpUtils.xOverallHttpPost(this, MyUrl.EDIT_THING_WARNS, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showToast(AddRemindsActivity.this, "修改成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTENT && resultCode == Activity.RESULT_OK) {
            String content = data.getStringExtra("remind_content");
            remindItemContent.setText(content);
        }
        if (requestCode == REQUEST_CODE_REPEAT && resultCode == Activity.RESULT_OK) {
            ArrayList<String> repeatArr = data.getStringArrayListExtra("remind_repeat");
            StringBuilder builder = new StringBuilder();
            for (String week : repeatArr) {
                builder.append(week);
                builder.append(",");
            }
            int length = builder.length();
            if (length > 0) {
                builder.deleteCharAt(length - 1);
            }
            repeatTime = builder.toString();
            if (repeatTime.contains("周一,周二,周三,周四,周五,周六,周日")) {
                remindItemRepeat.setText(repeatTime.replace("周一,周二,周三,周四,周五,周六,周日", "每天"));
            } else if (repeatTime.contains("周一,周二,周三,周四,周五")) {
                remindItemRepeat.setText(repeatTime.replace("周一,周二,周三,周四,周五", "工作日"));
            } else {
                remindItemRepeat.setText(repeatTime);
            }
        }
        if (requestCode == REQUEST_CODE_TARGET && resultCode == Activity.RESULT_OK) {
            String warnerName = data.getStringExtra("warner_name");
            warnerId = data.getStringExtra("warner_id");
            remindItemTarget.setText(warnerName);
        }
    }

    @Override
    public void timePicker(String time) {
        remindItemTime.setText(time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void okBack() {
        delete();
    }

    @Override
    public void remindSync(int item, String itemName) {
        String isStep = "";
        if (item == 0) {
            isStep = "1";
        } else if (item == 1) {
            isStep = "2";
        }
        if (addRemind) {
            save(isStep);
        } else {
            edit(isStep);
        }
    }
}
