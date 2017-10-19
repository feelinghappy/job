package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcai.medical.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 */
public class OrderRuKuInfoAdapter extends BaseAdapter {
//    private List<RuKuBean> orderList;
    private List<String> orderList;
    private Context context;
    private LayoutInflater mInflater;
    private int tag = 0;

    public OrderRuKuInfoAdapter(List<String> orderList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_ruku_info_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        myHolder.order_ruku_info_num.setText(position+1+".");
//        myHolder.order_ruku_info_name.setText(orderList.get(position).getName());
        myHolder.order_ruku_info_id.setText(orderList.get(position));
        return convertView;
    }

    public static class ViewHolder {
        public final TextView order_ruku_info_num;
        public final TextView order_ruku_info_name;
        public final TextView order_ruku_info_id;

        public ViewHolder(View root) {
            this.order_ruku_info_num = (TextView) root.findViewById(R.id.order_ruku_info_num);
            this.order_ruku_info_name = (TextView) root.findViewById(R.id.order_ruku_info_name);
            this.order_ruku_info_id = (TextView) root.findViewById(R.id.order_ruku_info_id);
        }
    }
}

