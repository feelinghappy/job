package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.KdniaoTraceResult;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC-XC on 2016/11/4.
 */

public class GoodsInfoAdapter extends BaseAdapter {

    private Context mContext;
    private List<KdniaoTraceResult> data = new ArrayList<>();

    public GoodsInfoAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<KdniaoTraceResult> data) {
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.listview_item_goodsinfo, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        AutoUtils.autoSize(convertView);
        if (i == 0) {
            vh.cbSelected.setChecked(true);
            vh.tvInfo.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            vh.cbSelected.setChecked(false);
        }
        if (data.size() > 0){
            KdniaoTraceResult traceResult = data.get(i);
            vh.tvInfo.setText(traceResult.getAcceptStation());
            vh.tvTime.setText(traceResult.getAcceptTime());
        }
        return convertView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbSelected;
        private TextView tvInfo;
        private TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            cbSelected = (CheckBox) itemView.findViewById(R.id.order_item_select);
            tvInfo = (TextView) itemView.findViewById(R.id.sales_txt_order_info);
            tvTime = (TextView) itemView.findViewById(R.id.sales_txt_time);
        }
    }
}
