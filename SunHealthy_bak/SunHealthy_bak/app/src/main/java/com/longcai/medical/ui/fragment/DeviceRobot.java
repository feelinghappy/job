package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.bean.RobotManagment;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 机器人管理界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class DeviceRobot extends BaseFragment implements OnClickListener {

    private static final String TAG = "DeviceRobot";
    private View view;

    private Button addRobotBtn;
    private List<RobotManagment.RobotManagmentResult> familyLists;
    private ListView lv;
    private RelativeLayout rl;
    /**
     * 懒加载
     */
    private Boolean isFirst = true;
    protected String tag = "DeviceRobot";
    ImageView addRobot;
    private TextView tv_myRobot;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.device_manament_robot, null);
        lv = (ListView) view.findViewById(R.id.robot_managment_lv);
        rl = (RelativeLayout) view.findViewById(R.id.robot_managment_rl);
        addRobot = (ImageView) view.findViewById(R.id.device_robot_add);
        addRobotBtn = (Button) view.findViewById(R.id.device_robot_add_btn);
        addRobotBtn.setOnClickListener(this);
        tv_myRobot = (TextView) view.findViewById(R.id.robot_myRobot);
        return view;
    }

    @Override
    public void initData() {
        familyLists = new ArrayList<RobotManagment.RobotManagmentResult>();
        getServiceDaata();
    }

    @Override
    public void LoadData() {
        if (isFirst) {
            isFirst = false;
            System.out.println("机器人管理加载数据了");
            // getLocalData();
            getServiceDaata();
        }
    }

    /**
     * 本地模拟数据
     */
    private void getLocalData() {
        for (int i = 0; i < 3; i++) {
            RobotManagment.RobotManagmentResult robotManagment = new RobotManagment().new RobotManagmentResult();
            robotManagment.family_name = "邀请加入幸福一家" + i + "";
            familyLists.add(robotManagment);
        }

        setMyAdapte();
    }

    /**
     * 获取网络数据
     */
    private void getServiceDaata() {
        RequestParams params = new RequestParams(MyUrl.ROBOT_ADMINISTRATION);
        params.addBodyParameter("uid", Constant.UID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getServiceDaata(): onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getServiceDaata(): onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getServiceDaata(): onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getServiceDaata(): onFinished   ");

            }
        });

    }

    /**
     * 解析数据，集合添加数据
     *
     * @param result json数据
     */
    protected void processData(String result) {
        // TODO 解析数据，集合添加数据
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("code");
            if (code == 1) {
                Gson gson = new Gson();
                RobotManagment RobotManagment = gson.fromJson(result,
                        RobotManagment.class);
                LogUtils.i(tag, "processData..." + RobotManagment.toString());
                familyLists = RobotManagment.result;
            }
            setMyAdapte();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private int mPosition;

    /**
     * 适配
     */
    private void setMyAdapte() {
        if (familyLists.size() > 0) {
            mAdapter = new mMessageAdapter();
            lv.setAdapter(mAdapter);
        } else {
            tv_myRobot.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    //TODO  onClick(View v)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_robot_unbundling:
                Toast.makeText(mActivity, "解绑", Toast.LENGTH_SHORT).show();
                showPops();
                break;
            case R.id.pop_cen_positive_btn:
                pop.setDismiss();
                break;
            case R.id.device_robot_add_btn:
                Toast.makeText(mActivity, "添加机器人", Toast.LENGTH_SHORT).show();
                addRobot();
                break;
            case R.id.pop_cen_negative_btn:
                pop.setDismiss();
                setRobotRelieveToOur(); //解绑机器人
                break;
        }

    }

    /*
    * 添加绑定+机器人*/
    private void addRobot() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_robot);
        intent.putExtra(Constant.Robot_MARK, Constant.ROBOT_BUDLING);
        intent.putExtra(Constant.Robot_create_MARK, Constant.ROBOT_Add);
        startActivity(intent);
        getActivity().finish();
    }

    private Button positive, negative;

    /**
     * 显示下拉框
     */
    ShowPopupWindow pop;

    private void showPops() {
        pop = new ShowPopupWindow(mActivity);
        View view = View.inflate(mActivity, R.layout.popul_center_robot, null);
        positive = (Button) view.findViewById(R.id.pop_cen_positive_btn);
        negative = (Button) view.findViewById(R.id.pop_cen_negative_btn);
        positive.setOnClickListener(DeviceRobot.this);
        negative.setOnClickListener(DeviceRobot.this);
        PopupWindow showPopup = pop.showPopup(view);
        // showPops.showAtLocation(parent, gravity, x, y)
        showPopup.showAtLocation(lv, Gravity.CENTER, 0, 0);
    }


    /**
     * 机器人服务器，机器人解绑
     */
    private void setRobotRelieveToRobotService() {
        //(robotId - 机器人的id    robotSerial - 机器人序列号)


    }

    /**
     * 本服务器，机器人解绑
     */
    private void setRobotRelieveToOur() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", mFamily_id);
        map.put("robot_imei", mRobot_imei);
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.ROBOT_UNBUNDLING, map, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
                setRobotRelieveToRobotService();
            }

            @Override
            public void onSuccess(String objects) {

            }

            @Override
            public void onFail(int code, String msg) {

            }
        });

    }

    String mUid;
    String mFamily_id;
    String mRobot_imei;
    private mMessageAdapter mAdapter;

    class mMessageAdapter extends BaseAdapter {
        // TODO 适配器

        @Override
        public int getCount() {
            if (familyLists.size() < 1) {

            }
            return familyLists.size();
        }

        @Override
        public RobotManagment.RobotManagmentResult getItem(int position) {
            return familyLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity,
                        R.layout.device_managment_robot_item, null);
                holder = new ViewHolder();
                holder.unbundling = (Button) convertView
                        .findViewById(R.id.device_robot_unbundling);
                holder.name = (TextView) convertView
                        .findViewById(R.id.device_robot_name);
                holder.family = (TextView) convertView
                        .findViewById(R.id.device_robot_family);
                holder.imei = (TextView) convertView
                        .findViewById(R.id.device_robot_imei);

                holder.unbundling.setOnClickListener(DeviceRobot.this);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            mPosition = position;
            // TODO 根据本地记录来标记已读未读
            // String readIds = PrefUtils.getString(mActivity, "read_ids", "");
            // if (readIds.contains(news.id + "")) {
            // holder.tvTitle.setTextColor(Color.GRAY);
            // } else {
            // holder.tvTitle.setTextColor(Color.BLACK);
            // }

            // mBitmapUtils.display(holder.ivIcon, news.listimage);
            RobotManagment.RobotManagmentResult rManagmentResult = getItem(position);
            mUid = Constant.UID;
            mFamily_id = Constant.Family_id;
            mFamily_id = rManagmentResult.family_id;
            mRobot_imei = rManagmentResult.robot_imei;
            holder.family.setText(rManagmentResult.family_name);
            holder.imei.setText(rManagmentResult.robot_imei);

            return convertView;
        }

    }

    static class ViewHolder {
        public Button unbundling;
        public TextView name;
        public TextView family;
        public TextView imei;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }
}
