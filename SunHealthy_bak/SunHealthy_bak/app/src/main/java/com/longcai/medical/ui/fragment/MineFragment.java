package com.longcai.medical.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetUserInfoResult;
import com.longcai.medical.bean.MsgInfo;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.LoginActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.NewPersonInfoActivity;
import com.longcai.medical.ui.activity.person.MessageListActivity;
import com.longcai.medical.ui.activity.person.PersonDeviceActivity;
import com.longcai.medical.ui.activity.person.SettingActivity;
import com.longcai.medical.ui.activity.person.healthdoc.HealthDocActivity;
import com.longcai.medical.ui.activity.person.help.HelpInfoActivity;
import com.longcai.medical.ui.view.popupwindow.ServicePopupWindow;
import com.longcai.medical.utils.GlideCircleTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/7/3.
 */

public class MineFragment extends BaseFragment implements ServicePopupWindow.ICallPhoneBack {
    @BindView(R.id.mine_name_tx)
    TextView mineNameTx;
    @BindView(R.id.mine_jkda_btn)
    RelativeLayout mineJkdaBtn;
    @BindView(R.id.mine_sb_btn)
    RelativeLayout mineSbBtn;
    @BindView(R.id.mine_bzfk_btn)
    RelativeLayout mineBzfkBtn;
    @BindView(R.id.mine_setting_btn)
    RelativeLayout mineSettingBtn;
    @BindView(R.id.mine_head_iv)
    ImageView mineHeadIv;
    @BindView(R.id.mine_lxkf_btn)
    RelativeLayout mineLxkfBtn;
    @BindView(R.id.mine_notice)
    ImageView mineNotice;
    @BindView(R.id.mine_head_layout)
    RelativeLayout mineHeadLayout;
    private String tel;
    private Unbinder unbinder;
    private List<MsgInfo.MsgInfoBean> msgInfoList = new ArrayList<>();
    protected boolean mCurFragmentisVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d("setUserVisibleHint-Mine", String.valueOf(isVisibleToUser));
        if (isVisibleToUser) {
            mCurFragmentisVisible = true;
            refreshMessage();
        } else {
            mCurFragmentisVisible = false;
        }
    }

    public void refreshMessage() {
        if (null != getActivity()) {
            ((MainActivity) getActivity()).refreshMsg();
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mineView = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, mineView);
        Bundle b = getArguments();
        if (null != b) {
            boolean hintMyorder = b.getBoolean("hint_myorder", false);
            if (hintMyorder) {
                setMyOrderHint(hintMyorder);
            }
            List<MsgInfo.MsgInfoBean> msgInfos = b.getParcelableArrayList("notice_list");
            if (null != msgInfos && msgInfos.size() > 0) {
                getNotice(msgInfos);
            }
        }
        return mineView;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
    }

    @Override
    public void LoadData() {
    }

    public boolean setMyOrderHint(boolean isHinted) {
        return false;
    }

    //判断系统和家庭消息是否已读
    public boolean getNotice(List<MsgInfo.MsgInfoBean> msgInfo) {
        if (null == mineNotice) {
            return false;
        } else {
            if (msgInfo != null) {
                mineNotice.setImageResource(R.mipmap.icon_notice);
                if (msgInfoList.size() > 0) {
                    msgInfoList.clear();
                }
                msgInfoList.addAll(msgInfo);
                for (int i = 0; i < msgInfoList.size(); i++) {
                    String notice = msgInfoList.get(i).getNotice();
                    if (notice.equals("0")) {
                        mineNotice.setImageResource(R.mipmap.icon_notice_red);
                        break;
                    } else {
                        mineNotice.setImageResource(R.mipmap.icon_notice);
                    }
                }
            }
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            mineNameTx.setText("请登录");
            Glide.with(getActivity()).load(R.mipmap.head)
                    .transform(new GlideCircleTransform(getActivity(), 2, getActivity().getResources().getColor(R.color.head_border)))
                    .into(mineHeadIv);
        } else if (StringUtils.isEmpty(MyApplication.myPreferences.readNickName()) && MyApplication.myPreferences.readavatar().equals("-1")) {
            getUserInfoData();
        } else {
            if (!StringUtils.isEmpty(MyApplication.myPreferences.readNickName())) {
                mineNameTx.setText(MyApplication.myPreferences.readNickName());
            } else {
//                mineNameTx.setText("请登录");
            }
            if (!MyApplication.myPreferences.readavatar().equals("-1")) {
                Glide.with(getActivity()).load(MyApplication.myPreferences.readavatar()).placeholder(R.mipmap.head)
                        .transform(new GlideCircleTransform(getActivity(), 2, getActivity().getResources().getColor(R.color.head_border)))
                        .into(mineHeadIv);
            }
        }

    }

    //获取会员资料
    public void getUserInfoData() {
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.USER_GET_INFO, map, GetUserInfoResult.class, new HttpUtils.OnxHttpCallBack<GetUserInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetUserInfoResult getUserInfoResult) {
                Glide.with(getActivity()).load(getUserInfoResult.getMember_avatar()).placeholder(R.mipmap.head)
                        .transform(new GlideCircleTransform(getActivity(), 2, getActivity().getResources().getColor(R.color.head_border))).into(mineHeadIv);
                mineNameTx.setText(getUserInfoResult.getMember_name());
                MyApplication.myPreferences.savePhone(getUserInfoResult.getMember_mobile());
                MyApplication.myPreferences.saveNickName(getUserInfoResult.getMember_name());
                MyApplication.myPreferences.saveavatar(getUserInfoResult.getMember_avatar());
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.e("获取会员资料" + code + msg);
                mineNameTx.setText("请登录");
                Glide.with(getActivity()).load(R.mipmap.head)
                        .transform(new GlideCircleTransform(getActivity(), 2, getActivity().getResources().getColor(R.color.head_border)))
                        .into(mineHeadIv);
            }
        });
    }

    @OnClick({R.id.mine_notice, R.id.mine_head_layout, R.id.mine_jkda_btn, R.id.mine_sb_btn, R.id.mine_lxkf_btn, R.id.mine_bzfk_btn,
            R.id.mine_setting_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_notice://消息
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    Intent intent = new Intent(getActivity(), MessageListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("msgInfoList", (Serializable) msgInfoList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.mine_head_layout://跳转到账户信息
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), NewPersonInfoActivity.class));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.mine_jkda_btn://健康档案
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), HealthDocActivity.class));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.mine_sb_btn://我的设备
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), PersonDeviceActivity.class));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.mine_lxkf_btn://联系客服
                ServicePopupWindow serviceWindow = new ServicePopupWindow(getActivity());
                serviceWindow.showPopupWindow(mineLxkfBtn);
                serviceWindow.setICallPhoneBack(this);
                break;
            case R.id.mine_bzfk_btn://帮助与反馈
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), HelpInfoActivity.class));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.mine_setting_btn://设置
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        MyApplication.source = 3;
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.pop_enter_anim, 0);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private void CallPhone(String number) {
        if (TextUtils.isEmpty(number)) {
            // 提醒用户
            // 注意：在这个匿名内部类中如果用this则表示是View.OnClickListener类的对象，
            // 所以必须用MainActivity.this来指定上下文环境。
            ToastUtils.showToast(getContext(), "号码不能为空！");
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + number); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
            getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone(tel);
                } else {
                    // 授权失败！
                    ToastUtils.showToast(getContext(), "授权失败！");
                }
                break;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void callPhoneBack(String tel) {
        this.tel = tel;
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            // 已经获得授权，可以打电话
            CallPhone(tel);
        }
    }
}

