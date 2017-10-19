package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;

import java.util.ArrayList;


/**
 * Created by apple1 on 2017/5/17.
 */

public class FamilyInviteAdapter extends BaseAdapter {

    private ArrayList<String> mList;
    private LayoutInflater mInflater;
    private Context context;

    public FamilyInviteAdapter(Context context, ArrayList<String> mList) {
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
        ViewHolder vh;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.invite_list_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
//        vh.tv_invite_name.setText(mList.get(position).getNickname());
//        Glide.with(context).load(mList.get(position).getAvatar()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.head).into(vh.iv_invite_head);

        return convertView;
    }
    class ViewHolder{
        ImageView iv_invite_head;
        TextView tv_invite_name;
        public ViewHolder(View view){
            iv_invite_head = (ImageView)view.findViewById(R.id.iv_invite_head);
            tv_invite_name = (TextView) view.findViewById(R.id.tv_invite_name);
        }
    }
}
