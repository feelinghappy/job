package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.MineSportAdapter;
import com.longcai.medical.adapter.MonthsDayAdapter;
import com.longcai.medical.bean.Sport;
import com.longcai.medical.bean.SportList;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SportActivity extends BaseActivity {


    private static final String TAG = "SportActivity";
    @BindView(R.id.bank_img)
    RelativeLayout bankImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_container)
    RelativeLayout titleContainer;
    @BindView(R.id.time_tx1)
    TextView timeTx1;
    @BindView(R.id.time_list)
    RecyclerView timeList;
    @BindView(R.id.time_select)
    LinearLayout timeSelect;
    @BindView(R.id.sport_list)
    RecyclerView sportList;
    @BindView(R.id.activity_sport)
    RelativeLayout activitySport;
    private String is = "-1";

    private String type = "1";
    private String time;
    private String familyId = "0";
    private MineSportAdapter mineSportAdapter;
    private List<String> yearmonth;
    private List<String> monthsDay;
    private List<String> data2;
    private List<String> day;
    private MonthsDayAdapter monthsDayAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        ButterKnife.bind(this);
        init();

    }



    private void init() {
        mineSportAdapter = new MineSportAdapter(SportActivity.this, familyId);
        sportList.setLayoutManager(new LinearLayoutManager(SportActivity.this));
        sportList.setAdapter(mineSportAdapter);
        //游客是1   会员是2
        is = getIntent().getStringExtra("is");
        familyId = getIntent().getStringExtra("familyId");
        Log.d("1555", "is:" + is);
        if (is.equals("1")) {
            yearmonth = TimeUtil.getYearMonthDay();
            monthsDay = TimeUtil.getMonthsDay();
            timeTx1.setText(monthsDay.get(0));
            //  游客就看不到  时间条
            timeList.setVisibility(View.GONE);
            String cm = getIntent().getStringExtra("cm");
            String kg = getIntent().getStringExtra("kg");
            String time = getIntent().getStringExtra("time");
            final String familyId = getIntent().getStringExtra("familyId");
            int sex = getIntent().getIntExtra("sex", -1);
            setData(cm, kg, time, familyId, sex);
            mineSportAdapter.setOnItemClickLitener(new MineSportAdapter.ItemClickListener() {
                @Override
                public void OnClick(String id, int i, String content, String img, String sportNum) {
                    if (i == 1) {
                        //TODO 查看某天数据
                        startActivity(new Intent(SportActivity.this, PedometerActivity.class).
                                putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "2").putExtra("familyId", familyId));
                    } else if (i == 0) {
                        startActivity(new Intent(SportActivity.this, SportInfoActivity.class).
                                putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "1").putExtra("familyId", familyId));
                    } else {
                        startActivity(new Intent(SportActivity.this, SportInfoActivity.class).
                                putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "3").putExtra("familyId", familyId));
                    }

                }
            });

        } else if (is.equals("2")) {
            //这里  判断 时间条  显示不显示
            if (MyApplication.myPreferences.readGrade().equals("1")) {
                Log.d("1535", "11111111111");
                monthsDay = TimeUtil.getMonthsDay();
                timeTx1.setText(monthsDay.get(0));
                Log.d("1555", "grade2:");
                timeList.setVisibility(View.GONE);
            } else if (MyApplication.myPreferences.readGrade().equals("2")) {
                timeList.setVisibility(View.VISIBLE);
                Log.d("1555", "grade1:");
                Log.d("1535", "2222222");
                //获取日期
                data2 = new ArrayList<>();
                yearmonth = TimeUtil.getYearMonthDay();
                monthsDay = TimeUtil.getMonthsDay();
                day = TimeUtil.getDay();
                for (int i = 0; i < day.size(); i++) {
                    data2.add("false");
                }
                data2.set(0, "true");
                timeTx1.setText(monthsDay.get(0));
                //设置布局管理器
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                timeList.setLayoutManager(linearLayoutManager);

                monthsDayAdapter = new MonthsDayAdapter(this);
                monthsDayAdapter.setData(day);
                monthsDayAdapter.setData2(data2);
                timeList.setAdapter(monthsDayAdapter);

                monthsDayAdapter.setOnItemClickLitener(new MonthsDayAdapter.ItemClickListener() {
                    @Override
                    public void OnClick(String id) {
                        Log.d("1555", "id" + id);
                        if (id.equals("0")) {
                            type = "1";
                        } else {
                            type = "0";
                        }
                        timeTx1.setText(monthsDay.get(Integer.parseInt(id)));

                        time = yearmonth.get(Integer.parseInt(id));
                        int size = data2.size();
                        data2.clear();
                        for (int i = 0; i < size; i++) {
                            data2.add("false");
                        }
                        data2.set((Integer.parseInt(id)), "true");
                        monthsDayAdapter.setData2(null);
                        monthsDayAdapter.setData2(data2);
                        initView(time);

                    }
                });

            }
            Calendar c = Calendar.getInstance();
            int i = c.get(Calendar.YEAR);
            int i1 = c.get(Calendar.MONTH);
            int i2 = c.get(Calendar.DAY_OF_MONTH);
            String year = String.valueOf(i);
            String month = String.valueOf(i1);
            String day = String.valueOf(i2);
            Log.d("1535", "time:" + year + "-" + month + "-" + "-" + day);
            if (month.equals("0")) {
                month = "1";
            }
            time = year + "-" + month + "-" + day;
            initView(time);
            mineSportAdapter.setOnItemClickLitener(new MineSportAdapter.ItemClickListener() {
                @Override
                public void OnClick(String id, int i, String content, String img, String sportNum) {
                    if (type.equals("1")) {
                        if (i == 1) {
                            //TODO 查看某天数据
                            startActivity(new Intent(SportActivity.this, PedometerActivity.class).
                                    putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "2").putExtra("familyId", familyId));
                        } else if (i == 0) {
                            startActivity(new Intent(SportActivity.this, SportInfoActivity.class).
                                    putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "1").putExtra("familyId", familyId));
                        } else {
                            startActivity(new Intent(SportActivity.this, SportInfoActivity.class).
                                    putExtra("content", content).putExtra("img", img).putExtra("sportNum", sportNum).putExtra("type", "3").putExtra("familyId", familyId));
                        }
                    } else {

                    }

                }
            });
        }

    }

    private void setData(String cm, String kg, String time, String familyId, int sex) {
        RequestParams params = new RequestParams(MyUrl.Sport);
        params.addParameter("uid", Constant.UID);
        params.addParameter("birth", time);
        params.addParameter("weight", kg);
        params.addParameter("height", cm);
        params.addParameter("age", sex);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "setData: onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "setData: onError   " + ex.getMessage());
                ToastUtils.show(SportActivity.this, ""+R.string.jsonfail);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "setData: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getServiceData: onFinished   ");

            }
        });

    }

    protected void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                Sport sport = gson.fromJson(result, Sport.class);
                Log.i(TAG, "processData: " + sport.toString());
                List<Sport.DataBean> data = sport.data;
                if (is.equals("1")) {
                    mineSportAdapter.setData2(data);
                    Log.d("1535", "dataSSSSS:" + data.size());
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void initView(String time) {
        Log.d("1555", "日期为" + time);
        RequestParams params = new RequestParams(MyUrl.RecommendList);
        params.addParameter("uid", Constant.UID);
        params.addParameter("time", time);
        params.addParameter("familyId", "");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "initView: onSuccess   " + result);
                processSportListData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "initView: onError   " + ex.getMessage());
                ToastUtils.show(SportActivity.this, ""+R.string.jsonfail);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "initView: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "initView: onFinished   ");

            }
        });
    }

    //运动列表数据
    private List<SportList.DataBean> recommend;

    private void processSportListData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                SportList sportList = gson.fromJson(result, SportList.class);
                Log.i(TAG, "processSportListData: " + sportList.toString());
                recommend = sportList.data;
                if (is.equals("2"))
                    mineSportAdapter.setData(recommend);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
