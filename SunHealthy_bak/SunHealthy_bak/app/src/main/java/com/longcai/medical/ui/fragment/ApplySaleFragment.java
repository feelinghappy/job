package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.ApplySuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/3.
 */

public class ApplySaleFragment extends BaseFragment {

    private static final String TAG = "LoginFragment";
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.ed_sale_name)
    EditText edSaleName;
    @BindView(R.id.ed_sale_cardId)
    EditText edSaleCardId;
    @BindView(R.id.apply_sale_card1)
    ImageView applySaleCard1;
    @BindView(R.id.apply_sale_cardZheng)
    ImageView applySaleCardZheng;
    @BindView(R.id.apply_sale_cardFan)
    ImageView applySaleCardFan;
    @BindView(R.id.ed_sale_company)
    EditText edSaleCompany;
    @BindView(R.id.jian_btn1)
    TextView jianBtn1;
    @BindView(R.id.quan_btn1)
    TextView quanBtn1;
    @BindView(R.id.zhiye1_view)
    RelativeLayout zhiye1View;
    @BindView(R.id.quan_btn2)
    TextView quanBtn2;
    @BindView(R.id.jian_btn2)
    TextView jianBtn2;
    @BindView(R.id.zhiye2_view)
    RelativeLayout zhiye2View;
    @BindView(R.id.apply_sale_line)
    View applySaleLine;
    @BindView(R.id.apply_sale_layout_company)
    LinearLayout applySaleLayoutCompany;
    public static ApplySaleFragment newInstance() {
        ApplySaleFragment fragment = new ApplySaleFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_apply_sale, container,false);
        ButterKnife.bind(this,view);
        //如果有传递的值
        if (getArguments() != null){

        }
        return view;
    }    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        titleTv.setText("申请销售人员");
        titleRightTv.setText("提交");
    }

    @Override
    public void LoadData() {

    }

    @OnClick({R.id.title_back, R.id.title_right_tv, R.id.apply_sale_fuli, R.id.apply_sale_card1, R.id.apply_sale_cardZheng
            , R.id.apply_sale_cardFan, R.id.ed_sale_company, R.id.jian_btn1, R.id.quan_btn1, R.id.quan_btn2, R.id.jian_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                getActivity().finish();
                break;
            case R.id.title_right_tv://提交
                Intent intent = new Intent(getActivity(), ApplySuccessActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.apply_sale_fuli:
                break;
            case R.id.jian_btn1:
                zhiye1View.setVisibility(View.GONE);
                zhiye2View.setVisibility(View.VISIBLE);
                applySaleLine.setVisibility(View.GONE);
                applySaleLayoutCompany.setVisibility(View.GONE);
                break;
            case R.id.quan_btn1:
                zhiye1View.setVisibility(View.VISIBLE);
                zhiye2View.setVisibility(View.GONE);
                applySaleLine.setVisibility(View.VISIBLE);
                applySaleLayoutCompany.setVisibility(View.VISIBLE);
                break;
            case R.id.quan_btn2:
                zhiye1View.setVisibility(View.VISIBLE);
                zhiye2View.setVisibility(View.GONE);
                applySaleLine.setVisibility(View.VISIBLE);
                applySaleLayoutCompany.setVisibility(View.VISIBLE);
                break;
            case R.id.jian_btn2:
                zhiye1View.setVisibility(View.GONE);
                zhiye2View.setVisibility(View.VISIBLE);
                applySaleLine.setVisibility(View.GONE);
                applySaleLayoutCompany.setVisibility(View.GONE);
                break;
            case R.id.ed_sale_company:
                break;
            case R.id.apply_sale_card1:
                break;
            case R.id.apply_sale_cardZheng:
                break;
            case R.id.apply_sale_cardFan:
                break;
        }
    }
}
