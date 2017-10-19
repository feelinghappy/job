package com.longcai.medical.ui.activity;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.adapter.RestAdapter;
import com.longcai.medical.bean.MineRest;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.AlarmManagerUtil;
import com.longcai.medical.utils.DbUtils;
import com.longcai.medical.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的作息  fei
public class MineRestActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.back_btn)
    RelativeLayout backBtn;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.rest_text)
    TextView restText;
    @BindView(R.id.morning_list)
    RecyclerView morningList;
    @BindView(R.id.rest_layout_morning)
    LinearLayout restLayoutMorning;
    @BindView(R.id.add_rest_view)
    LinearLayout addRestView;
    private Intent intent;
    private Cursor mCursor;

    private int minuts;
    private int hours;

    //数据库
    private String DB_NAME = "minerest";
    //搜索的东西
    private String CURRENTDATE = "";
    //
    int rest1 = 0;
    //悬浮窗
    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;
    List<MineRest> mineRestsBoo;
    private List<Boolean> rest_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rest);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydb();
    }
    private RestAdapter adapter;
    private void mydb() {
        DbUtils.createDb(this, DB_NAME);
        CURRENTDATE = "all";
        mineRestsBoo = DbUtils.getQueryByWhere(MineRest.class, "str", new String[]{CURRENTDATE});
//        List<MineRest> mineRestsBoo=DbUtils.getQueryAll(MineRest.class);
        if (mineRestsBoo.size() == 0 || mineRestsBoo.isEmpty()) {
            rest1 = 0;
            restText.setText("");
            adapter = new RestAdapter(this, "-1");
            morningList.setAdapter(adapter);
        } else {
            rest1 = mineRestsBoo.size();
            rest_image = new ArrayList<Boolean>();
            restText.setText("");
            Collections.sort(mineRestsBoo, new CalendarComparator()); // 根据时间排序
            for (int i = 0; i < mineRestsBoo.size(); i++) {
                rest_image.add(mineRestsBoo.get(i).isRestboolean());
            }
            StaggeredGridLayoutManager slManager1 = new StaggeredGridLayoutManager(rest1, StaggeredGridLayoutManager.HORIZONTAL);
            morningList.setLayoutManager(slManager1);
            List<MineRest> mineRestsBoo = this.mineRestsBoo;
            adapter = new RestAdapter(this, rest1, 1, this.mineRestsBoo, rest_image);
            morningList.setAdapter(adapter);
            init();
        }
    }

    // 自定义比较器：按书出版时间来排序
    static class CalendarComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            MineRest p1 = (MineRest) object1;// 强制转换
            MineRest p2 = (MineRest) object2;
            if (p2.getHour().equals(p1.getHour())) {
                return p1.getMinute().compareTo(p2.getMinute());
            }
            return p1.getHour().compareTo(p2.getHour());
        }
    }
    Calendar calendar = Calendar.getInstance();
    private void init() {

        adapter.setOnItemClickLitener(new RestAdapter.ItemClickListener() {
            @Override
            public void OnClick(View view, ImageView imageView, int position, TextView text1, TextView text2) {
                List<MineRest> list;
                CURRENTDATE = MineRestActivity.this.mineRestsBoo.get(position).getTime();
                list = DbUtils.getQueryByWhere(MineRest.class, "resttime", new String[]{CURRENTDATE});
                if (rest_image.get(position) == false) {
                    imageView.setImageResource(R.mipmap.rest_adapter_image3);
                    int time1 = Integer.parseInt("" + text1.getText().toString());
                    int time2 = Integer.parseInt("" + text2.getText().toString());

                    MineRestActivity.this.mineRestsBoo.get(position).setRestboolean(true);
                    MineRest data = MineRestActivity.this.mineRestsBoo.get(position);
                    data.setRestboolean(true);
                    DbUtils.update(data);

//
                    if (calendar.get(Calendar.HOUR_OF_DAY) <= time1 && calendar.get(Calendar.MINUTE) <= time2) {
                        AlarmManagerUtil.setAlarm(MineRestActivity.this, Integer.parseInt("0"), time1, time2,
                                ""+MineRestActivity.this.mineRestsBoo.get(position).getId(), Integer.parseInt(MineRestActivity.this.mineRestsBoo.get(position).getWeek()), MineRestActivity.this.mineRestsBoo.get(position).getTitle(),
                                Integer.parseInt(MineRestActivity.this.mineRestsBoo.get(position).getType()), true);
                    }
                    rest_image.set(position, true);
                } else {
                    imageView.setImageResource(R.mipmap.rest_adapter_image4);
                    AlarmManagerUtil.cancelAlarm(MineRestActivity.this, "com.longcai.alarm.clock", position);
//                    list.get(0).setRestboolean(false);
                    MineRestActivity.this.mineRestsBoo.get(position).setRestboolean(false);
                    MineRest data = MineRestActivity.this.mineRestsBoo.get(position);
                    data.setRestboolean(false);
                    DbUtils.update(data);

                    rest_image.set(position, false);
                }
            }
        });
        adapter.setOnItemLongClickLitener(new RestAdapter.ItemClickLongListener() {

            @Override
            public void OnCLick(View view, int position) {
                CURRENTDATE = "" + MineRestActivity.this.mineRestsBoo.get(position).getId();
                Log.d("1555", "删除" + MineRestActivity.this.mineRestsBoo.get(position).getId());
                showDialogView();
            }
        });


    }
    private void showDialogView() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_text_view, null);
//        MyApplication.ScaleScreenHelper.loadView((ViewGroup) contentView);
        TextView positiveButton = (TextView) contentView.findViewById(R.id.positiveButton);
        TextView negativeButton = (TextView) contentView.findViewById(R.id.negativeButton);
        TextView text = (TextView) contentView.findViewById(R.id.exit_view_text);
        text.setText("确认删除");
        dialog.show();
        dialog.getWindow().setContentView(contentView);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(MineRestActivity.this, "删除成功");
                DbUtils.deleteWhere(MineRest.class, "id", new String[]{CURRENTDATE});
                dialog.dismiss();
                mydb();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    @OnClick({R.id.back, R.id.add_rest_view, R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.add_rest_view:
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (!Settings.canDrawOverlays(this)){
                        ToastUtils.showToast(MineRestActivity.this,"当前无权限，请授权");
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent,OVERLAY_PERMISSION_REQ_CODE);
                    }else {
                        startActivity(new Intent(this, EditRestActivity.class).putExtra("type",-1));
                    }
                }else {
                    startActivity(new Intent(this, EditRestActivity.class).putExtra("type",-1));
                }
                break;
        }
    }
    /**
     * 用户返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
//            if (!Settings.canDrawOverlays(this)) {
//                ToastUtils.show(MineRestActivity.this,"权限授予失败，无法开启悬浮窗");
//            } else {
//                ToastUtils.show(MineRestActivity.this,"权限授予成功！");
//                //启动
//            }

        }
    }
}
