package com.longcai.medical.ui.activity.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.EmptyActivity;
import com.longcai.medical.ui.activity.LoginActivity;
import com.longcai.medical.ui.activity.SportActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/27.
 * 发现
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.discover_diet)
    AutoRelativeLayout discoverDiet;
    @BindView(R.id.discover_motion)
    AutoRelativeLayout discoverMotion;
    @BindView(R.id.discover_lecture)
    AutoRelativeLayout discoverLecture;
    Unbinder unbinder;
    @BindView(R.id.title_back)
    ImageView titleBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        titleTv.setText("发现");
        titleBack.setVisibility(View.GONE);
        titleRightTv.setVisibility(View.GONE);
    }

    @OnClick({R.id.discover_diet, R.id.discover_motion, R.id.discover_lecture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.discover_diet://饮食
//                startActivity(new Intent(getActivity(), SecondFoodActivity.class));
//                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), EmptyActivity.class).putExtra("title", "饮食"));
                    getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
                break;
            case R.id.discover_motion://运动
                //二期修改   原来一期是需要vip才可以看见时间条的
                MyApplication.myPreferences.saveGrade("2");
                startActivity(new Intent(getActivity(), SportActivity.class).putExtra("is", "2").putExtra("familyId", "0"));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.discover_lecture://讲座
//                startActivity(new Intent(getActivity(), CenterActivity.class));
                if (MyApplication.myPreferences.readToken().equals("-1")) {
                    login();
                } else {
                    startActivity(new Intent(getActivity(), EmptyActivity.class).putExtra("title", "讲座"));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
