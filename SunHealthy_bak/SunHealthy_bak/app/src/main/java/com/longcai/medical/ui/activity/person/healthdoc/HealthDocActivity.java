package com.longcai.medical.ui.activity.person.healthdoc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.BasicInfoResult;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.GloabeActivitySecond;
import com.longcai.medical.utils.GlideCircleTransform;
import com.longcai.medical.utils.HttpUtils;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HealthDocActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.iv_doc_avatar)
    ImageView ivDocAvatar;
    @BindView(R.id.tv_doc_name)
    TextView tvDocName;
    @BindView(R.id.tv_doc_sex)
    TextView tvDocSex;
    @BindView(R.id.tv_doc_age)
    TextView tvDocAge;
    @BindView(R.id.pb_info_degree)
    ProgressBar pbInfoDegree;
    @BindView(R.id.tv_info_degree)
    TextView tvInfoDegree;
    @BindView(R.id.tv_doc_basicinfo_complete)
    TextView tvDocBasicinfoComplete;
    @BindView(R.id.rl_doc_basicinfo)
    RelativeLayout rlDocBasicinfo;
    @BindView(R.id.tv_doc_data_complete)
    TextView tvDocDataComplete;
    @BindView(R.id.rl_doc_data)
    RelativeLayout rlDocData;

    private Unbinder unbinder;
    private BasicInfoResult mBasicInfoResult;
    private final int REQUEST_CODE_BASICINFO = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_doc);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleTv.setText("健康档案");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.myPreferences.readToken().equals("-1")) {
            Glide.with(this).load(R.mipmap.head).transform(new GlideCircleTransform(this)).into(ivDocAvatar);
        } else {
            if (!TextUtils.isEmpty(MyApplication.myPreferences.readavatar())) {
                Glide.with(this).load(MyApplication.myPreferences.readavatar()).placeholder(R.mipmap.head)
                        .transform(new GlideCircleTransform(this)).into(ivDocAvatar);
            }else {
                Glide.with(this).load(R.mipmap.head).transform(new GlideCircleTransform(this)).into(ivDocAvatar);
            }
        }
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(this, MyUrl.HEALTH_GET_BASIC_INFO, map, BasicInfoResult.class, new HttpUtils.OnxHttpCallBack<BasicInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(BasicInfoResult result) {
                HealthDocActivity.this.mBasicInfoResult = result;
                int sum = 0;
                String true_name = result.getTrue_name();
                if (!TextUtils.isEmpty(true_name)) {
                    sum++;
                    tvDocName.setText(true_name);
                }
                String member_sex = result.getMember_sex();
                if (!TextUtils.isEmpty(member_sex)) {
                    sum++;
                    if (member_sex.equals("1")) {
                        tvDocSex.setText("男");
                    } else if (member_sex.equals("2")) {
                        tvDocSex.setText("女");
                    }
                }
                String member_age = result.getMember_age();
                if (!TextUtils.isEmpty(member_age)) {
                    sum++;
                    tvDocAge.setText(member_age + "岁");
                }
                String member_height = result.getMember_height();
                if (!TextUtils.isEmpty(member_height)) {
                    sum++;
                }
                String member_weight = result.getMember_weight();
                if (!TextUtils.isEmpty(member_weight)) {
                    sum++;
                }
                String profession = result.getProfession();
                if (!TextUtils.isEmpty(profession)) {
                    sum++;
                }
                String medical_type = result.getMedical_type();
                if (!TextUtils.isEmpty(medical_type)) {
                    sum++;
                }
                double percent = (double)sum / 7;
                BigDecimal degree = new BigDecimal(Double.toString(percent * 100));
                tvInfoDegree.setText(degree.setScale(0, BigDecimal.ROUND_HALF_UP).toPlainString() + "%");
                pbInfoDegree.setProgress(degree.setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
                if (percent < 1) {
                    tvDocBasicinfoComplete.setVisibility(View.VISIBLE);
                } else {
                    tvDocBasicinfoComplete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10140) {
                    tvInfoDegree.setText("0%");
                    pbInfoDegree.setProgress(0);
                    tvDocBasicinfoComplete.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.rl_doc_basicinfo, R.id.rl_doc_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.rl_doc_basicinfo:
                Intent intent = new Intent(this, HealthBasicInfoActivity.class);
                if (null != mBasicInfoResult) {
                    intent.putExtra("basic_info", mBasicInfoResult);
                }
                startActivityForResult(intent, REQUEST_CODE_BASICINFO);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.rl_doc_data:
                startActivity(new Intent(this, GloabeActivitySecond.class)
                        .putExtra(Constant.FRAGMENT_MARK, Constant.HISTORY_FG)
                        .putExtra(Constant.FAMILY_MEMBER_ID, "-1"));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BASICINFO && resultCode == RESULT_OK) {
            initData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
