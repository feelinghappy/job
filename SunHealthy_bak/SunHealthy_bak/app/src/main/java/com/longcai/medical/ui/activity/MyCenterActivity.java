package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.R;
import com.longcai.medical.adapter.CenterAdapter;
import com.longcai.medical.bean.MyCenter;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.recyclerview.EndlessRecyclerOnScrollListener;
import com.longcai.medical.ui.view.recyclerview.LoadingFooter;
import com.longcai.medical.ui.view.recyclerview.RecyclerViewStateUtils;
import com.longcai.medical.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/3.
 * 我的活动
 */

public class MyCenterActivity extends BaseActivity {

    private static final String TAG ="MyCenterActivity" ;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.bank_btn)
    RelativeLayout bankBtn;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.title_text2)
    TextView titleText2;
    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    private String NowPage = "-1";//当前页码
    private String PageSize = "-1";//每页条数
    private String TotalPage = "-2";//总页数

    private CenterAdapter centerAdapter;
    // private List<JsonMyActivity.Info.DataBean.ActivisBean> data;
    //总页数
    int pages = 1;
    //当前页数
    int now_pages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        centerAdapter = new CenterAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(centerAdapter);
        LoadData(1);
        addScrollContorl();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
                mypart(1);
            }
        });
        centerAdapter.setOnItemClickLitener(new CenterAdapter.ItemClickListener() {

            @Override
            public void OnClick(String aid, int position) {
                startActivity(new Intent(MyCenterActivity.this, ApplySportActivity.class).
                        putExtra("aid", aid).putExtra("ps", position));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void OnWebClick(String url, String aid, int ps) {
                startActivity(new Intent(MyCenterActivity.this, WebActivity.class)
                        .putExtra("url", url).putExtra("title", "活动详情").
                                putExtra("aid", aid).putExtra("ps", ps));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }


        });
    }

    private int page = -1;

    private void LoadData(int i) {
        page = i;
        mypart(page);
    }

    private void addScrollContorl() {
        list.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState
                        (list);
                if (state == LoadingFooter.State.Loading) {
                    Log.d("@Cundong", "the state is Loading, just wait..");
                    return;
                } else if (state == LoadingFooter.State.TheEnd) {
                    Log.d("@Cundong", "the state is end.");
                    return;
                }
                if (!TotalPage.equals(NowPage)) {
                    // loading more
                    RecyclerViewStateUtils.setFooterViewState(MyCenterActivity.this,
                            list, Integer.parseInt(PageSize), LoadingFooter.State.Loading,
                            null);
                    mypart(Integer.parseInt(NowPage) + 1);
                } else {
                    //the end
                    ToastUtils.show(MyCenterActivity.this, "暂无更多数据");
                    RecyclerViewStateUtils.setFooterViewState(MyCenterActivity.this,
                            list, Integer.parseInt(PageSize), LoadingFooter.State.TheEnd,
                            null);

                }
            }
        });

    }

    private void mypart(final int now_pages) {
        titleText.setText("我的活动");
        titleText2.setVisibility(View.GONE);
        RequestParams params = new RequestParams(MyUrl.Center);
        params.addBodyParameter("page", "" + now_pages);
        params.addBodyParameter("uid", Constant.UID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getData: onSuccess   " + result);
                processData(result,now_pages);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getData: onError   " + ex.getMessage());
                refresh.setRefreshing(false);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getData: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getData: onFinished   ");

            }
        });

    }
    private List<MyCenter.ActivisBean> data;
    private void processData(String result, int now_pages) {
        try {
            JSONObject jso = new JSONObject(result);
            String code = jso.getString("code");
            if (code.equals("1")) {
                Gson gson = new Gson();
                MyCenter myCenter = gson.fromJson(result, MyCenter.class);
                LogUtils.i(TAG, "processData..." + myCenter.toString());
                TotalPage = String.valueOf(myCenter.data.pages);
                NowPage = String.valueOf(now_pages);
                PageSize = String.valueOf(myCenter.data.activis.size());
                RecyclerViewStateUtils.setFooterViewState(MyCenterActivity.this,
                        list, Integer.parseInt(PageSize),
                        LoadingFooter.State.Normal, null);

                    data =myCenter.data.activis;

                centerAdapter.addData2(data);

            } else {
//                Toast.makeText(mActivity, "无家庭，赶快去创建吧", Toast.LENGTH_SHORT).show();
            }
            refresh.setRefreshing(false);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.bank_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.bank_btn:
                onBackPressed();
                break;
        }
    }
}
