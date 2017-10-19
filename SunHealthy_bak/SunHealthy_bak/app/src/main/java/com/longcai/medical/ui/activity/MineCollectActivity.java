package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.ZiXunAdapter;
import com.longcai.medical.bean.MineCollectListBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//我的收藏
public class MineCollectActivity extends BaseActivity {


    private static final String TAG = "MineCollectActivity";
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.mine_collect_list)
    RecyclerView mineCollectList;
    @BindView(R.id.activity_mine_collect)
    LinearLayout activityMineCollect;
    private ZiXunAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_collect);
        ButterKnife.bind(this);
        titleTv.setText("我的收藏");
        titleRightTv.setVisibility(View.GONE);

        adapter = new ZiXunAdapter(this);
        mineCollectList.setLayoutManager(new LinearLayoutManager(MineCollectActivity.this));
        mineCollectList.setAdapter(adapter);
        //接口请求
        initListRequest();
    }

    private void initListRequest() {
        //没有 url
        RequestParams params = new RequestParams(MyUrl.LOGIN);
        params.addParameter("uid", MyApplication.myPreferences.readUID());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getData: onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getData: onError   " + ex.getMessage());
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
    //返回结果
    private void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            Gson gson = new Gson();
            MineCollectListBean info = gson.fromJson(result, MineCollectListBean.class);
            if (code == 1) {

                adapter.setData2(info.getData());
                adapter.setOnItemClickLitener(new ZiXunAdapter.ItemClickListener() {
                    @Override
                    public void OnClick(int position, String url, String id) {
                        startActivity(new Intent(MineCollectActivity.this, Web2Activity.class).
                                putExtra("title", "资讯详情").putExtra("url", url).putExtra("id", id));
                    }
                });
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @OnClick(R.id.title_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
