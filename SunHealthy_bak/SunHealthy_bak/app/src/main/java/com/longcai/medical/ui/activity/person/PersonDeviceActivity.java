package com.longcai.medical.ui.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.DeviceAdapter;
import com.longcai.medical.bean.DeviceBean;
import com.longcai.medical.bean.MyDeviceResult;
import com.longcai.medical.bean.Robot;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.GloabeActivityFirst;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.StringUtils;
import com.longcai.medical.utils.ToastUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/4.
 * 个人信息 我的设备
 */

public class PersonDeviceActivity extends BaseActivity implements DeviceAdapter.IRobotUnbind, DeviceAdapter.IWatchUnbind {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.person_robot_lv)
    ListView personRobotLv;
//    @BindView(R.id.person_watch_lv)
//    ListView personWatchLv;
//    @BindView(R.id.person_device_bind)
    LinearLayout personDeviceBind;
    private Unbinder unbinder;
    private HashMap<String,String> map = new HashMap<>();
    private List<DeviceBean> devices = new ArrayList<>();
//    private ArrayList<String> watch_list = new ArrayList<>();
//    private PersonRobotAdapter personRobotAdapter;
    private LayoutInflater mInflater;
    private View delete_pop;
    private PopupWindow popupWindow;
    private TextView tvTitle;
    private TextView deleteOk;
    private TextView deleteCancel;
    private DeviceAdapter deviceAdapter;
//    private PersonWatchAdapter personWatchAdapter;
    private boolean fromRobot = false;
    private boolean bindSuccess = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_device);
        unbinder = ButterKnife.bind(this);
        mInflater = LayoutInflater.from(PersonDeviceActivity.this);
        fromRobot = getIntent().getBooleanExtra("fromRobot", false);
        initView();
    }

    private void initFooter() {
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        personDeviceBind = (LinearLayout) mInflater.inflate(R.layout.list_footer_devices, null);
        personDeviceBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonDeviceActivity.this, CaptureActivity.class);
                intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_device);
                intent.putExtra(Constant.DEVICE_MARK, Constant.DEVICE_BUDLING);
                intent.putExtra(Constant.Device_create_MARK, Constant.DEVICE_manager);
                startActivityForResult(intent, 15);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        personDeviceBind.setLayoutParams(layoutParams);
        personRobotLv.addFooterView(personDeviceBind);
    }

    private void initView() {
        delete_pop = mInflater.inflate(R.layout.popop_delete_member, null);
        tvTitle = (TextView) delete_pop.findViewById(R.id.tv_title);
        tvTitle.setText("您确定要解绑该设备吗？");
        deleteOk = (TextView)delete_pop.findViewById(R.id.delete_ok);
        deleteCancel = (TextView)delete_pop.findViewById(R.id.delete_cancel);
        initFooter();
        //机器人list
        setRobotList();
//        setWatchList();
    }

    private void back() {
        if (fromRobot && bindSuccess) {
            startActivity(new Intent(this, GloabeActivityFirst.class).putExtra("refreshRobotList", true));
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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

    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                back();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15 && resultCode == Constant.Manager_family_CODE) {
            bindSuccess = true;
            setRobotList();
        }
    }

    private void setAdapter() {
        deviceAdapter = new DeviceAdapter(devices, this);
        personRobotLv.setAdapter(deviceAdapter);
        deviceAdapter.setiRobotUnbind(this);
        deviceAdapter.setiWatchUnbind(this);
    }

    //获取可管理机器人列表
    private void setRobotList() {
        map.clear();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(this, MyUrl.DEVICE_LIST, map, MyDeviceResult.class, new HttpUtils.OnxHttpCallBack<MyDeviceResult>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                    }

                    @Override
                    public void onSuccess(final MyDeviceResult deviceResult) {
                        if (!devices.isEmpty()) {
                            devices.clear();
                        }
                        List<Robot> robotList = deviceResult.getRobot_imei();
                        for (Robot robot : robotList) {
                            DeviceBean device = new DeviceBean();
                            device.setId(1);
                            device.setFamily_id(robot.getFamily_id());
                            device.setFamily_name(robot.getFamily_name());
                            device.setRobot_imei(robot.getRobot_imei());
                            devices.add(device);
                        }
                        String watchImei = deviceResult.getWatch_imei();
                        if (!TextUtils.isEmpty(watchImei)) {
                            DeviceBean device = new DeviceBean();
                            device.setId(2);
                            device.setWatch_imei(watchImei);
                            devices.add(device);
                        }
//                        personRobotAdapter = new PersonRobotAdapter(PersonDeviceActivity.this,robotList);
//                        personRobotLv.setAdapter(personRobotAdapter);
//                        //ListView item 中的解绑按钮的点击事件
//                        personRobotAdapter.setOnItemDeleteClickListener(new PersonRobotAdapter.onItemUnbindListener() {
//                            @Override
//                            public void onUnbindClick(int i) {
//                                popupWindow = new ShowPopupWindow(PersonDeviceActivity.this).showPopup(delete_pop);
//                                popupWindow.showAtLocation(personRobotLv, Gravity.CENTER, 0, 0);
//                                initRobotPop(i,robotList,personRobotAdapter);
//                            }
//                        });
                        setAdapter();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        setAdapter();
//                        setWatchList();
                    }
                });
    }

    private void initRobotPop(final int i, final List<DeviceBean> mList, final BaseAdapter baseAdapter) {
        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //解绑
                map.clear();
                map.put("token", MyApplication.myPreferences.readToken());
                map.put("family_id", mList.get(i).getFamily_id());
                map.put("robot_imei", mList.get(i).getRobot_imei());
                HttpUtils.xOverallHttpPost(PersonDeviceActivity.this, MyUrl.ROBOT_UNBUNDLING, map,new HttpUtils.OnxHttpCallBack<List<?>>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {

                    }

                    @Override
                    public void onSuccess(List<?> s) {
                        ToastUtils.showToast(PersonDeviceActivity.this, "解绑成功");
                        bindSuccess = true;

                        String robotImei = mList.get(i).getRobot_imei();
                        String robotId = StringUtils.getSubString(0, robotImei.indexOf("-"), robotImei);
                        String robotSerial = StringUtils.getSubString(robotImei.indexOf("-") + 1, robotImei.length(), robotImei);
                        setRobotList();

                    }

                    @Override
                    public void onFail(int code, String msg) {
                        if (code == 11172){
                            ToastUtils.showToast(PersonDeviceActivity.this,"非家庭创建者无法解绑");
                        }
                        setRobotList();
                    }
                });
                //需要从本地和机器人服务器解绑
            }
        });
        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void initWatchPop(final int i, final List<DeviceBean> mList, final BaseAdapter baseAdapter) {
        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                //解绑
                map.clear();
                map.put("token", MyApplication.myPreferences.readToken());
                map.put("watch_imei", mList.get(i).getWatch_imei());
                HttpUtils.xOverallHttpPost(PersonDeviceActivity.this, MyUrl.WHATCH_UNBUDLING, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
                    @Override
                    public void onSuccessMsg(String successMsg) {
                        ToastUtils.showToast(PersonDeviceActivity.this, "解绑成功");
                        bindSuccess = true;
                        setRobotList();
                    }

                    @Override
                    public void onSuccess(List<?> list) {

//                        mList.remove(i);
//                        baseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        setRobotList();
                    }
                });
                //需要从本地和机器人服务器解绑
            }
        });
        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void robotUnbind(int position) {
        popupWindow = new ShowPopupWindow(PersonDeviceActivity.this).showPopup(delete_pop);
        popupWindow.showAtLocation(personRobotLv, Gravity.CENTER, 0, 0);
        initRobotPop(position, devices, deviceAdapter);
    }

    @Override
    public void watchUnbind(int position) {
        popupWindow = new ShowPopupWindow(PersonDeviceActivity.this).showPopup(delete_pop);
        popupWindow.showAtLocation(personRobotLv, Gravity.CENTER, 0, 0);
        initWatchPop(position, devices, deviceAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        boolean refresh_devices = intent.getBooleanExtra("refresh_devices", false);
        bindSuccess = true;
        if (refresh_devices) {
            setRobotList();
        }
    }

}
