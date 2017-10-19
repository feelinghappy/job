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
import com.longcai.medical.R;
import com.longcai.medical.bean.WatchManagment;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.view.ShowPopupWindow;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 穿戴设备管理界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class DeviceWhatch extends BaseFragment implements OnClickListener {

    private View view;
    private List<WatchManagment> familyLists;
    private ListView lv;
    private RelativeLayout rl;
    private String TAG = "DeviceWhatch";
    private TextView tv_myWatch;
    /**
     * 懒加载
     */
    private Boolean isFirst = true;
    ImageView addWhatch;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.device_manament_watch, null);
        lv = (ListView) view.findViewById(R.id.watch_managment_lv);
        rl = (RelativeLayout) view.findViewById(R.id.watch_managment_rl);
        addWhatch = (ImageView) view.findViewById(R.id.device_whatch_add);
        addWhatch.setOnClickListener(this);
        tv_myWatch = (TextView) view.findViewById(R.id.robot_myWatch);
        return view;
    }

    @Override
    public void initData() {
        familyLists = new ArrayList<WatchManagment>();
        getServiceDaata();
    }

    @Override
    public void LoadData() {
        if (isFirst) {
            isFirst = false;
            System.out.println("穿戴设备管理加载数据了");
            // getLocalData();
            getServiceDaata();
        }
    }


    /**
     * 本地模拟数据
     */
    private void getLocalData() {
        for (int i = 0; i < 3; i++) {
            WatchManagment watchManagment = new WatchManagment();
//            watchManagment = "邀请加入幸福一家" + i + "";
            familyLists.add(watchManagment);
        }

        setMyAdapter();
    }

    /**
     * 获取网络数据
     */
    private void getServiceDaata() {
        RequestParams params = new RequestParams(MyUrl.WHATCH_LIST);
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
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("code");
            if (code == 1) {
                Gson gson = new Gson();
                WatchManagment watchManagment = gson.fromJson(result,
                        WatchManagment.class);
                LogUtils.i(TAG, "processData..." + watchManagment.toString());
//                familyLists = watchManagment.result;
            } else {

            }
            setMyAdapter();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 适配
     */
    private void setMyAdapter() {
        if (familyLists.size() > 0) {
            mAdapter = new mWatchAdapter();
            lv.setAdapter(mAdapter);
        } else {
            tv_myWatch.setVisibility(View.GONE);
            lv.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    //TODO onClick(View v)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.device_whatch_replace:
                Toast.makeText(mActivity, "更换", Toast.LENGTH_SHORT).show();
                showPopup();
                break;
            case R.id.device_whatch_unbundling:
                Toast.makeText(mActivity, "解除", Toast.LENGTH_SHORT).show();
                setWhatchRelieve();
                break;
            case R.id.device_whatch_add:
                addWhatch();
                break;
            case R.id.pop_whatch__positive_btn:
                Toast.makeText(getActivity(), "取消更换手表", Toast.LENGTH_SHORT).show();
                pop.setDismiss();
                break;
            case R.id.pop_whatch__negative_btn:
                Toast.makeText(getActivity(), "更换手表", Toast.LENGTH_SHORT).show();
                pop.setDismiss();
                setReplace();//更换手表操作
                break;

        }

    }

    /*
    * 添加手表设备*/
    private void addWhatch() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);
        intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_BUDLING);
        intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_Robot);
        getActivity().startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
    }

    /**
     * 执行更换手表操作
     */
    private void setReplace() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        intent.putExtra(Constant.SCAN_MARK, Constant.SCAN_MARK_watch);
        intent.putExtra(Constant.WHATCH_imei, mWatch_imei);
        intent.putExtra(Constant.WHATCH_MARK, Constant.WHATCH_REPLACE);
        intent.putExtra(Constant.WHATCH_Budling_From_markF, Constant.WHATCH_Budling_From_Robot);
        getActivity().startActivityForResult(intent, Constant.whatch_REQUEST_CODE);
    }

    private Button positive, negative;

    /**
     * 显示下拉框
     */
    ShowPopupWindow pop;

    private void showPopup() {
        pop = new ShowPopupWindow(mActivity);
        View view = View.inflate(mActivity, R.layout.popul_center_whatch, null);
        positive = (Button) view.findViewById(R.id.pop_whatch__positive_btn);
        negative = (Button) view.findViewById(R.id.pop_whatch__negative_btn);
        positive.setOnClickListener(DeviceWhatch.this);
        negative.setOnClickListener(DeviceWhatch.this);
        PopupWindow showPopup = pop.showPopup(view);
        // shoeDialog.showAtLocation(parent, gravity, x, y)
        showPopup.showAtLocation(lv, Gravity.CENTER, 0, 0);
    }


    String mWatch_imei;
    int mPosition;
    private mWatchAdapter mAdapter;

    /**
     * 手表设备解绑、与更换
     */
    private void setWhatchRelieve() {
        RequestParams params = new RequestParams(MyUrl.WHATCH_UNBUDLING);
        params.addBodyParameter("uid", Constant.UID);
        params.addBodyParameter("watch_imei", mWatch_imei);
        LogUtils.i(TAG, "setWhatchRelieve().." + mWatch_imei);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getServiceDaata(): onSuccess   " + result);
                try {
                    JSONObject jso = new JSONObject(result);
                    String code = jso.getString("code");
                    if (code.equals("1")) {
                        familyLists.remove(mPosition);
                        mAdapter.notifyDataSetChanged();
                        if (familyLists.size() < 1) {
                            lv.setVisibility(View.GONE);
                            rl.setVisibility(View.VISIBLE);
                        }
                        Intent intent = new Intent();
                        getActivity().setResult(Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_suc, intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    class mWatchAdapter extends BaseAdapter {
        // TODO 适配器

        @Override
        public int getCount() {
            return familyLists.size();
        }

        @Override
        public WatchManagment getItem(int position) {
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
                        R.layout.device_managment_watch_item, null);
                holder = new ViewHolder();
                holder.imei = (TextView) convertView
                        .findViewById(R.id.device_whatch_imei);
                holder.replace = (Button) convertView
                        .findViewById(R.id.device_whatch_replace);
                holder.unbundling = (Button) convertView
                        .findViewById(R.id.device_whatch_unbundling);
                holder.replace.setOnClickListener(DeviceWhatch.this);
                holder.unbundling.setOnClickListener(DeviceWhatch.this);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

//            WatchManagment.WatchManagmentResult wManagmentResult = getItem(position);
//            holder.imei.setText(wManagmentResult.watch_imei);
//            mWatch_imei = wManagmentResult.watch_imei;
            mPosition = position;
            //  根据本地记录来标记已读未读
            // String readIds = PrefUtils.getString(mActivity, "read_ids", "");
            // if (readIds.contains(news.id + "")) {
            // holder.tvTitle.setTextColor(Color.GRAY);
            // } else {
            // holder.tvTitle.setTextColor(Color.BLACK);
            // }

            // mBitmapUtils.display(holder.ivIcon, news.listimage);

            return convertView;
        }

    }

    static class ViewHolder {
        public TextView imei;
        public Button replace;
        public Button unbundling;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFirst = true;
    }
}
