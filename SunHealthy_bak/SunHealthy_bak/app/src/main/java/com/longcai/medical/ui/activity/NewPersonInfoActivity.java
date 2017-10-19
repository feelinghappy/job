package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.NewPersonInfoFragment2;

import butterknife.ButterKnife;

/**
 * 设置中 的个人资料
 */
public class NewPersonInfoActivity extends BaseActivity {

    private NewPersonInfoFragment2 newPersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        Bundle data = getIntent().getBundleExtra("mobile_bundle");
        newPersonFragment = new NewPersonInfoFragment2();
        if (null != data) {
            Constant.isHaveInfo = false;
            newPersonFragment.setArguments(data);
        } else {
            Constant.isHaveInfo = true;
        }
        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragment_container, newPersonFragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //如果 提交的数据没有上传服务器
//            if (!Constant.isCommit) {
//                newPersonFragment.back();
//            }else {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
