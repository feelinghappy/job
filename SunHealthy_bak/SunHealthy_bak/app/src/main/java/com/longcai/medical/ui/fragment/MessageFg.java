package com.longcai.medical.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.Messages;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseFragment;
import com.longcai.medical.utils.HttpUtils;
import com.longcai.medical.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 消息列表界面
 *
 * @author Administrator ajiang 2017.05.19
 */
public class MessageFg extends BaseFragment implements OnClickListener {

    protected static final String TAG = "MessageFg";
    private View view;
    private HashMap<String, String> map = new HashMap<>();
    private List<Messages.MessageResult> messageLists;
    private ListView lv;
    private RelativeLayout rl;
    private String mMessage_id;
    private int mPosition;
    TextView titleTv, title_ok;
    ImageView returIv;
    private String robot_imei;
    private String robotId;
    private String robotSerial;

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.message, null);

        lv = (ListView) view.findViewById(R.id.message_list_lv);
        rl = (RelativeLayout) view.findViewById(R.id.message_list_rl);
        returIv = (ImageView) view.findViewById(R.id.title_back);
        titleTv = (TextView) view.findViewById(R.id.title_tv);
        title_ok = (TextView) view.findViewById(R.id.title_right_tv);
        returIv.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        titleTv.setText("消息");
        title_ok.setVisibility(View.GONE);
        messageLists = new ArrayList<Messages.MessageResult>();
        Constant.isMessageHandle = true;
        getServiceData();
    }

    @Override
    public void LoadData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.message_have_agree:
                //  判断邀请我的家庭有没有绑定机器人
                isFamilyHasRobot();
                setService(MyUrl.MESSAGE_AGREE, Constant.UID, mMessage_id);
                break;
            case R.id.message_have_refuse:
                setService(MyUrl.MESSAGE_REFUSE, Constant.UID, mMessage_id);
                break;
            case R.id.title_back:
                getActivity().finish();
                break;
        }
    }

    private mMessageAdapter mAdapter;

    /**
     * 获取网络数据
     */
    private void getServiceData() {
        map.put("uid", Constant.UID);
        HttpUtils.xOverallHttpPost(getActivity(), MyUrl.MESSAGE_LIST, map, Messages.class, new HttpUtils.OnxHttpCallBack<Messages>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(Messages message) {
                messageLists = message.result;
                //获取机器人编码
                robot_imei = messageLists.get(mPosition).message_body.robot_imei;
                if (!TextUtils.isEmpty(robot_imei)){
                    robotId = StringUtils.getSubString(0, robot_imei.indexOf("-"), robot_imei);
                    robotSerial = StringUtils.getSubString(robot_imei.indexOf("-") + 1, robot_imei.length(), robot_imei);
                }
                mSetAdapter();
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }

    /**
     * 适配
     */
    private void mSetAdapter() {
        if (messageLists.size() > 0) {
            mAdapter = new mMessageAdapter();
            lv.setAdapter(mAdapter);
        } else {
            lv.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 上传网络 同意拒绝邀请
     *
     * @param url        路径
     * @param uid        会员id
     * @param message_id 信息ID
     */
    private void setService(String url, String uid, String message_id) {

        map.put("uid", uid);
        map.put("message_id", message_id);
        HttpUtils.xOverallHttpPost(getActivity(), url, map, new HttpUtils.OnxHttpCallBack<List<?>>() {
            @Override
            public void onSuccessMsg(String successMsg) {
            }

            @Override
            public void onSuccess(List<?> objects) {
                messageLists.remove(mPosition);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(int code, String msg) {
            }
        });
    }
    //  判断邀请我的家庭有没有绑定机器人
    private void isFamilyHasRobot() {
        if (!TextUtils.isEmpty(robot_imei)){
            if (Constant.ifRobotLogin){//判断机器人用户是否登录
                buildRobotService();//用户绑定到机器人服务器上
            }else {

            }
        }
    }
    /**
     * 机器人绑定到机器人厂家服务器
     */
    private void buildRobotService() {
        Constant.RobotId = robotId;
        Constant.RobotSerial = robotSerial;

    }
    class mMessageAdapter extends BaseAdapter {
        // TODO 适配器
        @Override
        public int getCount() {
            return messageLists.size();
        }

        @Override
        public Messages.MessageResult getItem(int position) {
            return messageLists.get(position);
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
                        R.layout.message_have_item, null);
                holder = new ViewHolder();
                holder.refuse = (Button) convertView
                        .findViewById(R.id.message_have_refuse);
                holder.agree = (Button) convertView
                        .findViewById(R.id.message_have_agree);
                holder.head = (ImageView) convertView
                        .findViewById(R.id.message_have_head);
                holder.name = (TextView) convertView
                        .findViewById(R.id.message_have_name);
                holder.family = (TextView) convertView
                        .findViewById(R.id.message_have_family);
                holder.add = (TextView) convertView
                        .findViewById(R.id.message_have_add);

                holder.refuse.setOnClickListener(MessageFg.this);
                holder.agree.setOnClickListener(MessageFg.this);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // TODO 根据本地记录来标记已读未读
            // String readIds = PrefUtils.getString(mActivity, "read_ids", "");
            // if (readIds.contains(news.id + "")) {
            // holder.tvTitle.setTextColor(Color.GRAY);
            // } else {
            // holder.tvTitle.setTextColor(Color.BLACK);
            // }

            // mBitmapUtils.display(holder.ivIcon, news.listimage);
            mPosition = position;
            Messages.MessageResult messageResult = getItem(position);
            holder.name.setText(messageResult.message_body.nickname);
            holder.family.setText(messageResult.message_body.family_name);
            mMessage_id = messageResult.message_id;
            if (messageResult.message_body.message_type.equals("1")) {
                holder.add.setText("申请加入");
            } else if (messageResult.message_body.message_type.equals("1")) {
                holder.add.setText("邀请你加入");
            }
            return convertView;
        }
    }

    static class ViewHolder {
        public Button refuse;
        public Button agree;
        public ImageView head;
        public TextView name;
        public TextView family;
        public TextView add;
    }
}
