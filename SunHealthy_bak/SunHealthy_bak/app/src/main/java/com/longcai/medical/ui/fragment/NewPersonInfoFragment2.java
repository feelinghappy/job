package com.longcai.medical.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.EditInfoBean;
import com.longcai.medical.bean.GetUserInfoResult;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectFragment;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.person.PersonEditNameActivity;
import com.longcai.medical.ui.view.popupwindow.CustomPopupWindow;
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
 * Created by LC-XC on 2017/4/28.
 * 新编辑和获取会员资料 头像，账号，昵称
 */

public class NewPersonInfoFragment2 extends PictureSelectFragment implements CustomPopupWindow.IOKBack {
    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    Unbinder unbinder;
    @BindView(R.id.rootLinear)
    LinearLayout rootLinear;
    @BindView(R.id.person_information_head)
    ImageView personInformationHead;
    @BindView(R.id.person_information_phone)
    TextView personInformationPhone;
    @BindView(R.id.person_information_name)
    TextView personInformationName;
    @BindView(R.id.person_information_layout3)
    RelativeLayout personInformationLayout3;
    @BindView(R.id.person_information_head_layout)
    RelativeLayout personInformationHeadLayout;
    private HashMap<String, String> map = new HashMap<>();
//    private PopupWindow popupWindow_head;
    private boolean isHaveImg = false;
    private String imagePath;
    private boolean isChanged = false;
    private String memberHeight;
    private String memberWeight;
    private String birthday;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_person_fragment2, null);
        unbinder = ButterKnife.bind(this, view);
        initFraView();
        return view;
    }

    private void initFraView() {
        titleTv.setText("账户信息");
//        titleRightTv.setText("保存");
        titleRightTv.setVisibility(View.GONE);
        titleRightTv.setTextColor(getResources().getColor(R.color.black));
//        Constant.isCommit = false;
        //判断是否从个人资料跳入
        if (Constant.isHaveInfo) {
            //获取个人资料
            initData();
//            Constant.isHaveInfo = false;
        }
        Bundle data = getArguments();
        if (null != data) {
            String mobile = data.getString("mobile");
            personInformationPhone.setText(mobile);
        }
        initEvents();
        getUserInfoData();
    }

    //获取个人资料
    public void initData() {
        if (!MyApplication.myPreferences.readNickName().equals("-1")) {
            personInformationName.setText(MyApplication.myPreferences.readNickName());
        } else {
            personInformationName.setText("");
        }
        if (!MyApplication.myPreferences.readPhone().equals("-1")) {
            personInformationPhone.setText(MyApplication.myPreferences.readPhone());
        }
        if (!MyApplication.myPreferences.readavatar().equals("-1")) {
            Glide.with(getActivity()).load(MyApplication.myPreferences.readavatar()).placeholder(R.mipmap.head)
                    .transform(new GlideCircleTransform(getActivity()))
                    .into(personInformationHead);
        }
    }

    @Override
    public View initView() {
        return null;
    }

    @OnClick({R.id.title_back, R.id.title_right_tv, R.id.person_information_head_layout, R.id.person_information_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.title_right_tv://点击保存
                editInfo();
                break;
            case R.id.person_information_head_layout:
//                View view_head = LayoutInflater.from(getActivity()).inflate(R.layout.person_information_pop, null);
//                popupWindow_head = new ShowPopupWindow(getActivity()).showPopup(personInformationHeadLayout);
//                //设置动画效果
//                popupWindow_head.setAnimationStyle(android.R.style.Animation_InputMethod);
//                //设置popwindow显示位置
////                popupWindow_head.showAtLocation(personInformationHead, Gravity.BOTTOM, 0, 0);
//                initPop(view_head);
                break;
            case R.id.person_information_name:
                startActivityForResult(new Intent(getActivity(), PersonEditNameActivity.class)
                        .putExtra("member_name", personInformationName.getText().toString())
                        .putExtra("mobile", personInformationPhone.getText().toString())
                        .putExtra("member_height", memberHeight)
                        .putExtra("member_weight", memberWeight)
                        .putExtra("birthday", birthday), 1);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    private void getUserInfoData() {
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPostWithoutDialog(getActivity(), MyUrl.USER_GET_INFO, map, GetUserInfoResult.class, new HttpUtils.OnxHttpCallBack<GetUserInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(GetUserInfoResult getUserInfoResult) {
                memberHeight = getUserInfoResult.getMember_height();
                memberWeight = getUserInfoResult.getMember_weight();
                birthday = getUserInfoResult.getBirthday();
            }

            @Override
            public void onFail(int code, String msg) {
                LogUtils.e("获取会员资料" + code + msg);
            }
        });
    }

    //编辑会员资料
    private void editInfo() {
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("member_name", personInformationName.getText().toString());
        map.put("mobile", personInformationPhone.getText().toString());//MyApplication.myPreferences.readPhone()
        map.put("member_height", memberHeight);
        map.put("member_weight", memberWeight);
        map.put("birthday", birthday);
        if (!isHaveImg) {//判断用户是否上传了照片
            HttpUtils.xOverallHttpPost(getActivity(), MyUrl.USER_REGISTER, map, EditInfoBean.class, new HttpUtils.OnxHttpCallBack<EditInfoBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                }

                @Override
                public void onSuccess(EditInfoBean editInfoBean) {
                    if (!Constant.isHaveInfo) {
                        startActivity(new Intent(getActivity(), MainActivity.class).putExtra("source", MyApplication.source).putExtra("login_success", true));
                    }
                    ToastUtils.showToast(getActivity(), "修改成功");
                    MyApplication.myPreferences.saveNickName(editInfoBean.getMember_name());
                    MyApplication.myPreferences.savePhone(editInfoBean.getMobile());
                    Constant.PHONE_NUMBER = editInfoBean.getMobile();
//                    Constant.isCommit = true;
//                    getActivity().finish();
//                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }

                @Override
                public void onFail(int code, String msg) {
                    ToastUtils.showToast(getActivity(), code + msg);
                }
            });
        } else {//imgList添加了一张图片
            HashMap<String, String> filemap = new HashMap<>();
            filemap.put("avatar", imagePath);
            HttpUtils.xOverallHttpPost(getActivity(), MyUrl.USER_REGISTER, filemap, map, EditInfoBean.class, new HttpUtils.OnxHttpCallBack<EditInfoBean>() {
                @Override
                public void onSuccessMsg(String successMsg) {
                }

                @Override
                public void onSuccess(EditInfoBean editInfoBean) {
//                    getActivity().finish();
                    MyApplication.myPreferences.saveNickName(editInfoBean.getMember_name());
                    MyApplication.myPreferences.savePhone(editInfoBean.getMobile());
                    MyApplication.myPreferences.saveavatar(editInfoBean.getMember_avatar());
                    Constant.PHONE_NUMBER = editInfoBean.getMobile();
                }

                @Override
                public void onFail(int code, String msg) {
//                    ToastUtils.showToast(getActivity(), msg);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Constant.PERSON_NAME:
                ToastUtils.showToast(getActivity(), "设置成功");
                isChanged = true;
                if (data.getStringExtra("nickname") != null) {
                    personInformationName.setText(data.getStringExtra("nickname"));
                }
        }
    }

    //设置图片
    public void initEvents() {
        // 设置图片点击监听
        personInformationHeadLayout.setOnClickListener(new View.OnClickListener() {
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
                        .into(personInformationHead);
                String filePath = fileUri.getEncodedPath();
                imagePath = Uri.decode(filePath);
                LogUtils.d("图片已经保存到:" + imagePath);
                isChanged = true;
                isHaveImg = true;
                editInfo();
            }
        });
    }

    public void back() {
//        String name = personInformationName.getText().toString();
//        //!TextUtils.isEmpty(name) || !TextUtils.isEmpty(imagePath) || !TextUtils.isEmpty(MyApplication.myPreferences.readavatar())
//        if (isChanged) {
//            CustomPopupWindow customPopupWindow = new CustomPopupWindow(getActivity());
//            customPopupWindow.showPopupWindow(titleRightTv);
//            customPopupWindow.setIOKBack(this);
//            customPopupWindow.setTitle("您的修改还未提交，确认现在返回吗？");
//        } else {
            getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
//        }
    }

    @Override
    public void okBack() {
        getActivity().finish();
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
/*
    @Override
    protected Crop getCrop() {
        return new Crop().setCrop(true);
    }

    @Override
    protected String getCameraAbsolutePath() {
        if (UtilSDCard.isSDCardEnable()) {
            File file = new File(Environment.getExternalStorageDirectory() + "/medical/head/");
            if (!file.exists()) {
                file.mkdir();
            }
        } else {
            File dir = new File(getActivity().getApplicationInfo().dataDir + "/medical/head/");
            if (!dir.exists())
                dir.mkdir();
            return dir.getAbsolutePath();
        }
        return Environment.getExternalStorageDirectory() + "/medical/head/";
    }

    private void deleteDir() {
        if (UtilSDCard.isSDCardEnable()) {
            File file = new File(Environment.getExternalStorageDirectory() + "/medical/");
            if (file.exists()) {
                StringUtils.recursionDeleteFile(file);
            }
        } else {
            File dir = new File(getActivity().getApplicationInfo().dataDir + "/medical/");
            if (dir.exists())
                StringUtils.recursionDeleteFile(dir);
        }
    }

    @Override
    protected void resultPhotoPath(ImageView imageView, String s) {
        LogUtils.e("resultPhotoPath: " + s);
        Glide.with(getActivity()).load(s).transform(new GlideCircleTransform(getActivity())).into(personInformationHead);
        headUrl = s;
        imgList.add(headUrl);
    }
*/

//    private TextView pop1, pop2, pop3;
//
//    private void initPop(View view) {
//        pop1 = (TextView) view.findViewById(R.id.person_information_pop_text1);//拍照
//        pop2 = (TextView) view.findViewById(R.id.person_information_pop_text2);//相册
//        pop3 = (TextView) view.findViewById(R.id.person_information_pop_text3);//取消
//        pop1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow_head.dismiss();
////                deleteDir();
//                checkPermission();
//            }
//
//
//        });
//        pop2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow_head.dismiss();
////                deleteDir();
////                startAlbum(personInformationHead);
//            }
//        });
//        pop3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow_head.dismiss();
//            }
//        });
//    }

    //拍照权限
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_TAKE_PHOTO_PERMISSION);
        } else {
            //有权限，直接拍照
            selectPicture();
//            startCamera(personInformationHead);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功，可以拍照
                selectPicture();
//                startCamera(personInformationHead);
            } else {
                ToastUtils.showToast(getActivity(), "CAMERA PERMISSION DENIED");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
