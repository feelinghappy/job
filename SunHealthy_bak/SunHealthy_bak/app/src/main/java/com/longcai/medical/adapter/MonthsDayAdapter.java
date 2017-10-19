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

public class MonthsDayAdapter extends RecyclerView.Adapter<MonthsDayAdapter.ViewHolder> {

    private Context mContext;
    private List<String> data;
    private List<String> data2;

    public MonthsDayAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.months_day_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.monthsDayTx.setText(data.get(position));
        holder.monthsDayTx.setSelected(Boolean.parseBoolean(data2.get(position)));
        holder.itemView.setSelected(Boolean.parseBoolean(data2.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.OnClick("" + position);
            }
        });
    }

    public void setData(List<String> hc) {
        this.data = hc;
    }

    public void setData2(List<String> hc) {
        this.data2 = hc;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void OnClick(String id);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView monthsDayTx;

        public ViewHolder(View itemView) {
            super(itemView);
            monthsDayTx = (TextView) itemView.findViewById(R.id.months_day_tx);
        }
    }
}
