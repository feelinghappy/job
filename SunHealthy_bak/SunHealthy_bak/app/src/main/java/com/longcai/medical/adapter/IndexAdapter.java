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
import com.longcai.medical.bean.ArticleListResult;
import com.longcai.medical.utils.GlideRoundTransform;
import com.longcai.medical.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class IndexAdapter extends BaseAdapter {
    private List<ArticleListResult> articleList;
    private LayoutInflater mInflater;
    private Context context;

    public IndexAdapter(List<ArticleListResult> articleList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (null != articleList) {
            return articleList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return articleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.article_list_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        LogUtils.d("position", position + "");
        ArticleListResult article = articleList.get(position);
        myHolder.iv_article_item.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(article.getThumb()).transform(new GlideRoundTransform(context))
                    .placeholder(R.mipmap.test_zhanweitu).into(myHolder.iv_article_item);
        myHolder.title_article_item.setText(article.getTitle());
        myHolder.num_article_item.setText(article.getView_nums() + "");
        return convertView;
    }

    public static class ViewHolder {
        public final ImageView iv_article_item;
        public final TextView title_article_item;
        public final TextView num_article_item;

        public ViewHolder(View root) {
            this.iv_article_item = (ImageView) root.findViewById(R.id.article_list_item_iv);
            this.title_article_item = (TextView) root.findViewById(R.id.article_list_item_title);
            this.num_article_item = (TextView) root.findViewById(R.id.article_list_item_num);
        }
    }
}
