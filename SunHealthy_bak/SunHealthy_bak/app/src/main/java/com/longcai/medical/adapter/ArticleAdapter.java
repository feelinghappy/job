package com.longcai.medical.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.ArticleListResult;
import com.longcai.medical.utils.GlideRoundTransform;

import java.util.List;

/**
 * 家庭消息Adapter
 */
public class ArticleAdapter extends BaseAdapter {//R.layout.messages_list_item;
    private List<ArticleListResult> mList;
    private LayoutInflater mInflater;
    private Context context;

    public ArticleAdapter(Context context, List<ArticleListResult> mList) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.article_list_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        }
        myHolder = (ViewHolder) convertView.getTag();
        myHolder.iv_article_item.setScaleType(ImageView.ScaleType.FIT_XY);
        String thumb = mList.get(position).getThumb();
        if (!TextUtils.isEmpty(thumb)) {
            Glide.with(context).load(thumb).fitCenter().transform(new GlideRoundTransform(context)).placeholder(R.mipmap.test_zhanweitu).into(myHolder.iv_article_item);
        } else {
            Glide.with(context).load(R.mipmap.test_zhanweitu).transform(new GlideRoundTransform(context)).fitCenter().into(myHolder.iv_article_item);
        }
        myHolder.title_article_item.setText(mList.get(position).getTitle());
        myHolder.num_article_item.setText(mList.get(position).getView_nums()+"");
        return convertView;
    }

    /**
     * ViewHolder1 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     */
    public static class ViewHolder {
        public final View root;
        public final ImageView iv_article_item;
        public final TextView title_article_item;
        public final TextView num_article_item;

        public ViewHolder(View root) {
            this.root = root;
            this.iv_article_item = (ImageView) this.root.findViewById(R.id.article_list_item_iv);
            this.title_article_item = (TextView) this.root.findViewById(R.id.article_list_item_title);
            this.num_article_item = (TextView) this.root.findViewById(R.id.article_list_item_num);
        }
    }
}
