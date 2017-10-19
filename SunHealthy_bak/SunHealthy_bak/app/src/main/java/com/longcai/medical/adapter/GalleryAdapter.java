package com.longcai.medical.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.SelectFoodBean;

import java.util.List;

/**
 * Created by LC-XC on 2017/5/15.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    private List<SelectFoodBean> data;

    public void setData(List<SelectFoodBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.foot_bottom_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data != null && data.size() > 0) {
            holder.img.setImageURI(Uri.parse(data.get(position).img));
            holder.tx.setText(data.get(position).name);
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView img;
        private TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (SimpleDraweeView) itemView.findViewById(R.id.food_bottom_view_item_img);
            tx = (TextView) itemView.findViewById(R.id.food_bottom_view_item_tx);
        }
    }
}
