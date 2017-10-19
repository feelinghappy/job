package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.BindingHaveFamFg;

import org.jetbrains.annotations.Nullable;


public class GloabeActivityThired extends BaseActivity {
    private String fgMark;
    private android.support.v4.app.Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gloabe);
        initData();
    }

    public void initData() {
        fgMark = getIntent().getStringExtra(Constant.FRAGMENT_MARK);
        boolean fromDevices = getIntent().getBooleanExtra("from_devices", false);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (fgMark) {

            case Constant.BINDING_HAVE_FAM:
                fragment = new BindingHaveFamFg();
                if (fromDevices) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("from_devices", true);
                    fragment.setArguments(bundle);
                }
                break;

        }
        ft.replace(R.id.gloabe_fm, fragment);
        ft.commit();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(resultCode, intent);
        finish();
    }

    /* 点击非edittext处隐藏键盘 */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
