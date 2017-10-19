package com.longcai.medical.ui.activity;

import android.os.Bundle;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.NewPersonInfoFragment2;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrator on 2017/6/30.
 * 注册
 */

public class RegisterActivity extends BaseActivity {

    private NewPersonInfoFragment2 newPersonFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
//        ButterKnife.bind(this);

        initView();
    }

    void initView() {
        if (newPersonFragment == null) {
            newPersonFragment = new NewPersonInfoFragment2();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newPersonFragment).commit();
        Constant.isHaveInfo = false;
    }
}
