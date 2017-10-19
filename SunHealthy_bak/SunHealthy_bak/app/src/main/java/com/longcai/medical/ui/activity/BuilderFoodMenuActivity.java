package com.longcai.medical.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.R;
import com.longcai.medical.adapter.FoodMenuListAdapter;
import com.longcai.medical.bean.BuilderFood;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.gloabe.ToastPrompt;
import com.longcai.medical.ui.BaseActivity;

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
 * 二期生成食谱
 */
public class BuilderFoodMenuActivity extends BaseActivity {


    private static final String TAG = "BuilderFoodMenuActivity";
    @BindView(R.id.food_menu_back_btn)
    RelativeLayout foodMenuBackBtn;
    @BindView(R.id.food_menu_list)
    RecyclerView foodMenuList;
    @BindView(R.id.activity_builder_food_menu)
    LinearLayout activityBuilderFoodMenu;

    private FoodMenuListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_food_menu);
        ButterKnife.bind(this);

        ArrayList<String> ids = getIntent().getStringArrayListExtra("ids");
        String[] b = ids.toArray(new String[ids.size()]);
        String str1 = "";
        if (b.length == 1) {
            str1 = b[0];
        } else {
            str1 = b[0];
            for (int i = 1; i < b.length; i++) {
                str1 = str1 + "," + b[i];
            }
        }
        //生成食谱的接口
        setMakeFood(str1);
        adapter = new FoodMenuListAdapter(this);
        foodMenuList.setLayoutManager(new LinearLayoutManager(this));
        foodMenuList.setAdapter(adapter);

    }

    private void setMakeFood(String str1) {
        RequestParams params = new RequestParams(MyUrl.FoodMenu);
        params.addParameter("sid", str1);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getServiceData: onSuccess   " + result);
               processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getServiceData: onError   " + ex.getMessage());
                BuilderFoodMenuActivity.this.finish();
                ToastUtils.show(BuilderFoodMenuActivity.this, ToastPrompt.Service_error);
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
    protected void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                BuilderFood builderFood = gson.fromJson(result, BuilderFood.class);
                Log.i(TAG, "processData: " + builderFood.toString());

                List<BuilderFood.DataBean> data = builderFood.data;
                adapter.setDataBeen(data);

            }else{
                ToastUtils.show(BuilderFoodMenuActivity.this, ToastPrompt.Service_error);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @OnClick(R.id.food_menu_back_btn)
    public void onClick() {
        this.finish();
    }
}
