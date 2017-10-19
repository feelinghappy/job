package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.bean.TestTopic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 * 体质测试适配器
 */

public class StaminaListAdapter extends RecyclerView.Adapter<StaminaListAdapter.ViewHolder> {

    private Context context;
    private List<TestTopic.DataBean> data;
    private List<List<Boolean>> booleanlist;
    //增加一个值判断显示的数目
    private int pos = 1;
    private List<Integer> mark;


    public StaminaListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout
                .adapter_stamina, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position == 0) {
            holder.text.setVisibility(View.GONE);
        } else {
            holder.text.setVisibility(View.GONE);
        }
        holder.text1.setText((position + 1) + "." + data.get(position).title);
        holder.text2.setText(data.get(position).answer1);
        holder.text3.setText(data.get(position).answer2);
        holder.text4.setText(data.get(position).answer3);
        holder.text5.setText(data.get(position).answer4);
        holder.text6.setText(data.get(position).answer5);

        holder.image2.setChecked(booleanlist.get(position).get(0));
        holder.image3.setChecked(booleanlist.get(position).get(1));
        holder.image4.setChecked(booleanlist.get(position).get(2));
        holder.image5.setChecked(booleanlist.get(position).get(3));
        holder.image6.setChecked(booleanlist.get(position).get(4));

        if (mOnItemClickLitener != null) {
            holder.layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(0, true);
                    mychange(holder, position);
//                    holder.text2.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.layout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(1, true);
                    mychange(holder, position);
//                    holder.text3.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.layout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(2, true);
                    mychange(holder, position);
//                    holder.text4.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.layout5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(3, true);
                    mychange(holder, position);
//                    holder.text5.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.layout6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(4, true);
                    mychange(holder, position);
//                    holder.text6.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(0, true);
                    mychange(holder, position);
//                    holder.text2.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(1, true);
                    mychange(holder, position);
//                    holder.text3.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.image4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(2, true);
                    mychange(holder, position);
//                    holder.text4.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.image5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(3, true);
                    mychange(holder, position);
//                    holder.text5.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
            holder.image6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myimage(holder, position);
                    booleanlist.get(position).set(4, true);
                    mychange(holder, position);
//                    holder.text6.setTextColor(context.getResources().getColor(R.color.appColor));
                }
            });
        }

    }

    private void myimage(ViewHolder holder, int position) {
        booleanlist.get(position).set(0, false);
        booleanlist.get(position).set(1, false);
        booleanlist.get(position).set(2, false);
        booleanlist.get(position).set(3, false);
        booleanlist.get(position).set(4, false);
    }

    private void mychange(ViewHolder holder, int position) {
        mOnItemClickLitener.OnClick(holder.itemView, position, pos);
        if (position + 1 < pos) {
            markadd(position);
        } else {
            if (pos < data.size()) {
                markadd(position);
                pos = pos + 1;
            } else if (pos == data.size()) {
                markadd(position);
            }
        }

        notifyDataSetChanged();
    }

    private void markadd(int position) {
//        mark=new ArrayList<Integer>();
        if (booleanlist.get(position).get(0) == true) {
            mark.set(position, 1);
        }
        if (booleanlist.get(position).get(1) == true) {
            mark.set(position, 2);
        }
        if (booleanlist.get(position).get(2) == true) {
            mark.set(position, 3);
        }
        if (booleanlist.get(position).get(3) == true) {
            mark.set(position, 4);
        }
        if (booleanlist.get(position).get(4) == true) {
            mark.set(position, 5);
        }
    }

    public void setBoolean(List<List<Boolean>> booleanList) {
        booleanlist = booleanList;
        notifyDataSetChanged();
    }

    public void setData(List<TestTopic.DataBean> list) {
        data = list;
        //创建一个mark
        mark = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            mark.add(0);
        }
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void OnClick(View view, int position, int pos);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public List<Integer> getMark() {
        return mark;
    }


    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return pos;
            //   return data.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout2;
        private LinearLayout layout3;
        private LinearLayout layout4;
        private LinearLayout layout5;
        private LinearLayout layout6;
        private LinearLayout layout;
        private TextView text1;
        private TextView text2;
        private TextView text3;
        private TextView text4;
        private TextView text5;
        private TextView text6;
        private CheckBox image2;
        private CheckBox image3;
        private CheckBox image4;
        private CheckBox image5;
        private CheckBox image6;
        private RelativeLayout text;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout);
            layout2 = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout2);
            layout3 = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout3);
            layout4 = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout4);
            layout5 = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout5);
            layout6 = (LinearLayout) itemView.findViewById(R.id.adapter_stamina_layout6);
            text = (RelativeLayout) itemView.findViewById(R.id.stamina_layout_text);
            text1 = (TextView) itemView.findViewById(R.id.adapter_stamina_text1);
            text2 = (TextView) itemView.findViewById(R.id.adapter_stamina_text2);
            text3 = (TextView) itemView.findViewById(R.id.adapter_stamina_text3);
            text4 = (TextView) itemView.findViewById(R.id.adapter_stamina_text4);
            text5 = (TextView) itemView.findViewById(R.id.adapter_stamina_text5);
            text6 = (TextView) itemView.findViewById(R.id.adapter_stamina_text6);
            image2 = (CheckBox) itemView.findViewById(R.id.adapter_stamina_image2);
            image3 = (CheckBox) itemView.findViewById(R.id.adapter_stamina_image3);
            image4 = (CheckBox) itemView.findViewById(R.id.adapter_stamina_image4);
            image5 = (CheckBox) itemView.findViewById(R.id.adapter_stamina_image5);
            image6 = (CheckBox) itemView.findViewById(R.id.adapter_stamina_image6);
        }
    }
}
