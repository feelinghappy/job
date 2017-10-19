package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longcai.medical.R;
import com.longcai.medical.bean.GetInfoGoodsResult;

import java.util.List;

/**
 * Created by Administrator on 2017/10/12.
 */

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<GetInfoGoodsResult> mGoodsResults;

    public HomeProductAdapter(Context context, List<GetInfoGoodsResult> goodsResults) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mGoodsResults = goodsResults;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_home_product_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.item = (LinearLayout) view.findViewById(R.id.sales_txt_item);
        viewHolder.img = (ImageView) view.findViewById(R.id.sales_img_robot);
        viewHolder.name = (TextView) view.findViewById(R.id.sales_txt_robotname);
        viewHolder.money = (TextView) view.findViewById(R.id.sales_txt_money);
        viewHolder.storage = (TextView) view.findViewById(R.id.sales_txt_storage);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GetInfoGoodsResult goodsResult = mGoodsResults.get(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnItemClickLitener) {
                    mOnItemClickLitener.onItemClick(goodsResult);
                }
            }
        });//dontAnimate().
        Glide.with(mContext).load(goodsResult.getGoods_image()).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter().placeholder(R.mipmap.test_zhanweitu).into(holder.img);
        holder.name.setText(goodsResult.getGoods_name());
        if (goodsResult.getGoods_storage() == 0) {
            holder.storage.setVisibility(View.VISIBLE);
            holder.money.setVisibility(View.GONE);
        } else if (goodsResult.getGoods_storage() > 0) {
            holder.storage.setVisibility(View.GONE);
            holder.money.setVisibility(View.VISIBLE);
            holder.money.setText("￥" + goodsResult.getGoods_price());
        }
    }

    @Override
    public int getItemCount() {
        return mGoodsResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
        LinearLayout item;
        ImageView img;
        TextView name;
        TextView money;
        TextView storage;
    }

    public interface OnItemClickLitener {
        void onItemClick(GetInfoGoodsResult goodsResult);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
