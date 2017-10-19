package com.longcai.medical.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.FamilyOwn;
import com.longcai.medical.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */

public class OwnFamilyAdapter extends BaseAdapter {
    private List<FamilyOwn> mLists;
    private Context mContext;
    private IBindRobot iBindRobot;

    public OwnFamilyAdapter(List<FamilyOwn> lists, Context context) {
        this.mLists = lists;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public FamilyOwn getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.binding_have_fam_item, null);
            holder = new ViewHolder();
            holder.bundling = (Button) convertView
                    .findViewById(R.id.have_fam_bundling);
            holder.name = (TextView) convertView
                    .findViewById(R.id.have_fam_name);
//            holder.num = (TextView) convertView
//                    .findViewById(R.id.have_fam_num);
//            holder.count = (TextView) convertView
//                    .findViewById(R.id.have_fam_count);
            holder.iv = (ImageView) convertView
                    .findViewById(R.id.have_fam_item_iv);
            holder.bundling.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != iBindRobot) {
                        iBindRobot.bindRobot(position);
                    }
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
//            mPosition = position;
        // TODO 根据本地记录来标记已读未读
        // String readIds = PrefUtils.getString(mActivity, "read_ids", "");
        // if (readIds.contains(news.id + "")) {
        // holder.tvTitle.setTextColor(Color.GRAY);
        // } else {
        // holder.tvTitle.setTextColor(Color.BLACK);
        // }

        // mBitmapUtils.display(holder.ivIcon, news.listimage);
        FamilyOwn familyOwn = getItem(position);

        holder.name.setText(familyOwn.getFamily_name());
//        holder.num.setText(familyOwn.getFamily_number());
//        holder.count.setText(familyOwn.getMember_count());
        String thumb = familyOwn.getThumb();
        if (!TextUtils.isEmpty(thumb)) {
            if (thumb.equals("1")) {
                holder.iv.setImageResource(R.mipmap.family_list1);
            } else if (thumb.equals("2")) {
                holder.iv.setImageResource(R.mipmap.family_list2);
            } else if (thumb.equals("3")) {
                holder.iv.setImageResource(R.mipmap.family_list3);
            } else {
                Glide.with(mContext).load(thumb)
                        .transform(new GlideCircleTransform(mContext, 0, Color.TRANSPARENT))
                        .placeholder(R.mipmap.head).into(holder.iv);
            }
        } else {
            holder.iv.setImageResource(R.mipmap.family_list1);
        }
//        Glide.with(mContext).load(thumb).transform(new GlideRoundTransform(mContext))
//                .placeholder(R.mipmap.head).into(holder.iv);
        return convertView;
    }

    public interface IBindRobot {
        void bindRobot(int position);
    }

    public void setiBindRobot(IBindRobot iBindRobot) {
        this.iBindRobot = iBindRobot;
    }

    static class ViewHolder {
        public Button bundling;
        public TextView name;
//        public TextView num;
//        public TextView count;
        public ImageView iv;
    }
}
