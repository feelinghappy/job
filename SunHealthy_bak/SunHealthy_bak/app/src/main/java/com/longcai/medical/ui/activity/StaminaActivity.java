package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.StaminaListAdapter;
import com.longcai.medical.bean.ConstitutionResult;
import com.longcai.medical.bean.TestTopic;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.view.recyclerview.HeaderAndFooterRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 体能测试
 */
public class StaminaActivity extends BaseActivity {

    private static final int NEWHOMEPAGE_FRAGMENT_TIZHI = 1;
    private static final int CREATE_MEM_TIZHI = 3;
    private static final int FAMILY_MEM_TIZHI = 5;
    private static final String TAG = "StaminaActivity";
    @BindView(R.id.bank_btn)
    RelativeLayout bankBtn;
    @BindView(R.id.title_container)
    RelativeLayout titleContainer;
    @BindView(R.id.stamina_recyclerview)
    RecyclerView list;
    @BindView(R.id.stamina_btn_erqi)
    RelativeLayout staminaBtnErqi;
    @BindView(R.id.stamina_layout3)
    RelativeLayout staminaLayout3;
    @BindView(R.id.ceshi_tx)
    TextView ceshiTx;
    @BindView(R.id.ceshi_btn)
    RelativeLayout ceshiBtn;
    @BindView(R.id.over_cs_view)
    RelativeLayout overCsView;
    @BindView(R.id.activity_stamina)
    RelativeLayout activityStamina;

    private String type = "-1";

    private String familyid = "-1";
    private boolean is_look;
    private String physical;
    private StaminaListAdapter adapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;

    List<List<Boolean>> listboo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamina);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        View headview = LayoutInflater.from(StaminaActivity.this).inflate(R.layout.dati_head_view, null);
//        MyApplication.ScaleScreenHelper.loadView((ViewGroup) headview);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        adapter = new StaminaListAdapter(this);
        headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(adapter);
        headerAndFooterRecyclerViewAdapter.addHeaderView(headview);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(headerAndFooterRecyclerViewAdapter);
        getTestTopicFormService(); //获取体质测试题目
        adapter.setOnItemClickLitener(new StaminaListAdapter.ItemClickListener() {
            @Override
            public void OnClick(View view, int position, int pos) {
                list.scrollToPosition(pos);
            }
        });

    }

    private void getTestTopicFormService() {
        RequestParams params = new RequestParams(MyUrl.getPhysical);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getServiceData: onSuccess   " + result);
                processTestTopicData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getServiceData: onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getServiceData: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getServiceData: onFinished   ");

            }
        });
    }

    private void processTestTopicData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                TestTopic testTopic = gson.fromJson(result, TestTopic.class);
                Log.i(TAG, "processTestTopicData: " + testTopic.toString());
                listboo = new ArrayList<List<Boolean>>();
                for (int i = 0; i < testTopic.data.size(); i++) {
                    List<Boolean> listboolean = new ArrayList<Boolean>();
                    for (int j = 0; j < 5; j++) {
                        listboolean.add(false);
                    }
                    listboo.add(listboolean);
                }
                adapter.setBoolean(listboo);

                adapter.setData(testTopic.data);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 二期修改
     * , R.id.submit_btn, R.id.go_home_btn
     */
    @OnClick({R.id.bank_btn, R.id.stamina_btn_erqi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bank_btn:
                this.finish();
                break;
            case R.id.stamina_btn_erqi:
                List<Integer> mark = adapter.getMark();
                for (int i = 0; i < mark.size(); i++) {
                    if (mark.get(i) == 0) {
                        ToastUtils.show(StaminaActivity.this, "您的第" + (i + 1) + "个问题没有回答");
                        return;
                    }
                }
                String strmark = mark.toString().replace("[", "").replace("]", "");
                if (type.equals("1") || type.equals("2") || type.equals("3")) {
                    mymark(strmark);
                } else if (type.equals("4")) {
//                    myfamilymark(strmark, familyid);
                }
                break;

        }
    }
    private void mymark(String mark) {
        RequestParams params = new RequestParams(MyUrl.Physical);
        params.addParameter("uid", Constant.UID);
        params.addParameter("answers", mark);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "mymark: onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "mymark: onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "mymark: onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "mymark: onFinished   ");

            }
        });


    }

    private void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                final ConstitutionResult constitutionResult = gson.fromJson(result, ConstitutionResult.class);
                Log.i(TAG, "processData: " + constitutionResult.toString());
                physical = constitutionResult.data.physical;
                MyApplication.myPreferences.savePhysica(physical);
                //体能测试 overCsView返回结果的视图
                overCsView.setVisibility(View.VISIBLE);
                ceshiTx.setText(constitutionResult.data.physical);
                ceshiBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type.equals("1")) {
                    /*
                    * 把数据回传给NewHomePageFragment
                    * */
                            Intent intent = new Intent();
                            intent.putExtra("physical", constitutionResult.data.physical);
                            setResult(NEWHOMEPAGE_FRAGMENT_TIZHI, intent);
                            StaminaActivity.this.finish();
                        } else if (type.equals("2")) {
                    /*
                    * 把数据回传给CreateFamilyMemActivity
                    * */
                            Intent intent = new Intent();
                            Constant.STAMINA_PHYSICAL_CREATEMEM = constitutionResult.data.physical;
                            setResult(CREATE_MEM_TIZHI, intent);
                            StaminaActivity.this.finish();
                        } else if (type.equals("3")) {
                     /*
                    * 把数据回传给FamilyMemActivity
                    * */
                            Intent intent = new Intent();
                            Constant.STAMINA_PHYSICAL_CREATEMEM = constitutionResult.data.physical;
                            setResult(FAMILY_MEM_TIZHI, intent);
                            StaminaActivity.this.finish();
                        } else {
                            ToastUtils.show(StaminaActivity.this, "测试体质为" + constitutionResult.data.physical);
                            StaminaActivity.this.finish();
                        }
                    }
                });
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
