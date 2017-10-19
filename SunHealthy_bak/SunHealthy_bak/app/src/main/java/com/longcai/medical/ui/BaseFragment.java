package com.longcai.medical.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcai.medical.R;

public abstract class BaseFragment extends Fragment {

    public Activity mActivity;//这个activity就是MainActivity
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    private AlertDialog mAlertDialog;

    // Fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();// 获取当前fragment所依赖的activity
    }

    // 初始化fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    // fragment所依赖的activity的onCreate方法执行结束
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 初始化数据
        initData();
    }

    // 初始化布局, 必须由子类实现
    public abstract View initView();

    // 初始化数据, 必须由子类实现
    public abstract void initData();

    // 加载数据, 必须由子类实现
    public void LoadData(){

    }
    /**
     * 请求权限
     *
     * 如果权限被拒绝过，则提示用户需要权限
     */
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }
}
