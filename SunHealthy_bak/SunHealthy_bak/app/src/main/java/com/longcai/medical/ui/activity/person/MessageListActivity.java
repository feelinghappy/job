package com.longcai.medical.ui.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.MessageAdapter;
import com.longcai.medical.bean.MsgInfo;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/31.
 * 系统通知列表和家庭邀请消息列表
 */

public class MessageListActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.message_lv)
    ListView messageLv;
    @BindView(R.id.family_lv)
    ListView familyLv;
    @BindView(R.id.layout_message_no)
    RelativeLayout layoutMessageNo;
    @BindView(R.id.layout_message_have)
    LinearLayout layoutMessageHave;
    public final static String System_Info = "SystemInfo";
    private Intent intent;
    private HashMap<String, String> map = new HashMap<>();
    private Handler handler;
    private MessageAdapter messageAdapter;
    private boolean isSystemList = true;//是家庭list->false或者系统list->true
    private Unbinder unbinder;
    private LayoutInflater mInflater;
    private View delete_pop;
    private TextView deleteOk;
    private TextView deleteCancel;
    private PopupWindow popupWindow;
    private int sysPosition;
    private int famPosition;
    private List<MsgInfo.MsgInfoBean> msgInfoList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(MessageListActivity.this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMsgData();
    }

    private void initData() {
        titleTv.setText("系统通知");
        titleRightTv.setVisibility(View.GONE);
        delete_pop = mInflater.inflate(R.layout.popop_delete_member, null);
        deleteOk = (TextView) delete_pop.findViewById(R.id.delete_ok);
        deleteCancel = (TextView) delete_pop.findViewById(R.id.delete_cancel);
    }

    private void getData() {
       /* msgInfoList = (List<MsgInfo.MsgInfoBean>) getIntent().getExtras().getSerializable("msgInfoList");
        //MainActivity中系统消息和家庭消息集合添加到了 msgList，
        if (msgInfoList.size() == 0) {
            layoutMessageHave.setVisibility(View.GONE);
            layoutMessageNo.setVisibility(View.VISIBLE);
        } else {
            //系统和家庭消息列表
            getMsgData();
        }*/
    }

    private void getMsgData() {
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(this, MyUrl.MESSAGE_SYSTEM_INFO, map, MsgInfo.class, new HttpUtils.OnxHttpCallBack<MsgInfo>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(MsgInfo msgInfo) {
                msgInfoList = new ArrayList<MsgInfo.MsgInfoBean>();
                List<MsgInfo.MsgInfoBean> familyInfo = msgInfo.getFamily_info();
                List<MsgInfo.MsgInfoBean> systemInfo = msgInfo.getSystem_info();
                if (msgInfoList.size() > 0) {
                    msgInfoList.clear();
                }
                if (familyInfo != null){
                    msgInfoList.addAll(familyInfo);
                }
                if (systemInfo != null){
                    msgInfoList.addAll(systemInfo);
                }
                if (msgInfoList.size() > 0) {
                    processMsgData(msgInfo);
                }
            }

            @Override
            public void onFail(int code, String msg) {
               if (msg.equals("empty data")){
                   layoutMessageHave.setVisibility(View.GONE);
                   layoutMessageNo.setVisibility(View.VISIBLE);
               }
            }
        });
    }

    private void processMsgData(MsgInfo msgInfo) {
        intent = new Intent(this, MessagesActivity.class);
        messageAdapter = new MessageAdapter(MessageListActivity.this, msgInfoList);
        messageLv.setAdapter(messageAdapter);
        messageLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sysPosition = position;
                LogUtils.d("msgInfoList的position： " + sysPosition);
                String message_id = msgInfoList.get(position).getMessage_id();
                String message_type = msgInfoList.get(position).getMessage_type();
                LogUtils.d("message_id=" + message_id + "message_type=" + message_type);
                startActivityForResult(intent.putExtra("message_id", message_id)
                        .putExtra("message_type", message_type), 1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        messageLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message_id = msgInfoList.get(position).getMessage_id();
                String message_type = msgInfoList.get(position).getMessage_type();
                popupWindow = new ShowPopupWindow(MessageListActivity.this).showPopup(delete_pop);
                popupWindow.showAtLocation(messageLv, Gravity.CENTER, 0, 0);
                initPop(message_id, message_type, position);

                return true;
            }
        });
    }

    private void initPop(final String message_id, final String message_type, final int position) {
        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setDeleteData(message_id, message_type, position);
            }
        });
        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    //删除消息
    private void setDeleteData(String message_id, String message_type, final int position) {
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("message_id", message_id);
        map.put("message_type", message_type);
        HttpUtils.xOverallHttpPost(this, MyUrl.DELETE_INFO, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String s) {
                msgInfoList.remove(position);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.d(code + msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            //读取消息内容返回
            case Constant.RESULT_READ_NOTICE:
               /* msgInfoList.get(sysPosition).setNotice("1");
                messageAdapter.notifyDataSetChanged();*/
//                messageAdapter.getItem(sysPosition).setNotice("1");
//                messageAdapter.addItems(msgInfoList);
//                messageLv.setAdapter(messageAdapter);
                break;
        }
    }

    @OnClick({R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent.putExtra("refresh_myhint", true));
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
