package com.longcai.medical.ui.activity.family;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.FamilysAdapter;
import com.longcai.medical.bean.Family;
import com.longcai.medical.bean.FamilyRemind;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.remind.RemindSetActivity;
import com.longcai.medical.utils.GlideRoundWithTopTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/15.
 */

public class FamilyPageFragment extends BaseFragment {

    @BindView(R.id.fg_family_page)
    ImageView fgFamilyPage;
    @BindView(R.id.fg_family_name)
    TextView fgFamilyName;
    @BindView(R.id.fg_family_count)
    TextView fgFamilyCount;
    @BindView(R.id.fg_family_lv)
    ListView fgFamilyLv;
    Unbinder unbinder;
    @BindView(R.id.fg_family_left)
    ImageView fgFamilyLeft;
    @BindView(R.id.fg_family_right)
    ImageView fgFamilyRight;
    @BindView(R.id.family_layout_detail)
    RelativeLayout familyLayoutDetail;
    @BindView(R.id.fg_family_none)
    ImageView fgFamillyNone;
    @BindView(R.id.family_btn_detail)
    ImageView familyBtnDetail;
    private List<Family> familyLists;
    private int position;
    private String family_id;
    private String family_member_id;
    private String mRobot_imei; //机器人编码
    private boolean isVisibleToUser = false;
    private String ridToCall;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
//        if (isVisibleToUser) {
//            try {
//                initRemindList();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (this.isVisibleToUser) {
//            try {
//                initRemindList();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static FamilyPageFragment newInstance(int position, List<Family> familyLists) {
        FamilyPageFragment fragment = new FamilyPageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("familyLists", (Serializable) familyLists);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_family_view, null);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
            familyLists = (List<Family>) getArguments().getSerializable("familyLists");
        }
//        initIcon();
        return view;
    }

    private void initIcon() {
        if (position == 0) {
            fgFamilyLeft.setVisibility(View.GONE);
        } else if (position == familyLists.size() - 1) {
            fgFamilyRight.setVisibility(View.GONE);
        } else {
            fgFamilyLeft.setVisibility(View.VISIBLE);
            fgFamilyRight.setVisibility(View.VISIBLE);
        }
        if (familyLists.size() == 1){
            fgFamilyLeft.setVisibility(View.GONE);
            fgFamilyRight.setVisibility(View.GONE);
        }
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        fgFamilyName.setText(familyLists.get(position).getFamily_name());
        family_id = familyLists.get(position).getFamily_id();
        family_member_id = familyLists.get(position).getFamily_member_id();
        String member_count = familyLists.get(position).getMember_count();
        fgFamilyCount.setText("家庭成员  " + member_count);
        mRobot_imei = familyLists.get(position).getRobot_imei();
        String thumb = familyLists.get(position).getThumb();
        if (thumb != null){
            if (thumb.equals("1")) {
                loadImg(R.mipmap.family_list1);
//                fgFamilyPage.setImageResource(R.mipmap.family_list1);
            } else if (thumb.equals("2")) {
                loadImg(R.mipmap.family_list2);
//                fgFamilyPage.setImageResource(R.mipmap.family_list2);
            } else if (thumb.equals("3")) {
                loadImg(R.mipmap.family_list3);
//                fgFamilyPage.setImageResource(R.mipmap.family_list3);
            } else {
                LogUtils.e("封面返回结果" + familyLists.get(position).getThumb());
                Glide.with(getActivity()).load(familyLists.get(position).getThumb()).placeholder(R.mipmap.test_zhanweitu)
                        .transform(new GlideRoundWithTopTransform(getActivity()))
                        .into(fgFamilyPage);
            }
        }
        FamilyRemind familyRemind = new FamilyRemind();
        familyRemind.setWarns_content("欢迎加入 " + familyLists.get(position).getFamily_name());
        List<FamilyRemind> remindList = new ArrayList<FamilyRemind>();
        remindList.add(familyRemind);
        FamilysAdapter adapter = new FamilysAdapter(getActivity(), remindList);
        fgFamilyLv.setAdapter(adapter);
        //添加家庭提醒列表
        initRemindList();
    }
    private void loadImg(int resourceId){
        Glide.with(getActivity()).load(resourceId).placeholder(R.mipmap.test_zhanweitu)
                .transform(new GlideRoundWithTopTransform(getActivity()))
                .into(fgFamilyPage);
    }
    private void getFamilyDetailData(String family_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", family_id);
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.FAMILY_INFO, map, GetFamilyInfoBean.class, new HttpUtils.OnxHttpCallBack<GetFamilyInfoBean>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetFamilyInfoBean getFamilyInfoBean) {
                GetFamilyInfoBean.FamilyInfoBean familyInfoBean = getFamilyInfoBean.getFamily_info();
                if (null != familyInfoBean) {
                    mRobot_imei = familyInfoBean.getRobot_imei();
                }
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    public void initRemindList() {
        if (TextUtils.isEmpty(family_id)) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", family_id);
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.FAMILY_REMIND_LIST, map,FamilyRemind.class,new HttpUtils.OnxHttpWithListResultCallBack<FamilyRemind>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<FamilyRemind> familyRemindList) {
//                FamilyRemind familyRemind = new FamilyRemind();
//                familyRemind.setWarns_content("欢迎加入 " + familyLists.get(position).getFamily_name());
//                List<FamilyRemind> remindList = new ArrayList<FamilyRemind>();
//                remindList.add(familyRemind);
//                remindList.addAll(familyRemindList);
                FamilysAdapter adapter = (FamilysAdapter) fgFamilyLv.getAdapter();
                if (null == adapter) {
                    adapter = new FamilysAdapter(getActivity(), familyRemindList);
                    fgFamilyLv.setAdapter(adapter);
                } else {
                    adapter.addRemindList(familyRemindList);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.e("family_remind_list:" + code + msg);
                if (code == 10194) {
//                    FamilysAdapter adapter = (FamilysAdapter) fgFamilyLv.getAdapter();
//                    try {
//                        adapter.clearItems();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    FamilyRemind familyRemind = new FamilyRemind();
//                familyRemind.setWarns_content("欢迎加入 " + familyLists.get(position).getFamily_name());
//                List<FamilyRemind> remindList = new ArrayList<FamilyRemind>();
//                remindList.add(familyRemind);
//                    FamilysAdapter familysAdapter = new FamilysAdapter(getActivity(), remindList);
//                    fgFamilyLv.setAdapter(familysAdapter);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.fg_family_page, R.id.fg_family_video, R.id.fg_family_remind,R.id.family_layout_detail,R.id.family_btn_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.family_btn_detail://家庭详情
                pageToDetail();
                break;
            case R.id.fg_family_page://家庭封面
                pageToDetail();
                break;
            case R.id.fg_family_video://开启视频

                break;
            case R.id.fg_family_remind://设置提醒
                startActivity(new Intent(getActivity(), RemindSetActivity.class).putExtra("family_id", family_id)
                        .putExtra("family_member_id", family_member_id));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    private void pageToDetail() {
        startActivity(new Intent(getActivity(), FamilyDetailActivity.class)
                .putExtra("family_id", family_id).putExtra("family_position", position)
                .putExtra("family_member_id", family_member_id));
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

}
