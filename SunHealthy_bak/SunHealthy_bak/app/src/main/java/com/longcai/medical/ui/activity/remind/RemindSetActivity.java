package com.longcai.medical.ui.activity.remind;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.RemindAdapter;
import com.longcai.medical.bean.GetThingWarns;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.AddRemindsActivity;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/15.
 */

public class RemindSetActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        RemindAdapter.IOpenCloseWarn, AdapterView.OnItemLongClickListener, CustomPopupWindow.IOKBack {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_iv)
    ImageView titleRightIv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(android.R.id.list)
    ListView list;

    private final int REQUEST_CODE_ADD = 300;
    private final int REQUEST_CODE_EDIT = 301;

    private String familyId;
    private String familyMemberId;
    private int longPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_set);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleRightIv.setVisibility(View.VISIBLE);
        titleRightIv.setImageResource(R.mipmap.plus);
        titleTv.setText("设置提醒");
        familyId = getIntent().getStringExtra("family_id");
        familyMemberId = getIntent().getStringExtra("family_member_id");
        if (!TextUtils.isEmpty(familyId)) {
            getRemind();
        }
    }

    @OnClick({R.id.title_back, R.id.title_right_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.title_right_iv://添加提醒
                Intent intent = new Intent(this, AddRemindsActivity.class);
                intent.putExtra("family_id", familyId);
                intent.putExtra("family_member_id", familyMemberId);
                intent.putExtra("add_remind", true);
                startActivityForResult(intent, REQUEST_CODE_ADD);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

//    private void refreshFamilyList() {
//        if (refreshFamilyList) {
//            Intent i = new Intent(this, MainActivity.class);
//            i.putExtra("source", 2);
//            i.putExtra("family_index", familyPosition);
//            startActivity(i);
//            finish();
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//        } else {
//            finish();
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//        }
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            refreshFamilyList();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            getRemind();
        }
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            getRemind();
        }
    }

    private void getRemind() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", familyId);
        HttpUtils.xOverallHttpPost(this, MyUrl.GET_THING_WARNS, map, GetThingWarns.class, new HttpUtils.OnxHttpWithListResultCallBack<GetThingWarns>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<GetThingWarns> t) {
                RemindAdapter adapter = new RemindAdapter(RemindSetActivity.this, t);
                list.setAdapter(adapter);
                list.setOnItemClickListener(RemindSetActivity.this);
                list.setOnItemLongClickListener(RemindSetActivity.this);
                adapter.setiOpenCloseWarn(RemindSetActivity.this);
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10502) {
                    ((RemindAdapter) list.getAdapter()).clearItems();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RemindAdapter adapter = (RemindAdapter) list.getAdapter();
        GetThingWarns warns = adapter.getItem(i);
        Intent intent = new Intent(this, AddRemindsActivity.class);
        intent.putExtra("add_remind", false);
        intent.putExtra("family_member_id", familyMemberId);
        intent.putExtra("thing_warn", warns);
        startActivityForResult(intent, REQUEST_CODE_EDIT);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void openCloseWarn(int position) {
        RemindAdapter adapter = (RemindAdapter) list.getAdapter();
        final GetThingWarns warns = adapter.getItem(position);
        final String warnsState = warns.getWarns_state();
        String warnsId = warns.getWarns_id();
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        if (!TextUtils.isEmpty(warnsId)) {
            map.put("warns_id", warnsId);
        }
        map.put("family_id", familyId);
        if (!TextUtils.isEmpty(warnsState) && warnsState.equals("1")) {
            map.put("warns_state", "0");
        } else if (!TextUtils.isEmpty(warnsState) && warnsState.equals("0")) {
            map.put("warns_state", "1");
        }
        HttpUtils.xOverallHttpPost(this, MyUrl.CLOSE_OPEN_WARNS, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String s) {
                if (!TextUtils.isEmpty(warnsState) && warnsState.equals("1")) {
                    ToastUtils.showToast(RemindSetActivity.this, "关闭提醒");
                    warns.setWarns_state("0");
                } else if (!TextUtils.isEmpty(warnsState) && warnsState.equals("0")) {
                    ToastUtils.showToast(RemindSetActivity.this, "开启提醒");
                    warns.setWarns_state("1");
                }
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        CustomPopupWindow customPopupWindow = new CustomPopupWindow(this);
        customPopupWindow.showPopupWindow(titleRightIv);
        customPopupWindow.setIOKBack(this);
        longPosition = i;
        return true;
    }

    private void delete(String warnsId, String warnerId) {
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
                ToastUtils.showToast(RemindSetActivity.this, "删除成功");
                getRemind();
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    @Override
    public void okBack() {
        RemindAdapter adapter = (RemindAdapter) list.getAdapter();
        GetThingWarns warns = adapter.getItem(longPosition);
        String acceptMemberName = warns.getAccept_member_name();
        String warnWarner = warns.getWarner();
        String warnerId = "";
        if (!TextUtils.isEmpty(acceptMemberName)) {

            if (warnWarner.equals("2")) {
                warnerId = "-2";
            } else if (warnWarner.equals("3")) {
                warnerId = "-3";
            } else {
                warnerId = warns.getAccept_member();
            }
        }
        delete(warns.getWarns_id(), warnerId);
    }
}
