package com.longcai.medical.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.OwnFamilyAdapter;
import com.longcai.medical.bean.FamilyOwn;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.ui.activity.family.CreateFamilyActivity;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.LogUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 绑定已有家庭界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class BindingHaveFamFg extends BaseFragment implements OnClickListener {

    private static final String TAG = "BindingHaveFamFg";
    private View view;
    private List<FamilyOwn> mLists;
    private ListView lv;
    private RelativeLayout rl;
    protected String tag = "BindingHaveFamFg";
    Button createBtn;
    TextView titleTv, title_ok;
    ImageView returIv;
    private boolean fromDevices;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.binding_fam_fg, null);
        lv = (ListView) view.findViewById(R.id.binging_have_fam_lv);
        rl = (RelativeLayout) view.findViewById(R.id.binging_have_fam_rl);
        createBtn = (Button) view.findViewById(R.id.binging_have_creat_btn);

        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        titleTv.setText("选择已有家庭");
        title_ok.setVisibility(View.GONE);
        mLists = new ArrayList<FamilyOwn>();
        Bundle bundle = getArguments();
        if (null != bundle) {
            fromDevices = bundle.getBoolean("from_devices");
        }
        // getLocalData();
        getServiceData();
    }

    /**
     * 获取网络数据
     */
    private void getServiceData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.HAVE_FAMILY_LIST, map, FamilyOwn.class, new HttpUtils.OnxHttpWithListResultCallBack<FamilyOwn>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<FamilyOwn> mFamilyList) {
                if (mFamilyList != null && mFamilyList.size() > 0) {
                    mLists.addAll(mFamilyList);
                    setMyAdapter();
                } else {

                }
            }

            @Override
            public void onFail(int code, String msg) {
//                if (code == 10080) {

//                }
                LogUtils.e("family_list:" + code + msg);
            }
        });

    }

    /**
     * 适配
     */
    private void setMyAdapter() {
        if (mLists.size() > 0) {
            OwnFamilyAdapter adapter = new OwnFamilyAdapter(mLists, getActivity());
            lv.setAdapter(adapter);
            adapter.setiBindRobot(new OwnFamilyAdapter.IBindRobot() {
                @Override
                public void bindRobot(int position) {
                    setBudlingOurService(mLists.get(position).getFamily_id());
                }
            });
//            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    setBudlingOurService(mLists.get(i).getFamily_id());
//                }
//            });
        } else {
            lv.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    // String mFamilyId = ConstantY.Family_id;
    // String mRobotImei = ConstantY.Robot_imei;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.have_fam_bundling:
                Toast.makeText(mActivity, "绑定", Toast.LENGTH_SHORT).show();
//                setBudlingOurService();
                break;
            case R.id.title_back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.binging_have_creat_btn:
                Constant.CreateFamilyHaveScaner = true;
                Intent intent = new Intent(mActivity, CreateFamilyActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                mActivity.finish();
                break;
        }

    }

    /**
     * 机器人绑定到机器人厂家服务器
     */
    private void setBudlingRobotService() {
        Log.e(TAG, "setBudlingRobotService: phone  " + Constant.PHONE_NUMBER + "  robotId  " +
                Constant.RobotId + "  robotSerial  " + Constant.RobotSerial);
        //(robotId - 机器人的id    robotSerial - 机器人序列号)
    }

    /**
     * 机器人绑定到自己服务器
     */
    private void setBudlingOurService(String mFamily_id) {
        HashMap<String, String> map = new HashMap<>();
        map.put("token", MyApplication.myPreferences.readToken());
        map.put("family_id", mFamily_id);
        map.put("robot_imei", Constant.Robot_imeiResult);
//        if (isReplaceRobot) {
//            isReplaceRobot = false;
//            map.put("old_robot_imei", mRobot_imei);
//        }
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.ROBOT_BUNDLING, map, String.class, new HttpUtils.OnxHttpCallBack<String>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(String objects) {
//                Constant.Robot_imeiResult = "0";
//                if (isReplaceRobot) {
//                    unBudlingRobot(true);//解绑机器人
//                } else {
                    setBudlingRobotService();//绑定机器人
//                }
//                getActivity().finish();
                Constant.isRobotBudling = true;
            }

            @Override
            public void onFail(int code, String msg) {
                if (code == 10162) {
                    ToastUtils.showToast(getActivity(), "机器人编码错误，无法换绑");
                } else {
                    ToastUtils.showToast(getActivity(), "绑定失败");
                }
                //创建家庭失败（绑定机器人失败）
                Constant.isRobotBudling = false;
//                getActivity().finish();
            }
        });
    }


//    int mPosition;

//    class mMessageAdapter extends BaseAdapter {
//
//        public mMessageAdapter() {
//
//        }
//
//        // TODO 适配器
//
//        @Override
//        public int getCount() {
//            return mLists.size();
//        }
//
//        @Override
//        public FamilyOwn getItem(int position) {
//            return mLists.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                convertView = View.inflate(mActivity,
//                        R.layout.binding_have_fam_item, null);
//                holder = new ViewHolder();
//                holder.bundling = (Button) convertView
//                        .findViewById(R.id.have_fam_bundling);
//                holder.name = (TextView) convertView
//                        .findViewById(R.id.have_fam_name);
//                holder.num = (TextView) convertView
//                        .findViewById(R.id.have_fam_num);
//                holder.count = (TextView) convertView
//                        .findViewById(R.id.have_fam_count);
//                holder.iv = (ImageView) convertView
//                        .findViewById(R.id.have_fam_item_iv);
//                holder.bundling.setOnClickListener(BindingHaveFamFg.this);
//
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
////            mPosition = position;
//            // TODO 根据本地记录来标记已读未读
//            // String readIds = PrefUtils.getString(mActivity, "read_ids", "");
//            // if (readIds.contains(news.id + "")) {
//            // holder.tvTitle.setTextColor(Color.GRAY);
//            // } else {
//            // holder.tvTitle.setTextColor(Color.BLACK);
//            // }
//
//            // mBitmapUtils.display(holder.ivIcon, news.listimage);
//            FamilyOwn familyOwn = getItem(position);
//
//            holder.name.setText(familyOwn.getFamily_name());
//            holder.num.setText(familyOwn.getFamily_number());
//            holder.count.setText(familyOwn.getMember_count());
//            String is_image = familyOwn.getIs_image();
////            if (is_image.equals("1")) {
////                //bitmapUtils.display(holder.iv, resultList.thumb);
////                holder.iv.setImageURI(resultList.thumb);
////            }
//            Glide.with(getActivity()).load(familyOwn.getThumb()).transform(new GlideRoundTransform(getActivity()))
//                    .placeholder(R.mipmap.head).into(holder.iv);
//            return convertView;
//        }
//
//    }

//    static class ViewHolder {
//        public Button bundling;
//        public TextView name;
//        public TextView num;
//        public TextView count;
//        public ImageView iv;
//    }
}
