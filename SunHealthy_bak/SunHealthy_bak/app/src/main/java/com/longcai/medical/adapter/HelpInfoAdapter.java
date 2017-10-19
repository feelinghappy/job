package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.HelpInfoResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by LC-XC on 2016/11/4.
 */

public class HelpInfoAdapter extends RecyclerView.Adapter<HelpInfoAdapter.ViewHolder> {

    private Context mContext;
    private List<HelpInfoResult> data;

    public HelpInfoAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<HelpInfoResult> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_helpinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickLitener) {
                    mOnItemClickLitener.OnClick(position, data.get(position));
                }
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
        void OnClick(int position, HelpInfoResult result);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_feedback_name);
        }
    }
}
