package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.MineCollectListBean;
import com.longcai.medical.bean.NewsList;
import com.longcai.medical.ui.view.FlowTagLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Created by LC-XC on 2016/11/17.
 */

public class ZiXunAdapter extends RecyclerView.Adapter<ZiXunAdapter.ViewHolder> {
    private Context context;
    private List<NewsList.DataInfo> list;
    private List<MineCollectListBean.DataBean> data;
    private TagAdapter<String> tagAdapter;

    public ZiXunAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NewsList.DataInfo> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void setData2(List<MineCollectListBean.DataBean> data2) {
        this.data = data2;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zixun_item, parent, false);
//        MyApplication.ScaleScreenHelper.loadView((ViewGroup) view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (list != null && list.size() > 0) {
            holder.tx1.setText(list.get(position).title);
            holder.tx2.setText(list.get(position).posttime);
            holder.tx4.setText(list.get(position).hits);
            holder.image_icon.setImageURI(list.get(position).cover);
            holder.tv_tag.setText(list.get(position).typeName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnClick(position, list.get(position).url,
                            list.get(position).id);
                }
            });
            tagAdapter = new TagAdapter<>(context);
            if (list.get(position).typeName.contains(",")) {
                holder.flowTagLayout.setVisibility(View.VISIBLE);
                holder.flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                holder.flowTagLayout.setAdapter(tagAdapter);
                List<String> result = Arrays.asList(list.get(position).typeName.split(","));
                tagAdapter.onlyAddAll(result);
            } else {
                holder.flowTagLayout.setVisibility(View.GONE);
            }

        } else if (data != null && data.size() > 0) {
            holder.tx1.setText(data.get(position).getTitle());
            holder.tx2.setText(data.get(position).getPosttime());
            holder.tx4.setText(data.get(position).getHits());
            holder.image_icon.setImageURI(data.get(position).getCover());
            holder.tv_tag.setText(data.get(position).getDict());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnClick(position, data.get(position).getUrl(),
                            data.get(position).getId());
                }
            });
//            tagAdapter = new TagAdapter<>(context);
//            if (data.get(position).getTypeName().contains(",")) {
//                holder.flowTagLayout.setVisibility(View.VISIBLE);
//                holder.flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
//                holder.flowTagLayout.setAdapter(tagAdapter);
//                List<String> result = Arrays.asList(list.get(position).getTypeName().split(","));
//                tagAdapter.onlyAddAll(result);
//            } else {
//                holder.flowTagLayout.setVisibility(View.GONE);
//            }
        }


    }

    public interface ItemClickListener {
        void OnClick(int position, String url, String id);
    }

    private ZiXunAdapter.ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ZiXunAdapter.ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tx1;
        private TextView tx2;
        private TextView tx3;
        private SimpleDraweeView image_icon;
        private FlowTagLayout flowtaglayout;
        private TextView tx4;
        private TextView tv_tag;
        private FlowTagLayout flowTagLayout;
        // private RelativeLayout zixunlayout;

        public ViewHolder(View itemView) {
            super(itemView);
            tx1 = (TextView) itemView.findViewById(R.id.tx1);
            tx2 = (TextView) itemView.findViewById(R.id.tx2);
            tx3 = (TextView) itemView.findViewById(R.id.tx3);
            tx4 = (TextView) itemView.findViewById(R.id.tx4);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
            image_icon = (SimpleDraweeView) itemView.findViewById(R.id.image_icon);
            flowTagLayout = (FlowTagLayout) itemView.findViewById(R.id.flow_tag);
            //  flowtaglayout = (FlowTagLayout) itemView.findViewById(R.id.flowtaglayout);
            //   zixunlayout= (RelativeLayout) itemView.findViewById(zixunlayout);
        }
    }
}
