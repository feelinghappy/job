package com.longcai.medical.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.AddrGetList;
import com.longcai.medical.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/1.
 */

public class AddrManageAdapter extends BaseAdapter {
    private List<AddrGetList> mList;
    private LayoutInflater mInflater;
    private Activity mContext;
    private final int color;
    private final int colorBlack;
    private int temp = -1;
    private int is_default;
    // 用来控制CheckBox的选中状况
    private Map<Integer, Boolean> isSelected;
    //用来存放选中的对象
    private List beSelectedData = new ArrayList();

    //供adapter获取选中数据的方法
    public List getBeSelectedData() {
        return beSelectedData;
    }

    public AddrManageAdapter(List<AddrGetList> mList, Activity mContext) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(mContext);
        color = mContext.getResources().getColor(R.color.blue);
        colorBlack = mContext.getResources().getColor(R.color.black);
        isSelected = new HashMap<Integer, Boolean>();

        for (int i = 0; i < mList.size(); i++) {
            isSelected.put(i, false);
        }
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

    public void update() {
        notifyDataSetChanged();
    }

    ViewHolder myHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int index = position;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.address_manage_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
            myHolder.checkBox.setTag(index);
        } else {
            myHolder = (ViewHolder) convertView.getTag();
        }

        myHolder.name.setText(mList.get(position).getTrue_name());
        myHolder.tel.setText(StringUtils.hideTelMid(mList.get(position).getMob_phone()));
        myHolder.detail.setText(mList.get(position).getArea_info()+mList.get(position).getAddress());
        final int address_id = mList.get(position).getAddress_id();
        is_default = mList.get(position).getIs_default();

        //给Item布局设置点击事件，这里并不操作CheckBox视图，而是操作CheckBox对应的Map容器
        myHolder.checkBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取map中对应位置的布尔值，取其相反值（因为点击一次我们要改变其状态）
                boolean isSelect = !isSelected.get(position);
                // 先将所有的置为FALSE（通过遍历HashMap将所有CheckBox对应的布尔值都设为false）
                for (Integer p : isSelected.keySet()) {
                    isSelected.put(p, false);
                }
                // 再将当前选择CB的实际状态
                isSelected.put(position, isSelect);
                //设置所有item is_default为0
                for (int i = 0; i < mList.size(); i++) {
                    mList.get(i).setIs_default(0);
                }
                mList.get(position).setIs_default(1);
                notifyDataSetChanged();

                beSelectedData.clear();
                if (isSelect) beSelectedData.add(position);
                //获取选中的position
                iIGetDefaultAddress.getAddress(address_id);
            }
        });
        //这里才是设置CheckBox的选中状态，根据上面处理好的Map容器中的数据显示
        myHolder.checkBox.setChecked(isSelected.get(position));
        if (is_default == 1) {//1默认
            myHolder.default_status.setText("默认地址");
            myHolder.default_status.setTextColor(color);
            myHolder.checkBox.setChecked(true);
        } else {
            myHolder.default_status.setText("设为默认");
            myHolder.default_status.setTextColor(colorBlack);
            myHolder.checkBox.setChecked(false);
        }

        //编辑地址
        myHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iEditAddress != null) {
                    iEditAddress.editAddress(position);
                }
            }
        });
        //删除地址
        myHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iDeleteAddress != null) {
                    iDeleteAddress.deleteAddress(position, address_id);
                }
            }
        });
        /*//整栏点击
        myHolder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iEditAddress != null) {
                    iEditAddress.editAddress(position);
                }
            }
        });*/
        return convertView;
    }


    public static class ViewHolder {
        public final TextView name;
        public final TextView tel;
        public final TextView detail;
        public final TextView default_status;
        public final LinearLayout delete;
        public final LinearLayout edit;
        public final CheckBox checkBox;
        public final LinearLayout checkBoxLayout;
        public final RelativeLayout layout1;

        public ViewHolder(View root) {
            this.name = (TextView) root.findViewById(R.id.address_manage_name);
            this.tel = (TextView) root.findViewById(R.id.address_manage_tel);
            this.detail = (TextView) root.findViewById(R.id.address_manage_detail);
            this.default_status = (TextView) root.findViewById(R.id.address_manage_default);
            this.delete = (LinearLayout) root.findViewById(R.id.address_manage_delete);
            this.edit = (LinearLayout) root.findViewById(R.id.address_manage_edit);
            this.checkBox = (CheckBox) root.findViewById(R.id.address_manage_cb);
            this.checkBoxLayout = (LinearLayout) root.findViewById(R.id.checkbox_layou);
            this.layout1 = (RelativeLayout) root.findViewById(R.id.layout1);
        }
    }

    public interface IGetDefaultAddress {
        void getAddress(int address_id);
    }

    public interface IEditAddress {
        void editAddress(int position);
    }

    public interface IDeleteAddress {
        void deleteAddress(int positon, int address_id);
    }

    private IGetDefaultAddress iIGetDefaultAddress;
    private IEditAddress iEditAddress;
    private IDeleteAddress iDeleteAddress;

    public void setOnGetDefaultAddress(IGetDefaultAddress iIGetDefaultAddress) {
        this.iIGetDefaultAddress = iIGetDefaultAddress;
    }

    public void setOnEditAddress(IEditAddress iEditAddress) {
        this.iEditAddress = iEditAddress;
    }

    public void setOnDeleteAddress(IDeleteAddress iDeleteAddress) {
        this.iDeleteAddress = iDeleteAddress;
    }
}
