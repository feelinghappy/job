package com.longcai.medical.rob.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.rob.bean.Robot;
import com.longcai.medical.rob.logic.VideoCallUA;
import com.longcai.medical.rob.ui.CallingActivity;
import com.longcai.medical.rob.ui.RobotHomeActivity;
import com.longcai.medical.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author : zbj on 2017/9/16 14:11.
 */

public class RobotFragment extends Fragment implements RobotHomeActivity.DataChangeListener{

    private Robot robot;
    @BindView(R.id.robot_name)
    TextView     mRobotName;
    @BindView(R.id.robot_status)
    TextView     mRobotStatus;
    @BindView(R.id.robot_video)
    LinearLayout mRobotVideo;
    @BindView(R.id.robot_monitor)
    LinearLayout mRobotMonitor;
    @BindView(R.id.robot_air_box)
    LinearLayout mRobotAirBox;
    @BindView(R.id.robot_control_center)
    LinearLayout mRobotControlCenter;
    @BindView(R.id.robot_close)
    ImageView    mRobotClose;
    @BindView(R.id.robot_name_setting)
    ImageView    mRobotNameSetting;
    @BindView(R.id.robot_about_robot)
    ImageView    mRobotAboutRobot;
    @BindView(R.id.robot_setting_view)
    LinearLayout mRobotSettingView;
    Unbinder unbinder;
    @BindView(R.id.robot_info)
    CardView     mRobotInfo;
    @BindView(R.id.robot_setting)
    LinearLayout mRobotSetting;
    @BindView(R.id.robot_air_box_view_close)
    ImageView    mRobotAirBoxViewClose;
    @BindView(R.id.robot_air_box_temperature)
    TextView     mRobotAirBoxTemperature;
    @BindView(R.id.robot_air_box_ppm)
    TextView     mRobotAirBoxPpm;
    @BindView(R.id.robot_air_box_wetness)
    TextView     mRobotAirBoxWetness;
    @BindView(R.id.robot_air_box_pm)
    TextView     mRobotAirBoxPm;
    @BindView(R.id.robot_air_box_co)
    TextView     mRobotAirBoxCo;
    @BindView(R.id.robot_air_box_gas)
    TextView     mRobotAirBoxGas;
    @BindView(R.id.robot_air_box_ground_view)
    LinearLayout mRobotAirBoxGroundView;
    @BindView(R.id.robot_air_box_desktop_view)
    LinearLayout mRobotAirBoxDesktopView;
    @BindView(R.id.robot_air_box_ground_status_pic)
    ImageView    mRobotAirBoxGroundStatusPic;
    @BindView(R.id.robot_air_box_ground_status_btn)
    LinearLayout mRobotAirBoxGroundStatusBtn;
    @BindView(R.id.robot_cancel_custom_name)
    TextView     mRobotCancelCustomName;
    @BindView(R.id.robot_confirm_custom_name)
    TextView     mRobotConfirmCustomName;
    @BindView(R.id.robot_custom_name_et)
    EditText     mRobotCustomNameEt;
    @BindView(R.id.robot_custom_name_del)
    ImageView    mRobotCustomNameDel;
    @BindView(R.id.robot_name_setting_view)
    LinearLayout mRobotNameSettingView;
    @BindView(R.id.robot_air_box_desktop_view_close)
    View         mRobotAirBoxDesktopViewClose;
    @BindView(R.id.robot_air_box_desktop_btn)
    ImageView    mRobotAirBoxDesktopBtn;
    @BindView(R.id.robot_air_box_desktop_fan_status_pic)
    ImageView    mRobotAirBoxDesktopFanStatusPic;
    @BindView(R.id.robot_air_box_desktop_fan_status_tv)
    TextView    mRobotAirBoxDesktopFanStatusTv;

    public static RobotFragment newInstance(Robot robot) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("robot",robot);
        RobotFragment fragment = new RobotFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private Robot getRobot() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getParcelable("robot");
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_robot, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Robot robot = getRobot();
        if (robot == null) {
            return;
        }
        this.robot = robot;
        Log.d("zbj", "onViewCreated: " + robot.toString());

        if (TextUtils.equals(robot.getMonitor(), "true")) {
            mRobotMonitor.setEnabled(true);
        } else {
            mRobotMonitor.setEnabled(false);
        }

        mRobotName.setText(robot.getName());
        mRobotStatus.setText(robot.getStatus());
        mRobotAirBoxCo.setText(robot.getAirBox().getCo2());
        mRobotAirBoxPpm.setText(robot.getAirBox().getCh2o());
        mRobotAirBoxPm.setText(robot.getAirBox().getPm25());
        mRobotAirBoxWetness.setText(robot.getAirBox().getHumidity());

        if (TextUtils.equals(robot.getFanState(), "high")) {
            mRobotAirBoxDesktopFanStatusTv.setText("高速");
            mRobotAirBoxDesktopFanStatusPic.setImageResource(R.drawable.robot_wind_plus);
        } else if(TextUtils.equals(robot.getFanState(), "low")) {
            mRobotAirBoxDesktopFanStatusTv.setText("低速");
            mRobotAirBoxDesktopFanStatusPic.setImageResource(R.drawable.robot_wind_normal);
        } else if(TextUtils.equals(robot.getFanState(), "off")){
            mRobotAirBoxDesktopFanStatusTv.setText("关闭");
            mRobotAirBoxDesktopFanStatusPic.setImageResource(R.drawable.robot_wind_close);
        }

        if (TextUtils.equals("true", robot.getAirBox().getGas())) {
            mRobotAirBoxGas.setText("正常");
            mRobotAirBoxGas.setTextColor(Color.parseColor("#333333"));
        } else {
            mRobotAirBoxGas.setText("超标");
            mRobotAirBoxGas.setTextColor(Color.parseColor("#fe3618"));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.robot_video, R.id.robot_monitor, R.id.robot_air_box, R.id.robot_setting
            , R.id.robot_close, R.id.robot_name_setting, R.id.robot_about_robot
            , R.id.robot_air_box_view_close, R.id.robot_cancel_custom_name
            , R.id.robot_confirm_custom_name, R.id.robot_custom_name_del
            , R.id.robot_air_box_desktop_view_close ,R.id.robot_air_box_desktop_btn
            , R.id.robot_air_box_desktop_fan_status_pic})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), CallingActivity.class);
        intent.putExtra(VideoCallUA.CALL_COMING_OR_OUTGOING, VideoCallUA.CALL_OUTGOING);

        switch (view.getId()) {
            case R.id.robot_video:
                intent.putExtra("monitor", false);
                intent.putExtra("callUid",robot.getUid());
                startActivity(intent);
                ToastUtils.showToast(getActivity().getApplicationContext(), "发起视频");
                break;
            case R.id.robot_monitor:
                ToastUtils.showToast(getActivity().getApplicationContext(), "发起监控");
                intent.putExtra("monitor", true);
                intent.putExtra("callUid",robot.getUid());
                startActivity(intent);
                break;
            case R.id.robot_air_box:
                if (TextUtils.equals(robot.getType(), "ground")) {
                    mRobotAirBoxGroundView.setVisibility(View.VISIBLE);
                } else {//desktop
                    mRobotAirBoxDesktopView.setVisibility(View.VISIBLE);
                }
                mRobotControlCenter.setVisibility(View.INVISIBLE);
                break;
            case R.id.robot_air_box_view_close:
                mRobotAirBoxGroundView.setVisibility(View.INVISIBLE);
                mRobotControlCenter.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_air_box_desktop_view_close:
                mRobotAirBoxDesktopView.setVisibility(View.INVISIBLE);
                mRobotControlCenter.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_setting:
                ToastUtils.showToast(getActivity().getApplicationContext(), "设置");
                mRobotControlCenter.setVisibility(View.INVISIBLE);
                mRobotSettingView.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_close:
                mRobotSettingView.setVisibility(View.INVISIBLE);
                mRobotControlCenter.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_name_setting:
                ToastUtils.showToast(getActivity().getApplicationContext(), "名字设置");
                mRobotNameSettingView.setVisibility(View.VISIBLE);
                mRobotSettingView.setVisibility(View.INVISIBLE);
                break;
            case R.id.robot_about_robot:
                ToastUtils.showToast(getActivity().getApplicationContext(), "关于机器人");
                break;
            case R.id.robot_cancel_custom_name:
                mRobotNameSettingView.setVisibility(View.INVISIBLE);
                mRobotSettingView.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_confirm_custom_name:
                mRobotNameSettingView.setVisibility(View.INVISIBLE);
                mRobotSettingView.setVisibility(View.VISIBLE);
                break;
            case R.id.robot_custom_name_del:
                break;
        }
    }

    @Override
    public void onData(Robot robot) {
        Log.d("RobotFragment", "onData: " + robot.toString());
        this.robot = robot;
//        mRobotName.setText(robot.getName());
//        mRobotStatus.setText(robot.getStatus());
//        mRobotAirBoxCo.setText(robot.getAirBox().getCo2());
//        mRobotAirBoxPpm.setText(robot.getAirBox().getCh2o());
//        mRobotAirBoxTemperature.setText(robot.getAirBox().getTemperature());

    }
}
