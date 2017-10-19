package com.longcai.medical.ui.activity.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.photo.PictureSelectActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.ui.activity.index.ZiXunInfoActivity;
import com.longcai.medical.ui.view.ClearEditText;
import com.longcai.medical.utils.AppManager;
import com.longcai.medical.utils.GlideRoundTransform;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.RegexUtils;
import com.longcai.medical.utils.ToastUtils;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/14.
 * 注册-申请个人消费商
 */

public class PersonApplyActivity extends PictureSelectActivity {
    @BindView(R.id.apply_ed_name)
    ClearEditText applyEdName;
    @BindView(R.id.apply_ed_card)
    ClearEditText applyEdCard;
    @BindView(R.id.apply_ed_invite)
    ClearEditText applyEdInvite;
    @BindView(R.id.apply_iv_card1)
    ImageView applyIvCard1;
    @BindView(R.id.apply_iv_card2)
    ImageView applyIvCard2;
    /*@BindView(R.id.apply_iv_card3)
    ImageView applyIvCard3;
    @BindView(R.id.apply_card1_layout)
    RelativeLayout applyCard1Layout;
    @BindView(R.id.apply_card2_layout)
    RelativeLayout applyCard2Layout;
    @BindView(R.id.apply_card3_layout)
    RelativeLayout applyCard3Layout;*/
    @BindView(R.id.apply_agree_cb)
    CheckBox applyAgreeCb;
    @BindView(R.id.apply_tv_agree)
    TextView applyTvAgree;
    @BindView(R.id.apply_iv_commit)
    Button applyIvCommit;
//    private int isAgree = -1;
    private String name;
    private String card;
    private String invite;
    private String imagePath1;
    private String imagePath2;
    private String imagePath3;
    private int pathType = -1;
    private int source;
    private boolean loginSuccess;
    private boolean weixinLogin;
    private int lastContentLength = 0;
    private boolean isDelete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_apply);
        ButterKnife.bind(this);
        initView();
        initListener();
        source = getIntent().getIntExtra("source", -1);
        loginSuccess = getIntent().getBooleanExtra("login_success", false);
        weixinLogin = getIntent().getBooleanExtra("weixin_login", false);
    }

    private void initListener() {
        applyEdName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                String idCard = applyEdCard.getText().toString().replaceAll("-", "");
                if (!TextUtils.isEmpty(name) && RegexUtils.isIdCard(idCard) && !TextUtils.isEmpty(imagePath1) &&
                        !TextUtils.isEmpty(imagePath2) && applyAgreeCb.isChecked()) {
                    applyIvCommit.setEnabled(true);
                }
            }
        });
        applyEdCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                StringBuffer sb = new StringBuffer(s);
                //是否为输入状态
                isDelete = s.length() > lastContentLength ? false : true;
                int length = s.length();
                //输入是第4，第9位，这时需要插入空格
                if(!isDelete && (length == 4|| length == 8 || length == 17)){
                    if(length == 4) {
                        sb.insert(3, "-");
                    } else if (length == 8){
                        sb.insert(7, "-");
                    } else if (length == 17) {
                        sb.insert(16, "-");
                    }
                    applyEdCard.setText(sb.toString());
                    applyEdCard.setSelection(sb.length());
                }

                if (isDelete && (s.length() == 4 || s.length() == 9)) {
                    sb.deleteCharAt(sb.length() - 1);
                    applyEdCard.setText(sb.toString());
                    applyEdCard.setSelection(sb.length());
                }

                lastContentLength = sb.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String idCard = editable.toString().replaceAll("-", "");
                String name = applyEdName.getText().toString();
                if (!TextUtils.isEmpty(name) && RegexUtils.isIdCard(idCard) && !TextUtils.isEmpty(imagePath1) &&
                        !TextUtils.isEmpty(imagePath2) && applyAgreeCb.isChecked()) {
                    applyIvCommit.setEnabled(true);
                }
            }
        });
        applyAgreeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (applyAgreeCb.isChecked()) {
                    checkEnabled();
                } else {
                    applyIvCommit.setEnabled(false);
                }
            }
        });
    }

    private void initView() {
        SpannableString sp = new SpannableString(getString(R.string.apply_agree));
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.theme_blue)), 12, 21, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        applyTvAgree.setText(sp);
    }

    @OnClick({R.id.title_back, R.id.apply_iv_card1, R.id.apply_iv_card2,
            R.id.apply_tv_agree, R.id.apply_iv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
            case R.id.apply_iv_card1:
                // 设置图片点击监听
                pathType = 1;
                selectPicture();
                initEvents(applyIvCard1);
//                imagePath1 = imagePath;
                break;
            case R.id.apply_iv_card2:
                pathType = 2;
                selectPicture();
                initEvents(applyIvCard2);
                break;
           /* case R.id.apply_card3_layout:
                pathType = 3;
                selectPicture();
                initEvents(applyIvCard3);
                break;*/
            case R.id.apply_tv_agree:
                startActivity(new Intent(this, ZiXunInfoActivity.class)
                        .putExtra("url", MyUrl.AGREEMENT_PERSONAL).putExtra("agreement", 2));
                break;
            case R.id.apply_iv_commit://提交
                name = applyEdName.getText().toString().trim();
                card = applyEdCard.getText().toString().trim().replaceAll("-", "");
                invite = applyEdInvite.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showToast(this,"请输入真实姓名");
                    return;
                }else if (name.length() < 2|| name.length() > 8){
                    ToastUtils.showToast(this,"请输入2-8位姓名");
                    return;
                }else if (TextUtils.isEmpty(card)){
                    ToastUtils.showToast(this,"请输入身份证号");
                    return;
                }else if (!RegexUtils.isIdCard(card)){
                    ToastUtils.showToast(this, "请输入正确的身份证号");
                    return;
                }else if (TextUtils.isEmpty(imagePath1)){
                    ToastUtils.showToast(this, "请上传身份证正面照");
                    return;
                }else if (TextUtils.isEmpty(imagePath2)){
                    ToastUtils.showToast(this, "请上传身份证背面照");
                    return;
                }else if (!applyAgreeCb.isChecked()){
                    ToastUtils.showToast(this, "请阅读并签署个人消费商协议");
                    return;
                }
                /*else if (TextUtils.isEmpty(imagePath3)){
                    ToastUtils.showToast(this, "请上传手持身份证照");
                    return;
                }*/
                applySeller();
                break;
        }
    }

    private void back() {
        if (loginSuccess) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("source", source).putExtra("login_success", loginSuccess);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            if (weixinLogin) {
                AppManager.getAppManager().leaveTopActivity();
            }
        } else {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //提交申请
    private void applySeller() {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> filemap = new HashMap<>();
        LogUtils.d("myPreferences == "+MyApplication.myPreferences);
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("true_name", name);
        map.put("id_card", card);
        map.put("invite_code", invite);
        if (!TextUtils.isEmpty(imagePath1)){
            filemap.put("card_face", imagePath1);
        }
        if (!TextUtils.isEmpty(imagePath2)){
            filemap.put("card_back", imagePath2);
        }
        /*if (!TextUtils.isEmpty(imagePath3)){
            filemap.put("card_hand", imagePath3);
        }*/
        HttpUtils.xOverallHttpPost(PersonApplyActivity.this, MyUrl.APPLY_SELLER, filemap, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
                MyApplication.myPreferences.saveApplySaler("1");
                back();
            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10511) {
                    ToastUtils.showToast(PersonApplyActivity.this, "您的申请已提交");
                    MyApplication.myPreferences.saveApplySaler("1");
                }
                if (code == 10512){
                    ToastUtils.showToast(PersonApplyActivity.this, "该身份证号输入有误，请重新输入");
                }
                if (code == 10517) {
                    ToastUtils.showToast(PersonApplyActivity.this, "该身份证号已经存在");
                }
                if (code == 10513){
                    ToastUtils.showToast(PersonApplyActivity.this, "该邀请码不存在");
                }
            }
        });
    }

    public void initEvents(final ImageView iv) {
        // 设置裁剪图片结果监听
        setOnPictureSelectedListener(new OnPictureSelectedListener() {
            @Override
            public void onPictureSelected(Uri fileUri, Bitmap bitmap) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(PersonApplyActivity.this).load(stream.toByteArray())
                        .transform(new GlideRoundTransform(PersonApplyActivity.this, 5))
                        .into(iv);
                String filePath = fileUri.getEncodedPath();
                if (pathType == 1) {
                    imagePath1 = Uri.decode(filePath);
                    LogUtils.d("路径 imagePath1====" + imagePath1);
                    checkEnabled();
                } else if (pathType == 2) {
                    imagePath2 = Uri.decode(filePath);
                    LogUtils.d("路径 imagePath2====" + imagePath2);
                    checkEnabled();
                } else if (pathType == 3) {
                    imagePath3 = Uri.decode(filePath);
                    LogUtils.d("路径 imagePath3====" + imagePath3);
                }
            }
        });
    }

    private void checkEnabled() {
        String idCard = applyEdCard.getText().toString().replaceAll("-", "");
        String name = applyEdName.getText().toString();
        if (!TextUtils.isEmpty(name) && RegexUtils.isIdCard(idCard) && !TextUtils.isEmpty(imagePath1) &&
                !TextUtils.isEmpty(imagePath2) && applyAgreeCb.isChecked()) {
            applyIvCommit.setEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
