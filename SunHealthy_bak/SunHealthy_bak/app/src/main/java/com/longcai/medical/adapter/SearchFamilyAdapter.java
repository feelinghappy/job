package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcai.medical.R;
import com.longcai.medical.bean.Search;
import com.longcai.medical.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class SearchFamilyAdapter extends BaseAdapter {
    private List<Search> searchLists;
    private Context context;
    private IApplyFamily iApplyFamily;

    public SearchFamilyAdapter(List<Search> searchLists, Context context) {
        this.searchLists = searchLists;
        this.context = context;
    }

    @Override
    public int getCount() {
        return searchLists.size();
    }

    @Override
    public Search getItem(int position) {
        return searchLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.family_search_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Search searchResult = getItem(position);
        holder.name.setText(searchResult.getFamily_id());
        holder.family.setText(searchResult.getFamily_name());
        holder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iApplyFamily != null) {
                    iApplyFamily.applyFamily(searchResult.getFamily_id());
                }
            }
        });
        Glide.with(context).load(searchResult.getThumb()).transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.head).into(holder.head);
        return convertView;
    }

    public void setiApplyFamily(IApplyFamily iApplyFamily) {
        this.iApplyFamily = iApplyFamily;
    }

    public interface IApplyFamily {
        void applyFamily(String familyId);
    }

    static class ViewHolder {
        public Button apply;
        public TextView name;
        public TextView family;
        public ImageView head;

        public ViewHolder(View convertView) {
            AutoUtils.autoSize(convertView);
            apply = (Button) convertView.findViewById(R.id.family_search_item_apply);
            name = (TextView) convertView.findViewById(R.id.family_search_item_name);
            family = (TextView) convertView.findViewById(R.id.family_search_item_family);
            head = (ImageView) convertView.findViewById(R.id.family_search_item_head);
        }
    }
}
