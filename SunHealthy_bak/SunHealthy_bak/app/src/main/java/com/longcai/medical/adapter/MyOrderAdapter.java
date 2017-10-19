package com.longcai.medical.adapter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longcai.medical.R;
import com.longcai.medical.bean.MyOrderResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by LC-XC on 2016/11/4.
 */

public class MyOrderAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyOrderResult> data ;
    private IQueryCallBack iQueryCallBack;

    public MyOrderAdapter(Context context, List<MyOrderResult> data) {
        this.mContext = context;
        this.data = data;
    }

    public void setiQueryCallBack(IQueryCallBack iQueryCallBack) {
        this.iQueryCallBack = iQueryCallBack;
    }

    public void setData(List<MyOrderResult> data) {
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
    public MyOrderResult getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item_myorder, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        MyOrderResult orderResult = data.get(i);
        vh.tvSerial.setText(String.format(mContext.getString(R.string.sales_myorder_serial), orderResult.getOrder_sn()));
        vh.tvName.setText(orderResult.getGoods_info().getGoods_name());
//        if (orderResult.getGoods_info().getGc_id() == 1) {
//            vh.tvMoney.setText(String.format(mContext.getString(R.string.sales_margin), orderResult.getOrder_amount()));
//        } else {
            vh.tvMoney.setText(String.format(mContext.getString(R.string.sales_money_sum), orderResult.getOrder_amount()));
//        }
        String goodsImage = orderResult.getGoods_info().getGoods_image();
        if (!TextUtils.isEmpty(goodsImage)) {
            Glide.with(mContext).load(goodsImage).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().placeholder(R.mipmap.test_zhanweitu).into(vh.imgRobot);
        } else {
            Glide.with(mContext).load(R.mipmap.test_zhanweitu).fitCenter().into(vh.imgRobot);
        }
        final String state = orderResult.getOrder_state();
        if (state.equals("0")) {
            vh.tvPayState.setText("已关闭");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.orange));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.orange));
            }
            vh.btnQuery.setVisibility(View.GONE);
        } else if (state.equals("10")) {
            vh.tvPayState.setText("待支付");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.orange));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.orange));
            }
            vh.btnQuery.setVisibility(View.VISIBLE);
            vh.btnQuery.setText("支付");
        } else if (state.equals("20")) {
            vh.tvPayState.setText("待发货");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.orange));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.orange));
            }
            vh.btnQuery.setVisibility(View.GONE);
        } else if (state.equals("30")) {
            vh.tvPayState.setText("待收货");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.orange));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.orange));
            }
            vh.btnQuery.setVisibility(View.VISIBLE);
            vh.btnQuery.setText("查询物流");
        }
//        else if (state.equals("")) {
//            vh.tvPayState.setText("未激活");
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                vh.tvPayState.setTextColor(mContext.getColor(R.color.theme_blue));
//            } else {
//                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.theme_blue));
//            }
//            vh.btnQuery.setText("去激活");
//        }
        else if (state.equals("40")) {
            vh.tvPayState.setText("已送达");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.theme_blue));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.theme_blue));
            }
            vh.btnQuery.setVisibility(View.GONE);
        } else if (state.equals("50")) {
            vh.tvPayState.setText("已完成");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vh.tvPayState.setTextColor(mContext.getColor(R.color.theme_blue));
            } else {
                vh.tvPayState.setTextColor(mContext.getResources().getColor(R.color.theme_blue));
            }
            vh.btnQuery.setVisibility(View.GONE);
        }
        final String order_sn = orderResult.getOrder_sn();
        vh.btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != iQueryCallBack) {
                    iQueryCallBack.query(state, order_sn);
//                    iQueryCallBack.query(i);
                }
            }
        });
        return convertView;
    }

    public static class ViewHolder {

        private TextView tvSerial;
        private TextView tvPayState;
        private ImageView imgRobot;
        private TextView tvName;
        private TextView tvMoney;
        private TextView tvTradeWay;
        private Button btnQuery;

        public ViewHolder(View itemView) {
            tvSerial = (TextView) itemView.findViewById(R.id.sales_txt_serial);
            tvPayState = (TextView) itemView.findViewById(R.id.sales_txt_paystate);
            imgRobot = (ImageView) itemView.findViewById(R.id.sales_img_robot);
            tvName = (TextView) itemView.findViewById(R.id.sales_txt_robotname);
            tvMoney = (TextView) itemView.findViewById(R.id.sales_txt_money);
            tvTradeWay = (TextView) itemView.findViewById(R.id.sales_txt_tradeway);
            btnQuery = (Button) itemView.findViewById(R.id.sales_btn_paystate);
        }
    }

    public interface IQueryCallBack {
        //        void query(int position);
        void query(String state, String orderSn);
    }
}
