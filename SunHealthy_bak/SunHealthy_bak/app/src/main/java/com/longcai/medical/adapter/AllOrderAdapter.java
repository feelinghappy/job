package com.longcai.medical.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.OrderList;
import com.longcai.medical.utils.StringUtils;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by LC-XC on 2016/11/4.
 */

public class AllOrderAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderList.OrderInfoBean> data;
    private String orderState;

    public AllOrderAdapter(Context context,List<OrderList.OrderInfoBean> data) {
        this.mContext = context;
        this.data = data;
    }

    public void setData(List<OrderList.OrderInfoBean> data) {
        if (!data.isEmpty()) {
            this.data.clear();
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item_allorder, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        OrderList.OrderInfoBean order = data.get(i);
        vh.tvSerial.setText("订单编号：" + String.valueOf(order.getOrder_sn()));
        vh.tvName.setText(order.getGoods_name());
        vh.tvMoney.setText("￥" + order.getOrder_amount()+"元");
//        long createTime = order.getCreate_time();
        long paymentTime = order.getPayment_time();
        int order_state = order.getOrder_state();
        vh.tvTime.setText(StringUtils.toTime(String.valueOf(paymentTime)));

        if (order_state == 10){
            orderState = "未付款";
        } else if (order_state == 20){
            orderState = "已付款";
        } else if (order_state == 30){
            orderState = "已发货";
        } else if (order_state == 40){
            orderState = "已收货";
        } else if (order_state == 50) {
            orderState = "已完成";
        }
        vh.tvPayState.setText(orderState);
        return convertView;
    }

    public static class ViewHolder {

        private TextView tvSerial;
        private TextView tvPayState;
        private TextView tvName;
        private TextView tvMoney;
        private TextView tvTime;

        public ViewHolder(View itemView) {
            tvSerial = (TextView) itemView.findViewById(R.id.allorder_txt_serial);
            tvPayState = (TextView) itemView.findViewById(R.id.allorder_txt_paystate);
            tvName = (TextView) itemView.findViewById(R.id.allorder_txt_robotname);
            tvMoney = (TextView) itemView.findViewById(R.id.allorder_txt_money);
            tvTime = (TextView) itemView.findViewById(R.id.allorder_txt_time);
        }
    }
}
