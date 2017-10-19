package com.longcai.medical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27.
 */

public class MineSaleGridAdapter extends BaseAdapter {
    /*private Integer imgs[] = {R.mipmap.xiadan,R.mipmap.yejiguanli,R.mipmap.yonghuguanli,R.mipmap.chakanchanpin,R.mipmap.cangkuguanli};
    private String names[] = {"下单","业绩管理","用户管理","查看产品","仓库管理",};*/
    private List<Integer> imgList;
    private List<String> nameList ;
    private LayoutInflater mInflater;
    private Context context;
    private String memberRole;

    public MineSaleGridAdapter(Context context,List<Integer> imgList,List<String> nameList){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.nameList = nameList;
        this.imgList = imgList;
       /* for (int i = 0; i < imgs.length; i++) {
            imgList.add(imgs[i]);
            nameList.add(names[i]);
        }
        if (!MyApplication.myPreferences.readRole().equals("-1")) {
            // 会员角色 1.销售员 2.库管员 3.销售员和库管员
            memberRole = MyApplication.myPreferences.readRole();
            switch (memberRole){//销售员 不能查看仓库
                case "0":
                    imgList.remove(imgList.size()-1);
                    nameList.remove(nameList.size()-1);
                    LogUtils.d("Jihe 的长度== "+imgList.size());
                    break;
            }
        }*/
    }
    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder myHolder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.mine_sales_item, null);
            myHolder = new ViewHolder(convertView);
            convertView.setTag(myHolder);
        }else {
            myHolder = (ViewHolder) convertView.getTag();
        }
        myHolder.mine_sales_item_iv.setImageResource(imgList.get(position));
        myHolder.mine_sales_item_tv.setText(nameList.get(position));
        return convertView;
    }
    public static class ViewHolder {
        public final ImageView mine_sales_item_iv;
        public final TextView mine_sales_item_tv;

        public ViewHolder(View view) {
            this.mine_sales_item_iv = (ImageView) view.findViewById(R.id.mine_sales_item_iv);
            this.mine_sales_item_tv = (TextView) view.findViewById(R.id.mine_sales_item_tv);
        }
    }
}
