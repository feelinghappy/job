package com.longcai.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.PersonBean;
import com.longcai.medical.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {
    List<PersonBean> data;
    private int sm_id;
    private int is_member;
    private RelativeLayout layout;

    public CustomerListAdapter(List<PersonBean> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nickname;
        TextView tag;
        ImageView state;

        public ViewHolder(View itemView) {
            super(itemView);
            nickname = (TextView) itemView.findViewById(R.id.name_tx);
            tag = (TextView) itemView.findViewById(R.id.zm_tx);
            layout = (RelativeLayout) itemView.findViewById(R.id.contact_item_view2);
            state = (ImageView) itemView.findViewById(R.id.customer_btn_recommend);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mOnCustomerItemClickListener){
                mOnCustomerItemClickListener.onClick((int)v.getTag());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.listview_item_customer, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PersonBean person = data.get(position);
        // 获取首字母的assii值
        int selection = person.getFirstPinYin().charAt(0);
        // 通过首字母的assii值来判断是否显示字母
        int positionForSelection = getPositionForSelection(selection);
        if (position == positionForSelection) {// 相等说明需要显示字母
            holder.tag.setVisibility(View.VISIBLE);
            holder.tag.setText(person.getFirstPinYin());
        } else {
            holder.tag.setVisibility(View.GONE);
        }
        int customer_state = data.get(position).getCustomer_state();
        if (customer_state == 1){//潜在客户
            holder.state.setVisibility(View.VISIBLE);
        }else {//绑定
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setImageResource(R.mipmap.bangdingkehu);
        }
        holder.nickname.setText(data.get(position).getCustomer_name());
        sm_id = data.get(position).getSm_id();
        is_member = data.get(position).getIs_member();
        LogUtils.d("sm_id=="+sm_id);
        holder.itemView.setTag(position);
    }

    public int getPositionForSelection(int selection) {
        for (int i = 0; i < data.size(); i++) {
            String Fpinyin = data.get(i).getFirstPinYin();
            char first = Fpinyin.toUpperCase().charAt(0);
            if (first == selection) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private OnCustomerItemClickListener mOnCustomerItemClickListener = null;

    public interface OnCustomerItemClickListener {
        void onClick(int position);
    }
    public void setOnCusItemClickListener(OnCustomerItemClickListener listener){
        this.mOnCustomerItemClickListener = listener;
    }
}
