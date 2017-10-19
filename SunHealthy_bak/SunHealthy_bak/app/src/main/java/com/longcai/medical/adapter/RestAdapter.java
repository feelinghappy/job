package com.longcai.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.MineRest;
import com.longcai.medical.ui.activity.EditRestActivity;
import com.longcai.medical.utils.DbUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9.
 */
public class RestAdapter extends RecyclerView.Adapter<RestAdapter.ViewHolder> {

    private Context context;
    private int pos;
    private int num;
    List<MineRest> mineRestsBoo;
    List<Boolean> rest_image;
    String type = "0";
    private String id;

    //数据库
    private String DB_NAME = "minerest";
    //搜索的东西
    private String CURRENTDATE = "";

    public RestAdapter(Context context, int num, int pos, List<MineRest> mineRestsBoo, List<Boolean> rest_image) {
        this.context = context;
        this.pos = pos;
        this.num = num;
        this.mineRestsBoo = mineRestsBoo;
        this.rest_image = rest_image;
    }

    public RestAdapter(Context context, String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.adapter_rest, parent, false);
        return new ViewHolder(view);
    }


    public interface ItemClickListener {
        void OnClick(View view, ImageView imageView, int position, TextView text1, TextView text2);
    }

    public interface ItemClickLongListener {
        void OnCLick(View view, int position);
    }

    private ItemClickListener mOnItemClickLitener;

    private ItemClickLongListener mOnItemClickLongListener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setOnItemLongClickLitener(ItemClickLongListener mOnItemClickLongListener) {
        this.mOnItemClickLongListener = mOnItemClickLongListener;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.time1.setText(mineRestsBoo.get(position).getHour());
        holder.time2.setText(mineRestsBoo.get(position).getMinute());
        holder.text2.setText(mineRestsBoo.get(position).getTitle());
        holder.text3.setText(mineRestsBoo.get(position).getContent());
        if (mineRestsBoo.get(position).getTitle().equals("起床")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle1);
        } else if (mineRestsBoo.get(position).getTitle().equals("午休")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle2);
        } else if (mineRestsBoo.get(position).getTitle().equals("晚睡")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle3);
        } else if (mineRestsBoo.get(position).getTitle().equals("吃药")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle4);
        } else if (mineRestsBoo.get(position).getTitle().equals("茶饮")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle5);
        } else if (mineRestsBoo.get(position).getTitle().equals("就餐")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle6);
        } else if (mineRestsBoo.get(position).getTitle().equals("水果")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle7);
        } else if (mineRestsBoo.get(position).getTitle().equals("购物")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle8);
        } else if (mineRestsBoo.get(position).getTitle().equals("锻炼")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle9);
        } else if (mineRestsBoo.get(position).getTitle().equals("出行")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle10);
        } else if (mineRestsBoo.get(position).getTitle().equals("自定义")) {
            holder.imageview2.setImageResource(R.mipmap.resttitle11);
        }

        if (position == 0) {
            holder.view1.setVisibility(View.INVISIBLE);
        } else if (position == (num - 1)) {
            holder.view2.setVisibility(View.GONE);
        }

        if (rest_image.get(position) == true) {
            holder.imageview3.setImageResource(R.mipmap.rest_adapter_image3);
        } else {
            holder.imageview3.setImageResource(R.mipmap.rest_adapter_image4);
        }
        holder.imageview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.OnClick(holder.imageview3, holder.imageview3, pos, holder.time1, holder.time2);
            }
        });
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickLongListener.OnCLick(holder.layout, position);
                return false;
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbUtils.createDb(context, DB_NAME);
                CURRENTDATE = "all";
                mineRestsBoo = DbUtils.getQueryByWhere(MineRest.class, "str", new String[]{CURRENTDATE});
                Log.d("1555","----"+holder.time1.getText().toString()+"---"+holder.time2.getText().toString()+"---"+holder.text2.getText().toString());
                context.startActivity(new Intent(context, EditRestActivity.class).putExtra("type",position)
                        .putExtra("hour",holder.time1.getText().toString())
                        .putExtra("min",holder.time2.getText().toString())
                        .putExtra("text",holder.text2.getText().toString())
                );

//                for (int i=0;i<mineRestsBoo.size();i++){
//                    id=""+position;
//                    Log.d("1555","1----"+id);
//                    Log.d("1555","2----"+position);
//                    Log.d("1555","3----"+i);
//
//                    if (id==null){
//
//                    }else {
//                        if (id.equals(position)){
//
//                        }
//                    }
//
////                    if (mineRestsBoo.get(i).getTime().equals(mineRestsBoo.get(position).getTime())){
////                        id=""+i;
////
////                    }
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (type.equals("-1")) {
            return 0;
        } else {
            return num;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView1;
        private ImageView imageview2;
        private ImageView imageview3;
        private TextView text1;
        private TextView time1;
        private TextView time2;
        private TextView text2;
        private TextView text3;
        private View view1;
        private View view2;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView1 = (ImageView) itemView.findViewById(R.id.rest_adapter_image1);
            imageview2 = (ImageView) itemView.findViewById(R.id.rest_adapter_image2);
            imageview3 = (ImageView) itemView.findViewById(R.id.rest_adapter_image3);
            time1 = (TextView) itemView.findViewById(R.id.rest_adapter_time1);
            time2 = (TextView) itemView.findViewById(R.id.rest_adapter_time3);
            text2 = (TextView) itemView.findViewById(R.id.rest_adapter_text2);
            text3 = (TextView) itemView.findViewById(R.id.rest_adapter_text3);
            view1 = itemView.findViewById(R.id.adapter_view_rest1);
            view2 = itemView.findViewById(R.id.adapter_view_rest2);
            layout = (LinearLayout) itemView.findViewById(R.id.rest_adapter_layout);
        }
    }
}
