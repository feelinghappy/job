package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.adapter.FoodListAdapter;
import com.longcai.medical.bean.FoodList;
import com.longcai.medical.bean.SelectFoodBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.activity.discover.FoodDetailAcivity;
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
 * Created by LC-XC on 2017/3/29.
 */

public class FoodListFragment extends Fragment {
    /**
     * 用来与外部activity交互的
     */
    private FragmentInteraction listterner;
    /**
     * 定义了所有activity必须实现的接口
     */
    public interface FragmentInteraction {
        /**
         * Fragment 向Activity传递指令，这个方法可以根据需求来定义
         *
         * @param bean
         */
        void process(SelectFoodBean bean);
    }
    private Unbinder unbinder;
    private HashMap<String, String> map;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.food_list_recycler)
    RecyclerView foodListRecycler;
    @BindView(R.id.recommend_list)
    GridView recommendList;
    @BindView(R.id.more_btn)
    RelativeLayout moreBtn;
    @BindView(R.id.food_view01)
    LinearLayout foodView01;
    @BindView(R.id.foodMoreTx2)
    TextView foodMoreTx2;
    private FoodListAdapter adapter;
    private FoodListAdapter adapter2;
    private FoodListAdapter adapter3;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private View rootView;
    private RelativeLayout moreBtn2;
    private View headView;
    private TextView foodMoreTx;
    private RecyclerView recyclerView;

    // TODO: Rename and change types and number of parameters
    public static FoodListFragment newInstance(int param1, int param2) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            LogUtils.d("传递的category_id是 " + mParam2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.second_list_info_view, null);
        unbinder = ButterKnife.bind(this, rootView);
        moreBtn.setVisibility(View.VISIBLE);
        map = new HashMap<>();
        initData();
        initHead();
        initFood();

        setClick();
        return rootView;
    }

    //获取食材二级分类
    private void initFood() {
        map.put("category_id", String.valueOf(mParam2));
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.GET_FOOD_LIST, map, FoodList.class, new HttpUtils.OnxHttpWithListResultCallBack<FoodList>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(final List<FoodList> foodCateList) {
                final FoodListAdapter adapter = new FoodListAdapter(getActivity(), foodCateList);
                recommendList.setAdapter(adapter);
                adapter.setOnRecyclerViewItemListener(new FoodListAdapter.OnGridViewItemListener() {
                    @Override
                    public void onItemClickListener(View view, int position, String url) {
                        if (url != null) {
                            startActivity(new Intent(getActivity(), FoodDetailAcivity.class)
                                    .putExtra("url", url));
                        }
                    }
                });
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    private ArrayList<SelectFoodBean> data = new ArrayList<>();
    private SelectFoodBean bean;

    private void setClick() {
    }

    private void initHead() {

    }

    private void initData() {

    }

    @OnClick(R.id.more_btn)
    public void onClick() {
        /*if (foodListRecycler.isShown()) {
            foodView01.setVisibility(View.VISIBLE);
            foodListRecycler.setVisibility(View.GONE);
        } else {
            foodView01.setVisibility(View.GONE);
            foodListRecycler.setVisibility(View.VISIBLE);
        }*/
    }


}
