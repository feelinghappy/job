package com.longcai.medical.service;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcai.medical.R;
import com.longcai.medical.bean.FoodCate;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.fragment.FoodListFragment;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 二期食材的碎片
 * Created by LC-XC on 2017/3/29.
 */

public class SecondFoodMaterialFragment extends Fragment {
    @BindView(R.id.food_material_tablayout)
    TabLayout foodMaterialTablayout;
    @BindView(R.id.food_material_viewpager)
    ViewPager foodMaterialViewpager;
    Unbinder unbinder;
    private View foodView;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //页卡标题集合
    private List<String> mTitleList = new ArrayList<>();
    private int size = 0;
    //页卡视图集合
    private List<Fragment> mViewList = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();
    private SecondFoodMaterialAdapter adapter;
    private int category_id;

    // TODO: Rename and change types and number of parameters
    public static SecondFoodMaterialFragment newInstance(String param1, String param2) {
        SecondFoodMaterialFragment fragment = new SecondFoodMaterialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        foodView = inflater.inflate(R.layout.second_foodmaterial_fragment, null);
        unbinder = ButterKnife.bind(this, foodView);
        initTitle();

        return foodView;
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    //获取食材标题
    private void initTitle() {
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.GET_FOOD_CATE, map, FoodCate.class, new HttpUtils.OnxHttpWithListResultCallBack<FoodCate>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<FoodCate> foodCateList) {
                if (mTitleList.size() > 0) {
                    mTitleList.clear();
                    mViewList.clear();

                }
                for (int i = 0; i < foodCateList.size(); i++) {
                    category_id = foodCateList.get(i).getCategory_id();
                    mTitleList.add(foodCateList.get(i).getCatname());
                    mViewList.add(FoodListFragment.newInstance(i, category_id));
                }
                size = mTitleList.size();
                init(category_id);
                LogUtils.d("titlelist的size" + size);
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    private void init(int category_id) {
//        for (int i = 0; i < mTitleList.size(); i++) {
//            foodMaterialTablayout.addTab(foodMaterialTablayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
//        }
        for (int i = 0; i < mTitleList.size(); i++) {

        }
        adapter = new SecondFoodMaterialAdapter(getChildFragmentManager(),getActivity(),mTitleList,mViewList);
        foodMaterialViewpager.setAdapter(adapter);//给ViewPager设置适配器
        foodMaterialTablayout.setupWithViewPager(foodMaterialViewpager);//将TabLayout和ViewPager关联起来。
//        foodMaterialTablayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
        //设置可以滑动
        foodMaterialTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*foodMaterialTablayout.setOnTabSelectedListener(this);
        foodMaterialViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(foodMaterialTablayout));*/
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        adapter.notifyDataSetChanged();
        unbinder.unbind();
    }

    public class SecondFoodMaterialAdapter extends FragmentPagerAdapter {

        private List<String> mTitles;
        private List<Fragment> mViewList;
        private Context context;

        public SecondFoodMaterialAdapter(FragmentManager fm,Context context, List<String> mTitles,List<Fragment> mViewList) {
            super(fm);
            this.context = context;
            this.mTitles = mTitles;
            this.mViewList = mViewList;
        }

        @Override
        public Fragment getItem(int position) {
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
