package com.longcai.medical.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.ProductGoodsSpecResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC-XC on 2016/11/4.
 */

public class ProductParamAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductGoodsSpecResult> data = new ArrayList<>();

    public ProductParamAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ProductGoodsSpecResult> data) {
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
    public ProductGoodsSpecResult getItem(int i) {
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
            convertView = View.inflate(mContext, R.layout.listview_item_product_param, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        ProductGoodsSpecResult goodsSpec = data.get(i);
        vh.tvTitle.setText(goodsSpec.getTitle() + "：");
        vh.tvContent.setText(goodsSpec.getContent());
        return convertView;
    }

    public static class ViewHolder {

        private TextView tvTitle;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            tvTitle = (TextView) itemView.findViewById(R.id.param_title);
            tvContent = (TextView) itemView.findViewById(R.id.param_content);
        }
    }

}
