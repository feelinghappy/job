package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.OrderRuKuBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 * 仓库-入库和调货 共用一个adapter
 */

public class StoreOrderRuKuAdapter extends BaseAdapter {
    private List<OrderRuKuBean> orderList;
    private Context context;
    private LayoutInflater mInflater;
    private int tag = 0;

    public StoreOrderRuKuAdapter(List<OrderRuKuBean> orderList, Context context, int tag) {
        this.mInflater = LayoutInflater.from(context);
        this.orderList = orderList;
        this.context = context;
        this.tag = tag;
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
            convertView = mInflater.inflate(R.layout.store_order_ruku_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        OrderRuKuBean order = orderList.get(position);
        myHolder.order_item_date.setText(order.getGoods_time());
//        myHolder.order_item_status.setText(orderList.get(position).getStatus());
        myHolder.order_item_name.setText(order.getGoods_name());
        myHolder.order_item_company.setText(order.getCompany());
        int goods_commonid = order.getGoods_commonid();
        myHolder.order_item_amount.setText("库存总数：" + order.getGoods_num());
        myHolder.order_item_date.setText(order.getGoods_time());
        myHolder.order_item_status.setText("已入库");
        if (tag == 2){//已入库
            myHolder.order_item_iv.setImageResource(R.mipmap.duigou);
        }else if (tag == 4){//已调货
            myHolder.order_item_iv.setImageResource(R.mipmap.duigou);
        }
        return convertView;
    }

    public static class ViewHolder {
        public final TextView order_item_date;
        public final TextView order_item_name;
        public final TextView order_item_company;
        public final TextView order_item_amount;
        public final TextView order_item_status;
        public final ImageView order_item_iv;

        public ViewHolder(View root) {
            this.order_item_date = (TextView) root.findViewById(R.id.order_item_date);
            this.order_item_name = (TextView) root.findViewById(R.id.order_item_name);
            this.order_item_company = (TextView) root.findViewById(R.id.order_item_company);
            this.order_item_amount = (TextView) root.findViewById(R.id.order_item_amount);
            this.order_item_status = (TextView) root.findViewById(R.id.order_item_status);
            this.order_item_iv = (ImageView) root.findViewById(R.id.order_item_iv);
        }
    }
}
