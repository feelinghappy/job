package com.longcai.medical.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.utils.HttpUtils;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/10.
 * 吃药提醒
 */

public class RemindPillsFragment extends BaseFragment {
    Unbinder unbinder;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_pill, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.remind_pill_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.remind_pill_btn:
                addPillRemind();//
                break;
        }
    }

    //添加吃药提醒
    private void addPillRemind() {
        String taking_time = "08:00,12:00,16:00";
        String repeat_time = "1,2,3,4";
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("warns_type", Integer.toString(1));//1-自己 2-机器人
        map.put("pill_name", "阿莫西林");
        map.put("taking_time", taking_time);
        map.put("repeat_time", repeat_time);
        map.put("pill_dose", "100,9,mg");
        map.put("warner", Integer.toString(1));
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.ADD_PILL_WARN, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(String o) {

            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
