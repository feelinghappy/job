package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.GloabeActivityThired;
import com.longcai.medical.ui.activity.family.CreateFamilyActivity;


/**
 * 机器人绑定界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class BindingFg extends BaseFragment implements OnClickListener {

    TextView titleTv, title_ok;
    ImageView returIv;
    private boolean fromDevices = false;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.family_binding, null);
        Button btn_select = (Button) view.findViewById(R.id.robot_binding_select);
        Button btn_establish = (Button) view
                .findViewById(R.id.robot_binding_establish);
        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);
        btn_select.setOnClickListener(this);
        btn_establish.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        titleTv.setText("绑定");
        title_ok.setVisibility(View.GONE);
        Bundle bundle = getArguments();
        if (null != bundle) {
            fromDevices = bundle.getBoolean("from_devices");
        }
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.robot_binding_select:
                intent = new Intent(mActivity, GloabeActivityThired.class);
                intent.putExtra(Constant.FRAGMENT_MARK, Constant.BINDING_HAVE_FAM);
                if (fromDevices) {
                    intent.putExtra("from_devices", true);
                }
                startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                mActivity.finish();
                break;
            case R.id.robot_binding_establish:
                Constant.CreateFamilyHaveScaner = true;
                intent = new Intent(mActivity, CreateFamilyActivity.class);
                if (fromDevices) {
                    intent.putExtra("from_devices", true);
                }
                startActivity(intent.putExtra("haveScanner", true));
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                mActivity.finish();
                break;
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
    }

}
