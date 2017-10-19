package com.longcai.medical.ui;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.longcai.medical.R;
import com.longcai.medical.utils.AppManager;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.AutoLayoutActivity;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Created by Administrator yst on 2017/6/30.
 */

public abstract class BaseActivity extends AutoLayoutActivity {

//    private Unbinder bind;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setOverflowShowingAlways();
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * 设置总是显示溢出菜单
     *
     * @return void
     * @date 2015-7-25 12:01:31
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 请求权限
     *
     * 如果权限被拒绝过，则提示用户需要权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.label_ok), null, getString(R.string.label_cancel));
        } else {
            requestPermissions(new String[]{permission}, requestCode);
        }
    }

    /**
     * 显示指定标题和信息的对话框
     *
     * @param title                         - 标题
     * @param message                       - 信息
     * @param onPositiveButtonClickListener - 肯定按钮监听
     * @param positiveText                  - 肯定按钮信息
     * @param onNegativeButtonClickListener - 否定按钮监听
     * @param negativeText                  - 否定按钮信息
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        builder.show();
    }

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void finish() {
        super.finish();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
