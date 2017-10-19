package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetFamilyInfoBean;
import com.longcai.medical.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by apple1 on 2017/5/17.
 */

public class RemindTargetAdapter extends BaseAdapter {

    private List<GetFamilyInfoBean.MemberListBean> mList;
    private LayoutInflater mInflater;
    private Context context;
    private ITargetSelect iTargetSelect;

    public RemindTargetAdapter(Context context, List<GetFamilyInfoBean.MemberListBean> mList) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GetFamilyInfoBean.MemberListBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.target_list_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final GetFamilyInfoBean.MemberListBean member = mList.get(position);
        GetFamilyInfoBean.MemberListBean.MemberInfoBean memberInfo = member.getMember_info();
        if (memberInfo.getIs_member() == 1) {
            vh.llRoot.setVisibility(View.VISIBLE);
            Glide.with(context).load(memberInfo.getMember_avatar()).transform(new GlideCircleTransform(context)).placeholder(R.mipmap.head).into(vh.iv_target_head);
            if (member.isChecked()) {
                vh.iv_target_select.setChecked(true);
            } else {
                vh.iv_target_select.setChecked(false);
            }
            vh.tv_target_name.setText(memberInfo.getMember_name());
            vh.iv_target_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (iTargetSelect != null) {
                        iTargetSelect.targetSelect(position);
                    }
                }
            });
        } else if (memberInfo.getIs_member() == 0) {
            vh.llRoot.setVisibility(View.GONE);
            notifyDataSetChanged();
        }
        return convertView;
    }

    public interface ITargetSelect {
        void targetSelect(int position);
    }

    public void setiTargetSelect(ITargetSelect iTargetSelect) {
        this.iTargetSelect = iTargetSelect;
    }

    class ViewHolder{
        LinearLayout llRoot;
        ImageView iv_target_head;
        TextView tv_target_name;
        CheckBox iv_target_select;

        public ViewHolder(View view){
            llRoot = (LinearLayout) view.findViewById(R.id.llRoot);
            iv_target_head = (ImageView)view.findViewById(R.id.target_item_head);
            tv_target_name = (TextView) view.findViewById(R.id.target_item_name);
            iv_target_select = (CheckBox) view.findViewById(R.id.target_item_select);
        }
    }
}
