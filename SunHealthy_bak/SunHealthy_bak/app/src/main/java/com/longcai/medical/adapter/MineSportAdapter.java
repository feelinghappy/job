package com.longcai.medical.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.longcai.medical.R;
import com.longcai.medical.bean.Sport;
import com.longcai.medical.bean.SportList;

import java.util.List;

/**
 * Created by LC-XC on 2016/11/2.
 * 我的运动  运动列表
 */

public class MineSportAdapter extends RecyclerView.Adapter<MineSportAdapter.ViewHolder> {

    private Context mContext;
    private String familyId;

    private List<SportList.DataBean> recommend;
    private List<Sport.DataBean> data;

    public MineSportAdapter(Context context, String familyId) {
        this.mContext = context;
        this.familyId = familyId;
    }


    public void setData(List<SportList.DataBean> hc) {
        this.recommend = hc;
        notifyDataSetChanged();
    }

    public void setData2(List<Sport.DataBean> hc2) {
        this.data = hc2;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.minesport_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.alpha.getBackground().setAlpha(80);
        holder.textView1.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.textView2.setTextColor(mContext.getResources().getColor(R.color.black));
        if (recommend != null && recommend.size() > 0) {
            if (recommend.get(position).type.equals("1")) {
                holder.textView1.setText("晨练");
                holder.minesportLayout.setVisibility(View.VISIBLE);
                Log.d("1555", "进度" + recommend.get(position).jindu + "总" +
                        recommend.get(position).sport_number);
                if (recommend.get(position).jindu < Integer.parseInt(
                        recommend.get(position).sport_number)) {
                    holder.right.setVisibility(View.GONE);
                    holder.sportTx.setText(R.string.sport_fail);
                } else {
                    holder.right.setVisibility(View.VISIBLE);
                    holder.sportTx.setText(R.string.sport_success);
                }
            } else if (recommend.get(position).type.equals("2")) {
                Log.d("1535", "fId:" + familyId.toString());
                if (!familyId.equals("0")) {
                    holder.minesport_layou.setVisibility(View.GONE);
                } else {
                    holder.minesport_layou.setVisibility(View.VISIBLE);
                    holder.textView1.setText("步行");
//                holder.minesportLayout.setVisibility(View.GONE);
                    Log.d("1555", "进度" + recommend.get(position).jindu + "总" +
                            recommend.get(position).sport_number);
                    if (recommend.get(position).jindu< Integer.parseInt(
                            recommend.get(position).sport_number)) {
                        holder.right.setVisibility(View.GONE);
                        holder.sportTx.setText(R.string.sport_fail);
                    } else {
                        holder.right.setVisibility(View.VISIBLE);
                        holder.sportTx.setText(R.string.sport_success);
                    }
                }

            } else if (recommend.get(position).type.equals("3")) {
                holder.textView1.setText("晚练");
                holder.minesportLayout.setVisibility(View.VISIBLE);
                Log.d("1555", "进度" + recommend.get(position).jindu + "总" +
                        recommend.get(position).sport_number);
                if (recommend.get(position).jindu < Integer.parseInt(
                        recommend.get(position).sport_number)) {
                    holder.right.setVisibility(View.GONE);
                    holder.sportTx.setText(R.string.sport_fail);
                } else {
                    holder.right.setVisibility(View.VISIBLE);
                    holder.sportTx.setText(R.string.sport_success);
                }
            }
            holder.textView2.setText(recommend.get(position).sport_number);
            holder.image.setImageURI(Uri.parse(recommend.get(position).imgpath));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnClick("", position,
                            recommend.get(position).content,
                            recommend.get(position).imgpath,
                            recommend.get(position).sport_number);
                }
            });
            //游客
        } else if (data != null && data.size() > 0) {
            if (data.get(position).type.equals("1")) {
                holder.textView1.setText("晨练");
            } else if (data.get(position).type.equals("2")) {
//                holder.minesportLayout.setVisibility(View.GONE);
                holder.textView1.setText("步行");
            } else if (data.get(position).type.equals("3")) {
                holder.textView1.setText("晚练");
            }
            holder.right.setVisibility(View.GONE);
            holder.sportTx.setText(R.string.sport_fail);
            holder.textView2.setText(data.get(position).sport_number + "");
            holder.image.setImageURI(Uri.parse(data.get(position).imgpath));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.OnClick("", position,
                            data.get(position).content,
                            data.get(position).imgpath,
                            data.get(position).sport_number + "");
                }
            });
        }

    }

    public interface ItemClickListener {
        void OnClick(String id, int i, String content, String img, String sportNum);

    }

    private ItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(ItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public int getItemCount() {
        if (recommend != null && recommend.size() > 0) {
            return recommend.size();
        } else if (data != null && data.size() > 0) {
            return data.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private SimpleDraweeView image;
        private TextView sportTx;
        private ImageView right;
        private RelativeLayout minesportLayout;
        private RelativeLayout alpha;
        private RelativeLayout minesport_layou;

        public ViewHolder(View itemView) {
            super(itemView);
            minesport_layou = (RelativeLayout) itemView.findViewById(R.id.minesport_layou);
            textView1 = (TextView) itemView.findViewById(R.id.minesport_text1);
            textView2 = (TextView) itemView.findViewById(R.id.minesport_text2);
            image = (SimpleDraweeView) itemView.findViewById(R.id.sport_img);
            sportTx = (TextView) itemView.findViewById(R.id.sport_tx);
            right = (ImageView) itemView.findViewById(R.id.sport_right);
            minesportLayout = (RelativeLayout) itemView.findViewById(R.id.minesport_layout);
            alpha = (RelativeLayout) itemView.findViewById(R.id.minesport_alpha);
        }
    }
}
