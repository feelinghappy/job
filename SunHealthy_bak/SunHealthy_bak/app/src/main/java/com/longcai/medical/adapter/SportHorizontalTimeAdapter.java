package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcai.medical.R;

import java.util.List;

/**
 * Created by LC-XC on 2016/11/10.
 */

public class SportHorizontalTimeAdapter extends RecyclerView.Adapter<SportHorizontalTimeAdapter.ViewHolder> {

    private Context mContext;
    private List<String> data;
    private List<String> data2;

    public SportHorizontalTimeAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sport_info_tiem_view, parent, false);
        // 设置Item的宽度
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tx.setText(data.get(position));
        holder.tx.setSelected(Boolean.parseBoolean(data2.get(position)));
        holder.itemView.setSelected(Boolean.parseBoolean(data2.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.OnClick((position) + "", data.get(position));
            }
        });
    }

    public void setData(List<String> hc) {
        this.data = hc;
        notifyDataSetChanged();
    }

    public void setData2(List<String> hc) {
        this.data2 = hc;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }


    public interface ItemClickListener {
        void OnClick(String id, String string);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tx;

        public ViewHolder(View itemView) {
            super(itemView);
            tx = (TextView) itemView.findViewById(R.id.tx);
        }
    }
}
