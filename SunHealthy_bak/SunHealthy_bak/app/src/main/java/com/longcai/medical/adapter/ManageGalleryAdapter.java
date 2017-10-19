package com.longcai.medical.adapter;

/**
 * Created by Administrator on 2017/7/28.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.utils.GlideRoundTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

/*
    * recyclerview适配器
    * */
public class ManageGalleryAdapter extends RecyclerView.Adapter<ManageGalleryAdapter.ViewHolder> {
    /**
     * * ItemClick的回调接口
     * * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(ImageView view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context context;
    private int currentIndex = 0;
    private LinearLayoutManager linearLayoutManager;

    public ManageGalleryAdapter(Context context, List<String> datats, int position, LinearLayoutManager linearLayoutManager) {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        this.context = context;
        this.linearLayoutManager = linearLayoutManager;
        if (position != -1) {
            currentIndex = position;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
            AutoUtils.autoSize(arg0);
        }
        ImageView mImg;
    }

    public int getItemCount() {
        int size = mDatas.size();
        return size;
    }

    /**
     * 创建ViewHolder
     */
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.activity_index_gallery_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.mImg = (ImageView) view.findViewById(R.id.id_index_gallery_item_image);
        return viewHolder;
    }

    /**
     * * 设置值
     */
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //如果设置了回调，则设置点击事件
        viewHolder.mImg.setScaleType(ImageView.ScaleType.FIT_XY);

        if (i == 0) {
            viewHolder.mImg.setImageResource(R.mipmap.addphoto2x);
//                viewHolder.mImg.setImageResource(R.mipmap.family_list1);
        } else if (i == 1) {
            Glide.with(context).load(R.mipmap.family_list1).transform(new GlideRoundTransform(context)).into(viewHolder.mImg);
        } else if (i == 2) {
            Glide.with(context).load(R.mipmap.family_list2).transform(new GlideRoundTransform(context)).into(viewHolder.mImg);
        } else if (i == 3) {
            Glide.with(context).load(R.mipmap.family_list3).transform(new GlideRoundTransform(context)).into(viewHolder.mImg);
        }else if (i > 3 && i < mDatas.size() - 1) {
            if (mDatas.get(i).equals("1") || mDatas.get(i).equals("2") || mDatas.get(i).equals("3")) {
            } else {
                Glide.with(context).load(mDatas.get(i)).transform(new GlideRoundTransform(context)).placeholder(R.mipmap.zw01).into(viewHolder.mImg);
            }
        }

        if (currentIndex == i && currentIndex != mDatas.size() - 1) {
            viewHolder.mImg.setScaleX(1.2f);
            viewHolder.mImg.setScaleY(1.2f);
        } else if (currentIndex == mDatas.size() - 1) {

        } else {
            viewHolder.mImg.setScaleX(1f);
            viewHolder.mImg.setScaleY(1f);
        }
        viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickLitener != null) {
                    currentIndex = i;
                    notifyDataSetChanged();
                    mOnItemClickLitener.onItemClick(viewHolder.mImg, i);
                }
            }
        });
    }

    public void setNotify() {
        notifyDataSetChanged();
    }
}

