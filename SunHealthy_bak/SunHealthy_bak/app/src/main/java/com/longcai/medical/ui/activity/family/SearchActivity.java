package com.longcai.medical.ui.activity.family;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.SearchMemberBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/7.
 * 搜索会员
 */

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.ed_phonenum)
    EditText edPhonenum;
    @BindView(R.id.btn_cancel)
    ImageView btnCancel;
    @BindView(R.id.btn_search)
    TextView tvSearch;
    @BindView(R.id.member_search_rl)
    RelativeLayout memberSearchRl;
    @BindView(R.id.member_head)
    ImageView memberHead;
    @BindView(R.id.member_name)
    TextView memberName;
    @BindView(R.id.member_phone)
    TextView memberPhone;
    @BindView(R.id.btn_invite)
    Button btnInvite;
    @BindView(R.id.member_details)
    AutoLinearLayout memberDetails;
    @BindView(R.id.nofind)
    ImageView nofind;
    @BindView(R.id.bottomLine)
    View bottomLine;

    private int intentTag, intentTag2, intentTag3;
    private ArrayList<String> mList = new ArrayList<>();
    private String family_id;
    private HashMap<String, String> map = new HashMap<String, String>();
    private HashMap<String, String> inviteMap = new HashMap<String, String>();
    private String member_id;
    private String member_id_pinjie;
    private int code;
    private SearchMemberBean searchMemberResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_member);
        ButterKnife.bind(this);
        titleTv.setText("搜索");
        titleRightTv.setVisibility(View.GONE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) edPhonenum.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(edPhonenum, 0);
                           }
                       },
                450);
    }

    @OnClick({R.id.title_back, R.id.btn_cancel, R.id.btn_search, R.id.btn_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_cancel:
                edPhonenum.setText("");
                break;
            case R.id.btn_search://搜索
                if (!TextUtils.isEmpty(edPhonenum.getText().toString().trim())) {
                    if (!RegexUtils.checkMobile(edPhonenum.getText().toString().trim())) {
                        ToastUtils.showToast(this, "请输入正确的手机号");
                        return;
                    }
                }
                //搜索请求
                mySearch();
                break;
            case R.id.btn_invite://邀请
               /*获取   family_id*/
                family_id = getIntent().getStringExtra("family_id");
                //邀请
                myInvite();
                break;
        }
    }

    //邀请
    private void myInvite() {
        map.clear();
        if (member_id != null) {
//            member_id_true = member_id.substring(0, member_id.length() - 1);
//            inviteMap.put("invite_id", member_id_true);
            map.put("invite_id", member_id);
        }
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", family_id);
        HttpUtils.xOverallHttpPost(this, MyUrl.INVITE_MEMBER, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String string) {
                ToastUtils.showToast(SearchActivity.this, "已发出邀请，请等待对方确认");
                processInviteData();
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11020){
                    ToastUtils.showToast(SearchActivity.this,"家庭创建者无法加入家庭");
                }
            }
        });
    }

    //邀请返回结果
    private void processInviteData() {
        Intent intent1 = new Intent();
        if (searchMemberResult != null) {
            intent1.putExtra("member_avatar", searchMemberResult.getAvatar());
            intent1.putExtra("member_name", searchMemberResult.getNickname());
        }
        setResult(10, intent1);
        finish();
    }

    //搜索
    private void mySearch() {
        map.clear();
        nofind.setVisibility(View.GONE);
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("keyword", edPhonenum.getText().toString());
        HttpUtils.xOverallHttpPost(SearchActivity.this, MyUrl.SEARCH_MEMBER, map, SearchMemberBean.class, new HttpUtils.OnxHttpCallBack<SearchMemberBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(SearchMemberBean searchMemberBean) {
                searchMemberResult = searchMemberBean;
                processSearchData(searchMemberBean);
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11041) {
                    memberDetails.setVisibility(View.GONE);
                    bottomLine.setVisibility(View.GONE);
                    nofind.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //搜索返回结果
    private void processSearchData(SearchMemberBean searchMemberBean) {
        memberPhone.setText(searchMemberBean.getMobile());
        Glide.with(SearchActivity.this).load(searchMemberBean.getAvatar()).placeholder(R.mipmap.head).into(memberHead);
        memberName.setText(searchMemberBean.getNickname());
        memberDetails.setVisibility(View.VISIBLE);
        bottomLine.setVisibility(View.VISIBLE);
        member_id = searchMemberBean.getMember_id();
        mList.add(member_id);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < mList.size(); i++) {
            buffer.append(mList.get(i) + "|");
        }
        member_id_pinjie = buffer.toString();
        Log.e(TAG, "onResponse: " + member_id_pinjie);
    }
}
