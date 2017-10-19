package com.longcai.medical.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.ProductGoodsResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */

public class RuKuProductAdapter extends BaseAdapter{
    private Context mContext;
    private List<ProductGoodsResult> data = new ArrayList<>();

    public RuKuProductAdapter(Context mContext, List<ProductGoodsResult> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item_ruku_product, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ProductGoodsResult productBean = data.get(position);
        String goods_commonid = productBean.getGoods_commonid();
        if (goods_commonid.equals("3")) {
            vh.imgRobot1.setImageResource(R.mipmap.xiaohuobao);
        }else {
            vh.imgRobot1.setImageResource(R.mipmap.yishengjun);
            vh.imgRobot.setImageResource(R.mipmap.shangpinruku);
        }
        vh.tvName.setText(productBean.getGoods_name());
        return convertView;
    }
    public static class ViewHolder {

        private ImageView imgRobot1;
        private ImageView imgRobot;
        private TextView tvName;

        public ViewHolder(View itemView) {
            AutoUtils.autoSize(itemView);
            imgRobot1 = (ImageView) itemView.findViewById(R.id.order_item_iv1);
            imgRobot = (ImageView) itemView.findViewById(R.id.order_item_iv2);
            tvName = (TextView) itemView.findViewById(R.id.order_item_name2);
        }
    }
}
