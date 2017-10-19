package com.longcai.medical.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.BindingFg;
import com.longcai.medical.ui.fragment.BindingHaveFamFg;
import com.longcai.medical.ui.fragment.FamilySearchFg;
import com.longcai.medical.ui.fragment.HistoryRobotFg;
import com.longcai.medical.ui.fragment.MessageFg;


public class GloabeActivitySecond extends BaseActivity {
    private String fgMark;
    private android.support.v4.app.Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题

        initView();
        initData();
    }

    public void initView() {
        setContentView(R.layout.activity_gloabe);
    }

    public void initData() {
        fgMark = getIntent().getStringExtra(Constant.FRAGMENT_MARK);
        boolean fromDevices = getIntent().getBooleanExtra("from_devices", false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (fgMark) {
            case Constant.ROBOTBINDINGFG:
                fragment = new BindingFg();
                if (fromDevices) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("from_devices", true);
                    fragment.setArguments(bundle);
                }
                break;
            case Constant.MESSAGE_LIST:
                fragment = new MessageFg();
                Constant.fragmentTag = Constant.MessageFgTag;
                break;
            case Constant.FAM_SEARCH_LIST:
                fragment = new FamilySearchFg();
                break;
            case Constant.BINDING_HAVE_FAM:
                fragment = new BindingHaveFamFg();
                break;
            case Constant.HISTORY_FG:
//                String family_member_id = getIntent().getStringExtra(Constant.FAMILY_MEMBER_ID);
                fragment = new HistoryRobotFg();
                /*if (family_member_id.equals("-1")){
                }else {
                    fragment = HistoryRobotFg.newInstance(family_member_id);
                }*/
                break;
        }
        ft.replace(R.id.gloabe_fm, fragment);
        ft.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(resultCode, intent);
//        finish();
    }
}
