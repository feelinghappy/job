package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.longcai.medical.R;
import com.longcai.medical.ui.view.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LC-XC on 2016/9/5.
 */
public  class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {
    private final Context mContext;
    private final List<T> mDataList;
    private int type;
    private View view;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = LayoutInflater.from(mContext).inflate(R.layout.tag_item, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        T t = mDataList.get(position);

        if (t instanceof String) {
            textView.setText((String) t);
        }
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return false;
        }
        return false;
    }

}
