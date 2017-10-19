package com.longcai.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.Center;
import com.longcai.medical.bean.MyCenter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.ViewHolder> {

    private Context context;
    private String str = "0";
   private List<Center.ActivisBean> activisBeen;
    private List<MyCenter.ActivisBean> mydata;

    public CenterAdapter(Context context) {
        this.context = context;
    }

    public CenterAdapter(Context context, List<MyCenter.ActivisBean> mydata) {
        this.context = context;
        this.mydata = mydata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.adapter_center, parent, false);
        return new ViewHolder(view);
    }

    public interface ItemClickListener {
        void OnClick(String aid, int position);

        void OnWebClick(String url, String aid, int pstatus);
    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void addData1(List<Center.ActivisBean> activisBeen) {
        this.activisBeen = activisBeen;
        notifyDataSetChanged();
    }

    public void addData2(List<MyCenter.ActivisBean> list) {
        this.mydata = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (activisBeen != null && activisBeen.size() > 0) {
            holder.text1.setText(activisBeen.get(position).title);
            holder.text2.setText("活动时间：" + activisBeen.get(position).time);
            holder.text3.setText("活动地点：" + activisBeen.get(position).addr);
            holder.imageView.setImageURI(activisBeen.get(position).cover);
            if (activisBeen.get(position).status == 1) {
                holder.btn.setText("查看报名");
            } else {
                holder.btn.setText("报名参加");
            }
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activisBeen.get(position).status == 1) {
                        mOnItemClickLitener.OnClick(activisBeen.get(position).id,
                                activisBeen.get(position).status);
                    } else {
                        mOnItemClickLitener.OnClick(activisBeen.get(position).id,
                                activisBeen.get(position).status);
                    }
//                    mOnItemClickLitener.OnWebClick(activisBeen.get(position).getUrl(),
//                            activisBeen.get(position).getId(), activisBeen.get(position).getStatus());
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnWebClick(activisBeen.get(position).url,
                            activisBeen.get(position).id,
                            activisBeen.get(position).status);
                }
            });
        } else if (mydata != null && mydata.size() > 0) {
            holder.btn.setText("查看报名");
            holder.text1.setText(mydata.get(position).title);
            holder.text2.setText("活动时间：" + mydata.get(position).time);
            holder.text3.setText("活动地点：" + mydata.get(position).addr);
            holder.imageView.setImageURI(mydata.get(position).cover);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnClick(mydata.get(position).actvityId, 1);
//                    mOnItemClickLitener.OnWebClick(mydata.get(position).getUrl(),
//                            mydata.get(position).getActvityId(), 1);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnWebClick(mydata.get(position).url,
                            mydata.get(position).actvityId, 1);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (activisBeen != null && activisBeen.size() > 0) {
            return activisBeen.size();
        } else if (mydata != null && mydata.size() > 0) {
            return mydata.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private SimpleDraweeView imageView;
        private TextView text1;
        private TextView text2;
        private TextView text3;
        private Button btn;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.center_iamge);
            text1 = (TextView) itemView.findViewById(R.id.center_text1);
            text2 = (TextView) itemView.findViewById(R.id.center_text2);
            text3 = (TextView) itemView.findViewById(R.id.center_text3);
            btn = (Button) itemView.findViewById(R.id.center_btn);
            layout = (LinearLayout) itemView.findViewById(R.id.adapter_layout);

        }
    }
}
