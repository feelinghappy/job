package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.DeviceBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;
/**
 * Created by Administrator on 2017/8/21.
 */

public class DeviceAdapter extends BaseAdapter {
    private List<DeviceBean> mDevices;
    private LayoutInflater mInflater;
    private Context context;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    private IRobotUnbind iRobotUnbind;
    private IWatchUnbind iWatchUnbind;

    public DeviceAdapter(List<DeviceBean> devices, Context context) {
        this.mDevices = devices;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        int id = mDevices.get(position).getId();
        if (id == 1) {
            return TYPE_1;
        } else {
            return TYPE_2;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder1 viewHolder1 = null;
        ViewHolder2 viewHolder2 = null;
        int type = getItemViewType(position);
        if (convertView == null){
            if (type == TYPE_1) {
                convertView = mInflater.inflate(R.layout.person_device_list_item, viewGroup, false);
                AutoUtils.autoSize(convertView);
                viewHolder1 = new ViewHolder1(convertView);
                convertView.setTag(viewHolder1);
            } else if (type == TYPE_2) {
                convertView = mInflater.inflate(R.layout.person_device_list_item, viewGroup, false);
                AutoUtils.autoSize(convertView);
                viewHolder2 = new ViewHolder2(convertView);
                convertView.setTag(viewHolder2);
            }
        } else {
            if (type == TYPE_1) {
                viewHolder1 = (ViewHolder1) convertView.getTag();
            } else if (type == TYPE_2) {
                viewHolder2 = (ViewHolder2) convertView.getTag();
            }
        }
        if (type == TYPE_1) {
            String familyName = mDevices.get(position).getFamily_name();
            if (null != familyName) {
                viewHolder1.name_device_item.setText(familyName);
            } else {
                viewHolder1.name_device_item.setText("");
            }
            viewHolder1.unbind_device_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != iRobotUnbind) {
                        iRobotUnbind.robotUnbind(position);
                    }
                }
            });
        } else if (type == TYPE_2) {
            String imei = mDevices.get(position).getWatch_imei();
            viewHolder2.iv_device_item.setImageResource(R.mipmap.icon_watch);
            if (null != imei) {
                viewHolder2.name_device_item.setText(imei);
            } else {
                viewHolder2.name_device_item.setText("");
            }
            viewHolder2.unbind_device_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != iWatchUnbind) {
                        iWatchUnbind.watchUnbind(position);
                    }
                }
            });
        }

        return convertView;
    }

    public interface IRobotUnbind {
        void robotUnbind(int position);
    }

    public interface IWatchUnbind {
        void watchUnbind(int position);
    }

    public void setiRobotUnbind(IRobotUnbind iRobotUnbind) {
        this.iRobotUnbind = iRobotUnbind;
    }

    public void setiWatchUnbind(IWatchUnbind iWatchUnbind) {
        this.iWatchUnbind = iWatchUnbind;
    }

    public static class ViewHolder1 {
        public final View root;
        public final ImageView iv_device_item;
        public final TextView name_device_item;
        public final ImageView unbind_device_item;

        public ViewHolder1(View root) {
            this.root = root;
            this.iv_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_iv);
            this.name_device_item = (TextView) this.root.findViewById(R.id.person_device_list_name);
            this.unbind_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_unbind);
        }
    }

    public static class ViewHolder2 {
        public final View root;
        public final ImageView iv_device_item;
        public final TextView name_device_item;
        public final ImageView unbind_device_item;

        public ViewHolder2(View root) {
            this.root = root;
            this.iv_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_iv);
            this.name_device_item = (TextView) this.root.findViewById(R.id.person_device_list_name);
            this.unbind_device_item = (ImageView) this.root.findViewById(R.id.person_device_list_unbind);
        }
    }
}
