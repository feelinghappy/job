package com.longcai.medical.ui.activity.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

/**
 * Created by Administrator on 2017/9/15.
 */

public class PersonInfoActivity extends BaseActivity {

    private PersonInfo1Fragment personInfo1Fragment;
    private PersonInfo2Fragment personInfo2Fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        if (null == personInfo1Fragment) {
            personInfo1Fragment = new PersonInfo1Fragment();
        }
        Bundle b = getIntent().getBundleExtra("mobile_bundle");
        personInfo1Fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, personInfo1Fragment, "Info1")
                .commit();
    }

    public void toInfo2(Bundle b) {
        if (null == personInfo2Fragment) {
            personInfo2Fragment = new PersonInfo2Fragment();
        }
        personInfo2Fragment.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, personInfo2Fragment, "Info2")
                .commit();
    }

}
