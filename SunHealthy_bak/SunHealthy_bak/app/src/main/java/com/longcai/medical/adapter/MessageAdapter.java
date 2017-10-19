package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.MsgInfo;
import com.longcai.medical.utils.StringUtils;

import java.util.List;

/**
 * 系统消息Adapter
 */
public class MessageAdapter extends BaseAdapter {
    private List<MsgInfo.MsgInfoBean> mList;
    private LayoutInflater mInflater;
    private Context context;

    public MessageAdapter(Context context, List<MsgInfo.MsgInfoBean> mList) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public MsgInfo.MsgInfoBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.messages_list_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        String notice = mList.get(position).getNotice();
        /*if (Constant.isReadNotice){
            notice = "1";
        }*/
        if (mList.get(position).getMessage_type().equals("1")){
            if (notice.equals("1")) {//1 已读 0 未读
                myHolder.iv_message_item.setImageResource(R.mipmap.icon_system_notice);
            }else {
                myHolder.iv_message_item.setImageResource(R.mipmap.icon_system_point);
            }
        }else {
            if (notice.equals("1")) {
                myHolder.iv_message_item.setImageResource(R.mipmap.icon_invitation);
            }else {
                myHolder.iv_message_item.setImageResource(R.mipmap.icon_invitation_red);
            }
        }

        if (StringUtils.isEmpty(mList.get(position).getMessage_title())) {
            if (mList.get(position).getMessage_type().equals("1")){
                myHolder.tv_message_item.setText("系统通知");
            }else {
                myHolder.tv_message_item.setText("家庭消息");
            }
        } else {
            myHolder.tv_message_item.setText(mList.get(position).getMessage_title());
        }
        myHolder.tv_message_item.setLeft(22);

        return convertView;
    }
    //通过向自定义的Adapte中增加更新数据的方法
    public void addItems(List<MsgInfo.MsgInfoBean> list){
        if (list != null){
            mList = list;
            notifyDataSetChanged();
        }
    }
    /**
     * ViewHolder1 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        public final ImageView iv_message_item;
        public final TextView tv_message_item;
        public final ImageView right_message_item;

        public ViewHolder(View root) {
            this.root = root;
            this.iv_message_item = (ImageView) this.root.findViewById(R.id.messagelist_item_system_iv);
            this.iv_message_item.setVisibility(View.VISIBLE);
            this.tv_message_item = (TextView) this.root.findViewById(R.id.messagelist_item_tv);
            this.right_message_item = (ImageView) this.root.findViewById(R.id.messagelist_item_right);
        }
    }
}
