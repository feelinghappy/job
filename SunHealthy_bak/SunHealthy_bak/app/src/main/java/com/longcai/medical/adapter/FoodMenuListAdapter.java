package com.longcai.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.BuilderFood;
import com.longcai.medical.ui.activity.WebActivity;

import java.util.List;

/**
 * Created by LC-XC on 2017/4/1.
 */

public class FoodMenuListAdapter extends RecyclerView.Adapter<FoodMenuListAdapter.ViewHolder> {

    private Context context;

    public FoodMenuListAdapter(Context context) {
        this.context = context;
    }

    private List<BuilderFood.DataBean> dataBeen;

    public void setDataBeen(List<BuilderFood.DataBean> dataBeen) {
        this.dataBeen = dataBeen;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_menu_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (dataBeen != null && dataBeen.size() > 0) {
            holder.img.setImageURI(dataBeen.get(position).cover);
            holder.tx.setText(dataBeen.get(position).name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(dataBeen.get(position).url)) {
                        context.startActivity(new Intent(context, WebActivity.class).
                                putExtra("title", "食谱详情").putExtra("url",
                                dataBeen.get(position).url));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataBeen != null && dataBeen.size() > 0) {
            return dataBeen.size();
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
}
