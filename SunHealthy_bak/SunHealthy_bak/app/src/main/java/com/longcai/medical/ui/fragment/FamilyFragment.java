package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.FamilyPagerAdapter;
import com.longcai.medical.bean.Family;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.GloabeActivitySecond;
import com.longcai.medical.ui.activity.LoginActivity;
import com.longcai.medical.ui.activity.family.CreateFamilyActivity;
import com.longcai.medical.ui.activity.family.FamilyPageFragment;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.FileUtils;
import com.longcai.medical.utils.GsonUtils;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 家庭列表界面
 *
 * @author Administrator ajiang 2017.05.19
 */

public class FamilyFragment extends BaseFragment {
//    private static final String TAG = "FamilyFragment";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.title_right_iv)
    ImageView familyFgPlus;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.family_fg_rl_none)
    RelativeLayout familyFgRlNone;
//    @BindView(R.id.family_fg_rl)
//    RelativeLayout familyFgRl;
    @BindView(R.id.family_fg_vp)
    ViewPager familyFgVp;
    @BindView(R.id.family_fg_join)
    ImageView familyFgJoin;
    @BindView(R.id.family_fg_establish)
    ImageView familyFgEstablish;
    @BindView(R.id.fg_tv1)
    TextView fgTv1;
    @BindView(R.id.linear)
    LinearLayout linear;
    Unbinder unbinder;
    private List<Family> familyLists = new ArrayList<>();
    private Intent intent;
    private View view_pop;
    PopupWindow pop;
    private List<FamilyPageFragment> fragList = new ArrayList<>();
    private int resfreshPosition = -1;

    /**
     * Fragment当前状态是否可见
     */
    protected boolean mCurFragmentisVisible = false;
    private List<ImageView> mPoint =new ArrayList<>();

    //setUserVisibleHint是在onCreateView之前调用的
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d("setUserVisibleHint-Family", String.valueOf(isVisibleToUser));
        try {
            if (isVisibleToUser) {
                mCurFragmentisVisible = true;
                if (MyApplication.myPreferences.readToken().equals("-1")) {
//                    familyFgRl.setVisibility(View.GONE);
                    familyFgVp.setVisibility(View.GONE);
                    linear.setVisibility(View.GONE);
                    familyFgRlNone.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.GONE);
                } else {
                    if (!familyLists.isEmpty()) {
//                        familyFgRl.setVisibility(View.VISIBLE);
                        familyFgVp.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.VISIBLE);
                        familyFgRlNone.setVisibility(View.GONE);
                        linear.setVisibility(View.VISIBLE);
                        initListData();
                    }
                    getServiceData();
                }
            } else {
                mCurFragmentisVisible = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!MyApplication.myPreferences.readToken().equals("-1")) {
            getFamilyDataFromLocal();
            getServiceData();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (mCurFragmentisVisible) {
//            getServiceData();
//        }
    }

    public void refreshFamilyList() {
        getServiceData();
    }

    public void refreshFamilyList(int position) {
        resfreshPosition = position;
        getServiceData();
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        titleTv.setText("家庭");
        titleRightTv.setVisibility(View.GONE);
        familyFgPlus.setVisibility(View.VISIBLE);
        familyFgPlus.setImageResource(R.mipmap.plus);
        titleBack.setVisibility(View.GONE);
        intent = new Intent(mActivity, GloabeActivitySecond.class);
//        getFamilyDataFromLocal();
    }

    private void getFamilyDataFromLocal() {
        String familyListStr = FileUtils.readFromFile(getActivity(), "FamilyData");
        if (!TextUtils.isEmpty(familyListStr)) {
            LogUtils.d("FamilyListFromLocal", familyListStr);
            List<Family> familyList = GsonUtils.jsonToList(familyListStr, Family.class);
            if (null == familyList || familyList.size() <= 0) {
//                familyFgRl.setVisibility(View.GONE);
                familyFgVp.setVisibility(View.GONE);
                linear.setVisibility(View.GONE);
                familyFgRlNone.setVisibility(View.VISIBLE);
                linear.setVisibility(View.GONE);
            } else {
//                familyFgRl.setVisibility(View.VISIBLE);
                familyFgVp.setVisibility(View.VISIBLE);
                linear.setVisibility(View.VISIBLE);
                familyFgRlNone.setVisibility(View.GONE);
                linear.setVisibility(View.VISIBLE);
                if (familyLists.size() > 0) {
                    familyLists.clear();
                }
                familyLists.addAll(familyList);
                initListData();
            }
        } else {
//            familyFgRl.setVisibility(View.GONE);
            familyFgVp.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);
            familyFgRlNone.setVisibility(View.VISIBLE);
            linear.setVisibility(View.GONE);
        }
    }

    //获取家庭列表网络数据
    private void getServiceData() {
        if (MyApplication.myPreferences.readToken().equals("-1")) {
//            familyFgRl.setVisibility(View.GONE);
            familyFgVp.setVisibility(View.GONE);
            linear.setVisibility(View.GONE);
            familyFgRlNone.setVisibility(View.VISIBLE);
            linear.setVisibility(View.GONE);
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.FAMILY_LIST, map, Family.class, new HttpUtils.OnxHttpWithListResultCallBack<Family>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<Family> mFamilyList) {
                try {
                    FileUtils.writeToFile(getActivity(), "FamilyData", GsonUtils.GsonString(mFamilyList));
                    if (mFamilyList != null && mFamilyList.size() > 0) {
//                        familyFgRl.setVisibility(View.VISIBLE);
                        familyFgVp.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.VISIBLE);
                        familyFgRlNone.setVisibility(View.GONE);
                        linear.setVisibility(View.VISIBLE);
                        if (familyLists.size() > 0) {
                            familyLists.clear();
                        }
                        familyLists.addAll(mFamilyList);
                        initListData();
                    } else {
//                        familyFgRl.setVisibility(View.GONE);
                        familyFgVp.setVisibility(View.GONE);
                        linear.setVisibility(View.GONE);
                        familyFgRlNone.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (resfreshPosition > 0) {
                        familyFgVp.setCurrentItem(resfreshPosition);
                    }
                    resfreshPosition= -1;
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 11011) {
                    if (!familyLists.isEmpty()) {
                        familyLists.clear();
                    }
                }
                if (!familyLists.isEmpty()) {
//                    familyFgRl.setVisibility(View.VISIBLE);
                    familyFgVp.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.VISIBLE);
                    familyFgRlNone.setVisibility(View.GONE);
                    linear.setVisibility(View.VISIBLE);
                    initListData();
                } else {
//                if (code == 10080) {
                    try {
//                        familyFgRl.setVisibility(View.GONE);
                        familyFgVp.setVisibility(View.GONE);
                        linear.setVisibility(View.GONE);
                        familyFgRlNone.setVisibility(View.VISIBLE);
                        linear.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                }
                    LogUtils.e("family_list:" + code + msg);
                }
            }
        });
    }

    //设置家庭列表数据
    public void initListData() {
        if (!fragList.isEmpty()) {
            fragList.clear();
        }
        if (!mPoint.isEmpty()){
            mPoint.clear();
            linear.removeAllViews();
        }
        for (int i = 0; i < familyLists.size(); i++) {
            fragList.add(FamilyPageFragment.newInstance(i, familyLists));
            ImageView iv1 = new ImageView(getActivity());
            iv1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv1.setImageResource(R.mipmap.lunbodian_weixuanzhong);
            iv1.setPadding(10,0,0,0);
            mPoint.add(iv1);
            linear.addView(iv1);
        }
        mPoint.get(0).setImageResource(R.mipmap.lunbodian_xuanzhong);

        //ViewPager适配器
        FamilyPagerAdapter familyPagerAdapter = new FamilyPagerAdapter(getChildFragmentManager(), fragList);
        familyFgVp.setAdapter(familyPagerAdapter);
        familyPagerAdapter.notifyDataSetChanged();
        familyFgVp.addOnPageChangeListener(onPageChangeListener);
    }

    @OnClick({R.id.title_right_iv, R.id.family_fg_establish, R.id.family_fg_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.family_fg_establish:
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    Intent intent2 = new Intent(mActivity, CreateFamilyActivity.class);
                    startActivity(intent2);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.family_fg_join:
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    intent.putExtra(Constant.FRAGMENT_MARK, Constant.FAM_SEARCH_LIST);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.title_right_iv:
                view_pop = View.inflate(mActivity, R.layout.family_popup, null);
                pop = new ShowPopupWindow(mActivity).showPopup(view_pop);
                pop.setAnimationStyle(0);
                pop.showAsDropDown(familyFgPlus);
                pop.update();
                initPopClick();
                break;
        }
    }

    private void login() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        MyApplication.source = 2;
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.pop_enter_anim, 0);
    }

    //viewpager滑动监听
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            for (ImageView iv : mPoint) {
                iv.setImageResource(R.mipmap.lunbodian_weixuanzhong);
            }
            mPoint.get(position % familyLists.size()).setImageResource(R.mipmap.lunbodian_xuanzhong);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void initPopClick() {
        /*创建家庭点击事件*/
        LinearLayout family_popup_family_establish = (LinearLayout) view_pop.findViewById(R.id.family_popup_family_establish);
        LinearLayout family_popup_family_add = (LinearLayout) view_pop.findViewById(R.id.family_popup_family_add);
        family_popup_family_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    intent.putExtra(Constant.FRAGMENT_MARK, Constant.FAM_SEARCH_LIST);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });
        family_popup_family_establish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    Intent intent1 = new Intent(mActivity, CreateFamilyActivity.class);
                    startActivity(intent1);
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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

}
