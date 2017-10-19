package com.longcai.medical.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcai.medical.R;
import com.longcai.medical.adapter.FoodListMenuAdapter;
import com.longcai.medical.bean.FoodMaterial;
import com.longcai.medical.ui.activity.SecondFoodActivity;
import com.longcai.medical.ui.activity.WebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LC-XC on 2017/4/12.
 */

public class FoodListMenuFragment extends Fragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private View rootView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //private SecondFoodMenuAdapter adapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Collect.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodListMenuFragment newInstance(String param1, String param2) {
        FoodListMenuFragment fragment = new FoodListMenuFragment();
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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.food_list_menu_fragment, null);
        ButterKnife.bind(this, rootView);

        return rootView;
    }
    private FoodListMenuAdapter adapter;
    @Override
    public void onResume() {
        super.onResume();
        adapter = new FoodListMenuAdapter(getActivity());
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setData2(data);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLitener(new FoodListMenuAdapter.ItemClickListener() {
            @Override
            public void OnClick(String id, String url) {
             //   ToastUtils.show(getContext(), "详情web:" + id);
                startActivity(new Intent(getActivity(), WebActivity.class).
                        putExtra("title", "食谱详情").putExtra("url", url));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
