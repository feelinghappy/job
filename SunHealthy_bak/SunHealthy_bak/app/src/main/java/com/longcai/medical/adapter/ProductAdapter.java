package com.longcai.medical.adapter;

import android.content.Context;
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
import com.longcai.medical.bean.ProductGoodsResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class ProductAdapter extends BaseAdapter {

    private Context mContext;
    private List<ProductGoodsResult> data = new ArrayList<>();
    private int mType; // 0:product 1:placeorder
    private ISelectBack iSelectBack;

    public ProductAdapter(Context context, int type) {
        this.mContext = context;
        this.mType = type;
    }

    public void setiSelectBack(ISelectBack iSelectBack) {
        this.iSelectBack = iSelectBack;
    }

    public void setData(List<ProductGoodsResult> data) {
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item_product, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        ProductGoodsResult productBean = data.get(i);
        if (mType == 0) {
            vh.btnSelect.setVisibility(View.GONE);
        } else if (mType == 1) {
            int goods_storage = productBean.getGoods_storage();
            if (goods_storage > 0) {
                vh.btnSelect.setVisibility(View.VISIBLE);
                vh.btnSelect.setText("选择");
                vh.btnSelect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (iSelectBack != null) {
                            iSelectBack.select(i);
                        }
                    }
                });
            } else if (goods_storage <= 0) {
                vh.btnSelect.setVisibility(View.GONE);
            }
        }else if (mType == 2){//入库 暂时显示

        }
        String image = productBean.getGoods_image();
        String name = productBean.getGoods_name();
        String price = productBean.getGoods_price();
        if (null != image) {
            Glide.with(mContext).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().placeholder(R.mipmap.test_zhanweitu).into(vh.imgRobot);
            Glide.with(mContext).load(image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).fitCenter().placeholder(R.mipmap.test_zhanweitu).into(vh.imgRobot);
        } else {
            Glide.with(mContext).load(R.mipmap.test_zhanweitu).asBitmap().fitCenter().into(vh.imgRobot);
        }
        if (!TextUtils.isEmpty(name)) {
            vh.tvName.setText(name);
        }
        if (!TextUtils.isEmpty(price)) {
            vh.tvMoney.setText(price);
        }
        return convertView;
    }

    public static class ViewHolder {

        private ImageView imgRobot;
        private TextView tvName;
        private TextView tvMoney;
        private Button btnSelect;

        public ViewHolder(View itemView) {
            AutoUtils.autoSize(itemView);
            imgRobot = (ImageView) itemView.findViewById(R.id.product_img_bg);
            tvName = (TextView) itemView.findViewById(R.id.product_txt_name);
            tvMoney = (TextView) itemView.findViewById(R.id.product_txt_money);
            btnSelect = (Button) itemView.findViewById(R.id.product_btn_select);
        }
    }

    public interface ISelectBack {
        void select(int position);
    }
}
