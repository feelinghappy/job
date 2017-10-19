package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.TeamDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public class TeamMemberAdapter extends BaseAdapter {
    private List<TeamDetail.TeamMemberInfoBean> mList;
    private Context context;
    private LayoutInflater mInflater;

    public TeamMemberAdapter(List<TeamDetail.TeamMemberInfoBean> mList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.team_member_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        myHolder.team_member_name.setText((position+1)+"."+mList.get(position).getSeller_name());
        myHolder.team_member_amount.setText(mList.get(position).getOrder_amount()+"元");
        return convertView;
    }

    public static class ViewHolder {
        public final TextView team_member_name;
        public final TextView team_member_amount;

        public ViewHolder(View root) {
            this.team_member_name = (TextView) root.findViewById(R.id.team_member_name);
            this.team_member_amount = (TextView) root.findViewById(R.id.team_member_amount);
        }
    }
}

