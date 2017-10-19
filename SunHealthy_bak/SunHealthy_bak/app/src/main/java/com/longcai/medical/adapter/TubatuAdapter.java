package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.utils.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by apple1 on 2017/5/31.
 */
public class TubatuAdapter extends RecyclingPagerAdapter {

    private static final String TAG = "TubatuAdapter";
    private final List<GetFamilyInfoBean.MemberListBean> mList;
    private final Context mContext;


    public TubatuAdapter(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    public void addAll(List<GetFamilyInfoBean.MemberListBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            vh = new ViewHolder();
            vh.iv = (ImageView) convertView.findViewById(R.id.img);
            vh.tv = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(vh);
            convertView.setTag(R.id.tag_first_vp,position);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        try {
            vh.tv.setText(mList.get(position).getMember_info().getMember_name());
            Glide.with(mContext).load(mList.get(position).getMember_info().getMember_avatar()).transform(new GlideCircleTransform(mContext)).placeholder(R.mipmap.head).into(vh.iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
    private class ViewHolder{
        private ImageView iv;
        private TextView tv;
    }
}



