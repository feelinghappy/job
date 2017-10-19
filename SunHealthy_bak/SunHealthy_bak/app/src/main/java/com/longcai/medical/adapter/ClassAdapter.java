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
 * Created by LC-XC on 2016/11/4.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {

    private Context mContext;
    private List<String> data;

    public ClassAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tx1.setText(data.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.OnClick(position + "", data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public interface ItemClickListener {
        void OnClick(String id, String tx);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tx1;

        public ViewHolder(View itemView) {
            super(itemView);
            tx1 = (TextView) itemView.findViewById(R.id.tx1);
        }
    }
}
