package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.WatchManagment;

import java.util.List;

/**
 * 手表Adapter 类
 */
public class PersonWatchAdapter extends BaseAdapter {
    private List<WatchManagment> mList;
    private LayoutInflater mInflater;
    private Context context;

    public PersonWatchAdapter(Context context, List<WatchManagment> mList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.person_device_list_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        }
        myHolder = (ViewHolder) convertView.getTag();
        myHolder.iv_device_item.setImageResource(R.mipmap.icon_bracelet);
        WatchManagment watch = mList.get(position);
        String imei = watch.getWatch_imei();
        if (null != imei) {
            myHolder.name_device_item.setText(imei);
        } else {
            myHolder.name_device_item.setText("");
        }
        myHolder.unbind_device_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemUnbindListener.onUnbindClick(position);
            }
        });
        return convertView;
    }

    /**
     * ViewHolder 通过构造方法中 实现具体view的绑定的方式 创建一个自实现绑定View的ViewHolder
     * Created by bailiangjin on 16/7/5.
     */
    public static class ViewHolder {
        public final View root;
        public final ImageView iv_device_item;
        public final TextView name_device_item;
        public final ImageView unbind_device_item;

        public ViewHolder(View root) {
            this.root = root;
            this.iv_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_iv);
            this.name_device_item = (TextView) this.root.findViewById(R.id.person_device_list_name);
            this.unbind_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_unbind);
        }
    }

    /**
     * 解绑按钮的监听接口
     */
    public interface onItemUnbindListener {
        void onUnbindClick(int i);
    }

    private onItemUnbindListener mOnItemUnbindListener;

    public void setOnItemDeleteClickListener(onItemUnbindListener mOnItemUnbindListener) {
        this.mOnItemUnbindListener = mOnItemUnbindListener;
    }
}
