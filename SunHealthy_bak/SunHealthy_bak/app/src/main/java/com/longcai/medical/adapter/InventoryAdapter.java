package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/22.
 * 库存管理
 */

public class InventoryAdapter extends BaseAdapter {
    private List<String> mList;
//    private List<ProductGoodsResult> mList;
    private Context context;
    private LayoutInflater mInflater;
    private int type = -1;

    public InventoryAdapter(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public InventoryAdapter(List<String> mList, Context context, int type) {
        this.mList = mList;
        this.context = context;
        this.type = type;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.inventory_list_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        if (type == 2){//库存
            myHolder.inventoryItemLayout.setVisibility(View.GONE);
            myHolder.orderItemNum.setVisibility(View.GONE);
            myHolder.orderItemAmount.setText("剩余库存：100");
            myHolder.orderItemIv.setImageResource(R.mipmap.mine_jiantou_img);
        }else if (type == 3){//调货
            myHolder.orderItemAmount.setText("总计：￥100");
            myHolder.orderItemNum.setText("数量总计：100");
            myHolder.orderItemIv.setImageResource(R.mipmap.mine_jiantou_img);
        }
        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.inventory_item_iv1)
        ImageView inventoryItemIv1;
        @BindView(R.id.inventory_item_num)
        TextView inventoryItemNum;
        @BindView(R.id.inventory_item_status)
        TextView inventoryItemStatus;
        @BindView(R.id.inventory_item_layout)
        RelativeLayout inventoryItemLayout;
        @BindView(R.id.inventory_item_product)
        ImageView inventoryItemProduct;
        @BindView(R.id.inventory_item_name)
        TextView inventoryItemName;
        @BindView(R.id.order_item_amount)
        TextView orderItemAmount;
        @BindView(R.id.order_item_num)
        TextView orderItemNum;
        @BindView(R.id.order_item_iv)
        ImageView orderItemIv;
        @BindView(R.id.inventory_item_layout2)
        RelativeLayout inventoryItemLayout2;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
