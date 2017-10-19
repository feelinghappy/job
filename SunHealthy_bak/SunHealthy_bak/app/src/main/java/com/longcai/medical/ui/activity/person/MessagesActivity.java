package com.longcai.medical.ui.activity.person;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrator on 2017/7/31.
 */

public class MessagesActivity extends BaseActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        initData();
    }

    private void initData() {
        String messageId = getIntent().getStringExtra("message_id");
        String messageType = getIntent().getStringExtra("message_type");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = MessageInfoFragment.newInstance(messageId, messageType);//系统消息fragment
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragment instanceof MessageInfoFragment) {
            ((MessageInfoFragment) fragment).onKeyDown(keyCode, event);
        }
        return false;
    }
}
