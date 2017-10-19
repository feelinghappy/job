package com.longcai.medical.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.FoodMaterial;

import java.util.List;

/**
 * Created by LC-XC on 2017/4/12.
 */

public class FoodListMenuAdapter extends RecyclerView.Adapter<FoodListMenuAdapter.ViewHolder> {

    private Context mContext;
    private List<FoodMaterial.Recipe> data2;
    public FoodListMenuAdapter(Context context) {
        this.mContext = context;
    }
    public void setData2(List<FoodMaterial.Recipe> data2) {
        this.data2 = data2;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_menu_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (data2 != null && data2.size() > 0) {
            if (!TextUtils.isEmpty(data2.get(position).cover)) {
                holder.img.setImageURI(Uri.parse(data2.get(position).cover));
            }
            if (!TextUtils.isEmpty(data2.get(position).name)) {
                holder.tx.setText(data2.get(position).name);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(data2.get(position).url)) {
                        if (!TextUtils.isEmpty(data2.get(position).id)) {
                            mOnItemClickLitener.OnClick(data2.get(position).id,
                                    data2.get(position).url);
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data2 != null && data2.size() > 0) {
            return data2.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView img;
        private TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.food_menu_list_item_img);
            tx = (TextView) itemView.findViewById(R.id.food_menu_list_item_tx);
        }
    }

    public interface ItemClickListener {
        void OnClick(String id, String url);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
