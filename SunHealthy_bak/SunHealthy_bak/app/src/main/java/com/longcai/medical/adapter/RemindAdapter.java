package com.longcai.medical.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.GetThingWarns;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 * 提醒列表
 */

public class RemindAdapter extends BaseAdapter {
    private Context context;
    private List<GetThingWarns> mList;
    private IOpenCloseWarn iOpenCloseWarn;

    public RemindAdapter(Context context, List<GetThingWarns> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GetThingWarns getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearItems() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.remind_list_item,null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        GetThingWarns warn = mList.get(position);
        String sendTime = warn.getSend_time();
        String repeatTime = warn.getRepeat_time();
        String warnsContent = warn.getWarns_content();
        String warnWarner = warn.getWarner();
        String acceptMemberName = warn.getAccept_member_name();
        String warnsState = warn.getWarns_state();
        if (warnsState.equals("1")) {
            vh.remind_toggleButton.setChecked(true);
        } else if (warnsState.equals("2")) {
            vh.remind_toggleButton.setChecked(false);
        }
        if (!TextUtils.isEmpty(sendTime)) {
            vh.tv_remind_time.setText(sendTime);
        }
        if (!TextUtils.isEmpty(repeatTime)) {
            if (repeatTime.contains("周一,周二,周三,周四,周五,周六,周日")) {
                vh.tv_remind_repeat.setText(repeatTime.replace("周一,周二,周三,周四,周五,周六,周日", "每天"));
            } else if (repeatTime.contains("周一,周二,周三,周四,周五")) {
                vh.tv_remind_repeat.setText(repeatTime.replace("周一,周二,周三,周四,周五", "工作日"));
            } else {
                vh.tv_remind_repeat.setText(repeatTime);
            }
        }
        if (!TextUtils.isEmpty(warnsContent)) {
            vh.tv_remind_content.setText(warnsContent);
        }
        if (!TextUtils.isEmpty(warnWarner)) {
            if (warnWarner.equals("2")) {
                vh.tv_remind_target.setText("发送给：机器人");
            } else if (warnWarner.equals("3")) {
                vh.tv_remind_target.setText("发送给：家庭");
            } else if (warnWarner.equals("4") && !TextUtils.isEmpty(acceptMemberName)) {
                vh.tv_remind_target.setText("发送给：" + acceptMemberName);
            }
        }
        vh.remind_toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (null != iOpenCloseWarn) {
                    iOpenCloseWarn.openCloseWarn(position);
                }
            }
        });
        return convertView;
    }

    public interface IOpenCloseWarn {
        void openCloseWarn(int position);
    }

    public void setiOpenCloseWarn(IOpenCloseWarn iOpenCloseWarn) {
        this.iOpenCloseWarn = iOpenCloseWarn;
    }

    class ViewHolder{
        TextView tv_remind_time;
        TextView tv_remind_content;
        TextView tv_remind_repeat;
        TextView tv_remind_target;
        CheckBox remind_toggleButton;
        public ViewHolder(View view){
            tv_remind_time = (TextView)view.findViewById(R.id.remind_item_time);
            tv_remind_content = (TextView) view.findViewById(R.id.remind_item_content);
            tv_remind_repeat = (TextView) view.findViewById(R.id.remind_item_repeat);
            tv_remind_target = (TextView) view.findViewById(R.id.remind_item_target);
            remind_toggleButton = (CheckBox) view.findViewById(R.id.remind_toggleButton);
        }
    }
}
