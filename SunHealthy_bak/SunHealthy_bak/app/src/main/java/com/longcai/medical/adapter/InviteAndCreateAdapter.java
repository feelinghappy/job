package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.utils.GlideCircleTransform;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/15.
 * 创建和邀请成员
 */

public class InviteAndCreateAdapter extends BaseAdapter {
    private ArrayList<String> avatarList;
    private ArrayList<String> nameList;
    private LayoutInflater mInflater;
    private Context context;

    public InviteAndCreateAdapter(Context context, ArrayList<String> avatarList,ArrayList<String> nameList) {
        this.mInflater = LayoutInflater.from(context);
        this.avatarList = avatarList;
        this.nameList = nameList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return avatarList.size();
    }

    @Override
    public Object getItem(int position) {
        return avatarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.invite_list_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();

        vh.tv_invite_name.setText(nameList.get(position));
        Glide.with(context).load(avatarList.get(position)).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.head).into(vh.iv_invite_head);
//        notifyDataSetChanged();
        return convertView;
    }


    class ViewHolder {
        ImageView iv_invite_head;
        TextView tv_invite_name;

        public ViewHolder(View view) {
            iv_invite_head = (ImageView) view.findViewById(R.id.iv_invite_head);
            tv_invite_name = (TextView) view.findViewById(R.id.tv_invite_name);
        }

    }
}
