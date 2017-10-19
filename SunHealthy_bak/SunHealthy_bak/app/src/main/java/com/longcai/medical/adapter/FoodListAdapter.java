package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.FoodList;

import java.util.List;

/**
 * Created by LC-XC on 2017/3/30.
 */

public class FoodListAdapter extends BaseAdapter {

    private Context mContext;
    private List<FoodList> mList;
    private LayoutInflater inf;
    //子view是否充满了手机屏幕
    private boolean isCompleteFill = false;
    private String thumb;

    public FoodListAdapter(Context mContext, List<FoodList> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inf = LayoutInflater.from(mContext);

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.food_list_item_view, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.food_tx.setText(mList.get(position).getTitle());
        String thumb = mList.get(position).getThumb();
        final String url = mList.get(position).getUrl();
        if (thumb != null) {
            Glide.with(mContext).load(thumb).placeholder(R.mipmap.zw01).into(holder.food_img);
        }
        final View finalConvertView = convertView;
        holder.food_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecyclerViewItemListener.onItemClickListener(finalConvertView, position, url);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView food_img;
        TextView food_tx;

        public ViewHolder(View itemView) {
            food_img = (ImageView) itemView.findViewById(R.id.food_list_item_view_img);
            food_tx = (TextView) itemView.findViewById(R.id.food_list_item_view_tx);
        }
    }

    public interface OnGridViewItemListener {
        void onItemClickListener(View view, int position, String url);

    }

    private OnGridViewItemListener mOnRecyclerViewItemListener;

    public void setOnRecyclerViewItemListener(OnGridViewItemListener listener) {
        mOnRecyclerViewItemListener = listener;
    }
}
