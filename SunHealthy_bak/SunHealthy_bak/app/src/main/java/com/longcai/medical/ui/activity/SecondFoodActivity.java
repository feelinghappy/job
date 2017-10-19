package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.utils.ToastUtils;
import com.google.gson.Gson;
import com.longcai.medical.MyApplication;
import com.longcai.medical.R;
import com.longcai.medical.adapter.GalleryAdapter;
import com.longcai.medical.bean.FoodMaterial;
import com.longcai.medical.bean.SelectFoodBean;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.service.SecondFoodMaterialFragment;
import com.longcai.medical.ui.BaseFragmentActivity;
import com.longcai.medical.ui.fragment.FoodListFragment;
import com.longcai.medical.ui.fragment.SecondFoodMenuFragment;
import com.longcai.medical.utils.LogUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 二期饮食全改
 */
public class SecondFoodActivity extends BaseFragmentActivity implements FoodListFragment.FragmentInteraction {


    private static final String TAG = "SecondFoodActivity";
    @BindView(R.id.second_food_back)
    AutoLinearLayout secondFoodBack;
    @BindView(R.id.second_food_tx2)
    TextView secondFoodTx2;
    @BindView(R.id.second_food_tx3)
    TextView secondFoodTx3;
    @BindView(R.id.menu_btn)
    ImageView menuBtn;
    @BindView(R.id.fragment_container)
    AutoRelativeLayout fragmentContainer;
    @BindView(R.id.recyclerview_horizontal)
    RecyclerView recyclerviewHorizontal;
    @BindView(R.id.food_menu_btn)
    AutoLinearLayout foodMenuBtn;
    @BindView(R.id.food_bottom_view)
    AutoLinearLayout foodBottomView;
    @BindView(R.id.activity_second_food)
    AutoLinearLayout activitySecondFood;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    private GalleryAdapter bottomAdapter;

    private List<FoodMaterial.HotFoodData> HotFoodData;
    private List<FoodMaterial.HotFood> hotData;

    private List<FoodMaterial.AllFoodData> allFoodData;
    private ArrayList<FoodMaterial.AllFood> allData;

    private List<FoodMaterial.RecipeData> recipeData;
    private List<FoodMaterial.Recipe> recipe;
    private HashMap<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_food);
        ButterKnife.bind(this);
        MyApplication.myPreferences.saveSelectPosition("0");
        bottomAdapter = new GalleryAdapter(SecondFoodActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerviewHorizontal.setLayoutManager(linearLayoutManager);
//        getServiceData();
        titleTv.setText("食材");
        titleRightTv.setVisibility(View.GONE);
        setFoodMaterialView();//食材
    }

    private void getServiceData() {
        map = new HashMap();
        RequestParams params = new RequestParams(MyUrl.GET_FOOD_CATE);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "getServiceDaata(): onSuccess   " + result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "getServiceDaata(): onError   " + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "getServiceDaata(): onCancelled   ");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "getServiceDaata(): onFinished   ");

            }
        });

    }

    private void processData(String result) {
        try {
            JSONObject jso = new JSONObject(result);
            int code = jso.getInt("status");
            if (code == 1) {
                Gson gson = new Gson();
                FoodMaterial foodMaterial = gson.fromJson(result, FoodMaterial.class);
                LogUtils.i(TAG, "processData..." + foodMaterial.toString());
                HotFoodData = foodMaterial.data.hot_food;
                allFoodData = foodMaterial.data.all_food;
                recipeData = foodMaterial.data.recipe;
                secondFoodTx2.setSelected(true);
            } else {
//                Toast.makeText(mActivity, "无家庭，赶快去创建吧", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private SecondFoodMaterialFragment foodMaterialView = SecondFoodMaterialFragment.newInstance(null, null);

    private void setFoodMaterialView() {
        //两个参数 随便传，不需要的时候传null
        if (foodMaterialView == null) {
            foodMaterialView = SecondFoodMaterialFragment.newInstance(null, null);
        }
        if (!menuBtn.isShown()) {
            menuBtn.setVisibility(View.VISIBLE);
        }
        getSupportFragmentManager().
                beginTransaction().replace(R.id.fragment_container, foodMaterialView)
//                .addToBackStack(null)
                .commit();
    }

    @OnClick({R.id.second_food_back, R.id.second_food_tx2, R.id.second_food_tx3,
            R.id.menu_btn, R.id.food_menu_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.second_food_back:
                this.finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.second_food_tx2:
                setDefaultClick();
                secondFoodTx2.setSelected(true);
                setFoodMaterialView();
                break;
            case R.id.second_food_tx3:
                setDefaultClick();
                secondFoodTx3.setSelected(true);
                setFoodMenuView();//食谱
                break;
            case R.id.menu_btn:
                if (menuBtn.isSelected()) {
                    menuBtn.setSelected(false);
                    foodBottomView.setVisibility(View.GONE);
                    MyApplication.myPreferences.saveSelectPosition("0");
                } else {
                    menuBtn.setSelected(true);
                    foodBottomView.setVisibility(View.VISIBLE);
                    MyApplication.myPreferences.saveSelectPosition("1");
                }
                break;
            case R.id.food_menu_btn:
                if (foodIds != null && foodIds.size() > 0) {
                    Set<String> set = new LinkedHashSet<String>();
                    set.addAll(foodIds);
                    foodIds.clear();
                    foodIds.addAll(set);
                    Log.d("1535", "fod:" + foodIds.toString());
                    startActivity(new Intent(SecondFoodActivity.this,
                            BuilderFoodMenuActivity.class).putStringArrayListExtra("ids", foodIds));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                } else {
                    ToastUtils.show(SecondFoodActivity.this, "请选择食材");
                }
                break;
        }
    }

    private void setDefaultClick() {
        secondFoodTx2.setSelected(false);
        secondFoodTx3.setSelected(false);
    }

    private SecondFoodMenuFragment foodMenuFragment = SecondFoodMenuFragment.newInstance(null, null);

    private void setFoodMenuView() {
        //两个参数 随便传，不需要的时候传null
        if (foodMenuFragment == null) {
            foodMenuFragment = SecondFoodMenuFragment.newInstance(null, null);
        }
        if (foodBottomView.isShown()) {
            foodBottomView.setVisibility(View.GONE);
        }
        if (menuBtn.isShown()) {
            menuBtn.setVisibility(View.GONE);
        }
        getSupportFragmentManager().
                beginTransaction().replace(R.id.fragment_container, foodMenuFragment)
//                .addToBackStack(null)
                .commit();
    }

    public List<FoodMaterial.HotFood> getHotData() {
        hotData = new ArrayList<FoodMaterial.HotFood>();
        if (HotFoodData != null && HotFoodData.size() > 0) {
            for (int i = 0; i < HotFoodData.size(); i++) {
                hotData.addAll(HotFoodData.get(i).food);
            }
        }
        return hotData;
    }

    public List<FoodMaterial.AllFood> getAllDate() {
        allData = new ArrayList<FoodMaterial.AllFood>();
        if (HotFoodData != null && HotFoodData.size() > 0) {
            for (int i = 0; i < allFoodData.size(); i++) {

                allData.addAll(allFoodData.get(i).food);
            }
        }
        return allData;
    }

    public List<FoodMaterial.Recipe> getData() {
        recipe = new ArrayList<FoodMaterial.Recipe>();
        if (recipeData != null && recipeData.size() > 0) {
            for (int i = 0; i < recipeData.size(); i++) {

                recipe.addAll(recipeData.get(i).recipe);
            }
        }
        return recipe;
    }

    /**
     * bottomData 底部生成食谱的数据元
     */
    private List<SelectFoodBean> bottomData = new ArrayList<>();
    private List<String> ids = new ArrayList<>();
    private String id;
    private ArrayList<String> foodIds = new ArrayList<>();

    @Override
    public void process(SelectFoodBean bean) {
        for (int i = 0; i < bottomData.size(); i++) {
            id = bottomData.get(i).id;
            ids.add(id);
        }
        foodIds.add(bean.id);
        if (!ids.contains(bean.id)) {
            //添加
            bottomData.add(0, bean);
        } else {
            //移除
            bottomData.remove(bean);
        }
        Log.d("1535", "b:" + bottomData.size());
        bottomAdapter.setData(bottomData);
        //bottomAdapter.notifyDataSetChanged();
        recyclerviewHorizontal.setAdapter(bottomAdapter);

    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }
}
