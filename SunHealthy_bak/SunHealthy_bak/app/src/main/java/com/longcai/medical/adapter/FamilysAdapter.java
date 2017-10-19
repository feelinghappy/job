package com.longcai.medical.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.FamilyRemind;
import com.longcai.medical.utils.GlideCircleTransform;
import com.longcai.medical.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class FamilysAdapter extends BaseAdapter {
    private List<FamilyRemind> familyLists;
    private Activity context;

    // TODO 适配器
    public FamilysAdapter(Activity context, List<FamilyRemind> familyLists) {
        this.context = context;
        this.familyLists = familyLists;
    }

    @Override
    public int getCount() {
        return familyLists.size();
    }

    @Override
    public Object getItem(int position) {
        return familyLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearItems() {
        familyLists.clear();
        notifyDataSetChanged();
    }

    public void setFamilyLists(List<FamilyRemind> familyLists) {
        this.familyLists = familyLists;
        notifyDataSetChanged();
    }

    public void addRemindList(List<FamilyRemind> familyLists) {
        this.familyLists.addAll(familyLists);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.invite_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        LogUtils.d("家庭提醒内容 " + familyLists.get(position).getWarns_content());
        holder.tv_warn_content.setText(familyLists.get(position).getWarns_content());
        //img左边距20
        holder.iv_family_head.setVisibility(View.GONE);
        holder.iv_family_head2.setVisibility(View.VISIBLE);
        if (position == 0) {
            Glide.with(context).load(R.mipmap.icon_family_avatar_default).transform(new GlideCircleTransform(context))
                    .into(holder.iv_family_head2);
        } else {
            Glide.with(context).load(familyLists.get(position).getSend_member_avatar()).transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.head).into(holder.iv_family_head2);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_family_head;
        ImageView iv_family_head2;
        TextView tv_warn_content;

        public ViewHolder(View view) {
            iv_family_head = (ImageView) view.findViewById(R.id.iv_invite_head);
            iv_family_head2= (ImageView) view.findViewById(R.id.iv_invite_head2);
            tv_warn_content = (TextView) view.findViewById(R.id.tv_invite_name);
        }
    }
}


