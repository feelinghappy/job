package com.longcai.medical.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcai.medical.R;
import com.longcai.medical.bean.FoodMaterial;
import com.longcai.medical.ui.activity.SecondFoodActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LC-XC on 2017/4/12.
 * 食谱
 */

public class SecondFoodMenuFragment extends Fragment {

    @BindView(R.id.food_menu_tabLayout)
    TabLayout foodMenuTabLayout;
    @BindView(R.id.food_menu_viewpager)
    ViewPager foodMenuViewpager;
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
    private SecondFoodMenuAdapter adapter;
    private int selectPost = 0;

    public SecondFoodMenuFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static SecondFoodMenuFragment newInstance(String param1, String param2) {
        SecondFoodMenuFragment fragment = new SecondFoodMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private List<FoodMaterial.Recipe> data;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        data = ((SecondFoodActivity) activity).getData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mTitleList.clear();
        mTitleList.add("早餐");
        mTitleList.add("主食");
        mTitleList.add("素食");
        mTitleList.add("煲汤");
        mTitleList.add("粥品");
        size = mTitleList.size();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        foodView = inflater.inflate(R.layout.second_foodmenu_fragment, null);
        ButterKnife.bind(this, foodView);
        init();
        foodMenuTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                foodMenuViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        foodMenuViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                foodMenuTabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return foodView;
    }


    private void init() {
        mViewList.clear();
        mViewList.add(FoodListMenuFragment.newInstance(String.valueOf(0), null));
        mViewList.add(FoodListMenuFragment.newInstance(String.valueOf(1), null));
        mViewList.add(FoodListMenuFragment.newInstance(String.valueOf(2), null));
        mViewList.add(FoodListMenuFragment.newInstance(String.valueOf(3), null));
        mViewList.add(FoodListMenuFragment.newInstance(String.valueOf(4), null));
        foodMenuTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//tab可以滚动，tab宽度自动缩放
        for (int i = 0; i < mTitleList.size(); i++) {
            foodMenuTabLayout.addTab(foodMenuTabLayout.newTab().setText(mTitleList.get(i)));//添加tab选项卡
        }
        adapter = new SecondFoodMenuAdapter(getChildFragmentManager(), mViewList, mTitleList);
        foodMenuViewpager.setAdapter(adapter);
        foodMenuTabLayout.setupWithViewPager(foodMenuViewpager);
        foodMenuTabLayout.setTabsFromPagerAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public class SecondFoodMenuAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mViewList;
        private List<String> mTitleList;

        public SecondFoodMenuAdapter(FragmentManager fm, List<Fragment> mViewList, List<String> mTitleList) {
            super(fm);
            this.mViewList = mViewList;
            this.mTitleList = mTitleList;
        }

        @Override
        public Fragment getItem(int i) {
            return mViewList.get(i);
        }

        @Override
        public int getCount() {
            return mTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
