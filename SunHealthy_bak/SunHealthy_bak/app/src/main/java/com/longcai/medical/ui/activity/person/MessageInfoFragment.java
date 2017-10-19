package com.longcai.medical.ui.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.FamilyMsgBody;
import com.longcai.medical.bean.ReadMessageBean;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/31.
 * 系统消息详情
 */

public class MessageInfoFragment extends BaseFragment {
    public static final String Message_id = "message_id";
    public static final String Message_type = "message_type";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    Unbinder unbinder;
    @BindView(R.id.tv_system_title)
    TextView tvSystemTitle;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;
    @BindView(R.id.tv_system_body)
    TextView tvSystemBody;
    @BindView(R.id.btn_family_agree)
    Button btnFamilyAgree;
    private String messageId;
    private String messageType;
    private HashMap<String, String> map = new HashMap<>();

    public static MessageInfoFragment newInstance(String messageId, String messageType) {
        MessageInfoFragment fragment = new MessageInfoFragment();
        Bundle args = new Bundle();
        args.putString(Message_id, messageId);
        args.putString(Message_type, messageType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            messageId = getArguments().getString(Message_id);
            messageType = getArguments().getString(Message_type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        if (messageType.equals("1")){
            titleTv.setText("系统消息");
        }else {
            titleTv.setText("家庭消息");
        }
        titleRightTv.setVisibility(View.GONE);
        setMessageData();
    }

    //读取消息内容
    private void setMessageData() {
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("message_id", messageId);//messageId
        map.put("message_type", messageType);//messageType
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.READ_INFO, map, ReadMessageBean.class, new HttpUtils.OnxHttpCallBack<ReadMessageBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(ReadMessageBean readMessage) {
                processReadMsg(readMessage);
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.d(code + msg);
            }
        });
    }
    //读取消息返回结果
    private void processReadMsg(ReadMessageBean readMessage) {
        if (StringUtils.isEmpty(readMessage.getMessage_title())) {
            if (messageType.equals("1")){
                tvSystemTitle.setText("系统通知");
            }else {
                tvSystemTitle.setText("家庭消息");
            }
        } else {
            tvSystemTitle.setText(readMessage.getMessage_title());
        }
        tvSystemTime.setText("发送时间: " + StringUtils.toTime(readMessage.getMessage_time()));
        String message_body = readMessage.getMessage_body();
        LogUtils.d("messageType=  " + messageType);
        if (messageType.equals("2")) {//1系统消息 2家庭消息
            FamilyMsgBody familyMsgBody = new Gson().fromJson(message_body, FamilyMsgBody.class);
            btnFamilyAgree.setVisibility(View.VISIBLE);
            String memeberName = familyMsgBody.getMember_name();
            String famillyName = familyMsgBody.getFamily_name();
            String isInvite = String.valueOf(familyMsgBody.getIs_invite());
            int is_join = familyMsgBody.getIs_join();//是否加入过1 加入 0 未加入
            if (is_join == 1){
                btnFamilyAgree.setText("已加入");
                btnFamilyAgree.setClickable(false);
            }
            if (isInvite.equals("1")) {
                isInvite = "邀请您";
            }else if (isInvite.equals("2")){
                isInvite = "申请";
            }
            tvSystemBody.setText("您的家人 " + memeberName + "  " + isInvite + "进入 " + famillyName);
        } else {
            tvSystemBody.setText(message_body);
        }
        // 设置消息已读
        Constant.isReadNotice = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title_back, R.id.btn_family_agree})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                Intent intent = new Intent();
                getActivity().setResult(Constant.RESULT_READ_NOTICE, intent);
                getActivity().finish();
                break;
            case R.id.btn_family_agree://同意
                joinFamilyData();
                break;
        }
    }
    //同意加入家庭
    private void joinFamilyData() {
        map.put("token", MyApplication.myPreferences.readToken());//messageType
        map.put("message_id", messageId);//messageType
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.MESSAGE_JOIN_FAMILY, map,new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String object) {
                Intent intent = new Intent();
                getActivity().setResult(Constant.RESULT_READ_NOTICE, intent);
                getActivity().finish();
                MyApplication.myPreferences.saveAgree(true);
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11203){//已经加入了
                    getActivity().finish();
                    MyApplication.myPreferences.saveAgree(true);
                }else if (code == 11205){
                    ToastUtils.showToast(getActivity(),"非家庭创建者无法编辑");
                }
                LogUtils.d(code + msg);
            }
        });
    }

    // 返回键按下时会被调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            // TODO
            Intent intent = new Intent();
            getActivity().setResult(Constant.RESULT_READ_NOTICE, intent);
            getActivity().finish();
            return true;
        }
        return false;
    }
}
