package com.longcai.medical.ui.activity.login;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetUserInfoResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectFragment;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.utils.GlideCircleTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/15.
 */

public class PersonInfo1Fragment extends PictureSelectFragment {

    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;

    Unbinder unbinder;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.cet_nickname)
    ClearEditText cetNickname;
    @BindView(R.id.cet_code)
    ClearEditText cetCode;
    @BindView(R.id.linear_code)
    RelativeLayout linearCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String imagePath;
    private String mobile;
    private String token;
    private boolean open_login;
    private boolean weixin_login;
    private String inviteCode = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_info1, null);
        unbinder = ButterKnife.bind(this, view);
        inviteCode = MyApplication.myPreferences.readInviteCode();
        if (!TextUtils.isEmpty(inviteCode)) {
            linearCode.setVisibility(View.GONE);
        }
        Bundle data = getArguments();
        if (null != data) {
            mobile = data.getString("mobile", "");
            token = data.getString("token", "");
            open_login = data.getBoolean("open_login", false);
            weixin_login = data.getBoolean("weixin_login", false);
        }
        cetNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                int length = name.length();
                if (TextUtils.isEmpty(name.trim())) {
                    btnNext.setEnabled(false);
                } else if (length < 2 || length > 16) {
                    btnNext.setEnabled(false);
                } else {
                    btnNext.setEnabled(true);
                }
            }
        });
        initEvents();
//        if (open_login) {
            getEditInfo();
//        }
        return view;
    }

    private void getEditInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.USER_GET_INFO, map, GetUserInfoResult.class, new HttpUtils.OnxHttpCallBack<GetUserInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetUserInfoResult getUserInfoResult) {
                Glide.with(getActivity()).load(getUserInfoResult.getMember_avatar()).placeholder(R.mipmap.head)
                        .transform(new GlideCircleTransform(getActivity(), 2, getActivity().getResources().getColor(R.color.head_border))).into(imgAvatar);
                String memberName = getUserInfoResult.getMember_name();
                if (!TextUtils.isEmpty(memberName)) {
                    cetNickname.setText(memberName);
                }
                if (TextUtils.isEmpty(mobile)) {
                    try {
                        mobile = getUserInfoResult.getMember_mobile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.e("获取会员资料" + code + msg);
            }
        });
    }

    @OnClick({R.id.btn_next, R.id.title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                if (checkNull()) {
                    return;
                }
                Bundle b = new Bundle();
                b.putString("token", token);
                b.putString("mobile", mobile);
                b.putString("member_name", cetNickname.getText().toString());
                b.putBoolean("weixin_login", weixin_login);
                if (!TextUtils.isEmpty(imagePath)) {
                    b.putString("avatar", imagePath);
                }
                if (!TextUtils.isEmpty(inviteCode)) {
                    b.putString("invite_code", inviteCode);
                } else {
                    String invite_code = cetCode.getText().toString().trim();
                    if (!TextUtils.isEmpty(invite_code)) {
                        b.putString("invite_code", invite_code);
                    }
                }
                ((PersonInfoActivity) getActivity()).toInfo2(b);
                break;
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
    }

    private boolean checkNull() {
        String name = cetNickname.getText().toString();
        int length = name.length();
        if (TextUtils.isEmpty(name.trim())) {
            ToastUtils.showToast(getActivity(), "请填写昵称");
            return true;
        } else if (length < 2 || length > 16) {
            ToastUtils.showToast(getActivity(), "昵称只能输入2～16个字符");
            return true;
        }
        return false;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }

    public void initEvents() {
        // 设置图片点击监听
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(getActivity()).load(stream.toByteArray()).placeholder(R.mipmap.head).transform(new GlideCircleTransform(getActivity()))
                        .into(imgAvatar);
                String filePath = fileUri.getEncodedPath();
                imagePath = Uri.decode(filePath);
                LogUtils.d("图片已经保存到:" + imagePath);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                selectPicture();
            } else {
                ToastUtils.showToast(getActivity(), "没有拍照权限");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            unbinder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
