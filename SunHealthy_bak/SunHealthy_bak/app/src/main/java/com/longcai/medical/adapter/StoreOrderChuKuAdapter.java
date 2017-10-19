package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.OrderChuKuBean;
import com.longcai.medical.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 */

public class StoreOrderChuKuAdapter extends BaseAdapter {
    private List<OrderChuKuBean> orderList;
    private Context context;
    private LayoutInflater mInflater;
    private int tag = 0;

    public StoreOrderChuKuAdapter(List<OrderChuKuBean> orderList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.orderList = orderList;
        this.context = context;
    }

    public StoreOrderChuKuAdapter(List<OrderChuKuBean> orderList, Context context, int tag) {
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
            convertView = mInflater.inflate(R.layout.store_order_chuku_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        OrderChuKuBean order = orderList.get(position);
        myHolder.order_item_id.setText(String.valueOf(order.getOrder_sn()));
//        myHolder.order_item_status.setText(orderList.get(position).getStatus());
        myHolder.order_item_name.setText(order.getGoods_name());
        myHolder.order_item_amount.setText(order.getOrder_amount());
        long paymentTime = order.getPayment_time();
        String time = StringUtils.refFormatNowDate(paymentTime * 1000);
        myHolder.order_item_date.setText("下单日期："+time);
        //已发货列表
        if (tag == 2){
            myHolder.order_item_iv.setVisibility(View.GONE);
            myHolder.order_item_date_out.setVisibility(View.VISIBLE);
            myHolder.order_item_company.setVisibility(View.VISIBLE);
            myHolder.order_item_numbers.setVisibility(View.VISIBLE);
            myHolder.order_item_status.setText("已发货");
            myHolder.order_item_date.setText("下单日期："+orderList.get(position).getCreate_time());
            myHolder.order_item_date_out.setText("发货日期："+orderList.get(position).getShipping_time());
            myHolder.order_item_company.setText(orderList.get(position).getExpress_name());
            myHolder.order_item_numbers.setText(orderList.get(position).getShipping_code());
        }
        return convertView;
    }

    public static class ViewHolder {
        public final TextView order_item_id;
        public final TextView order_item_status;
        public final TextView order_item_name;
        public final TextView order_item_amount;
        public final TextView order_item_date;
        public final TextView order_item_date_out;
        public final TextView order_item_company;
        public final TextView order_item_numbers;
        public final ImageView order_item_iv;

        public ViewHolder(View root) {
            this.order_item_id = (TextView) root.findViewById(R.id.order_item_id);
            this.order_item_status = (TextView) root.findViewById(R.id.order_item_status);
            this.order_item_name = (TextView) root.findViewById(R.id.order_item_name);
            this.order_item_amount = (TextView) root.findViewById(R.id.order_item_amount);
            this.order_item_date = (TextView) root.findViewById(R.id.order_item_date);
            this.order_item_iv = (ImageView) root.findViewById(R.id.order_item_iv);

            //已发货
            this.order_item_date_out = (TextView) root.findViewById(R.id.order_item_date_out);
            this.order_item_company = (TextView) root.findViewById(R.id.order_item_company);
            this.order_item_numbers = (TextView) root.findViewById(R.id.order_item_numbers);
        }
    }
}
