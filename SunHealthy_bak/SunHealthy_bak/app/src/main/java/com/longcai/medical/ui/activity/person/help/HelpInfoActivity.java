package com.longcai.medical.ui.activity.person.help;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.adapter.HelpInfoAdapter;
import com.longcai.medical.bean.HelpInfoResult;
import com.longcai.medical.gloabe.MyUrl;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.DividerItemDecoration;
import com.longcai.medical.utils.HttpUtils;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/3.
 */

public class HelpInfoActivity extends BaseActivity implements HelpInfoAdapter.ItemClickListener  {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(android.R.id.list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;

    private Unbinder unbinder;
    private final int REQUEST_CODE_ADVICE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        titleRightTv.setVisibility(View.GONE);
        titleTv.setText("帮助与反馈");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        HttpUtils.xOverallHttpPost(this, MyUrl.HELP_INFO, map, HelpInfoResult.class, new HttpUtils.OnxHttpWithListResultCallBack<HelpInfoResult>() {
            @Override
            public void onSuccessMsg(String successMsg) {

            }

            @Override
            public void onSuccess(List<HelpInfoResult> t) {
                HelpInfoAdapter adapter = new HelpInfoAdapter(HelpInfoActivity.this);
                adapter.setData(t);
                recyclerView.setAdapter(adapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(HelpInfoActivity.this, DividerItemDecoration.VERTICAL_LIST));
                adapter.setOnItemClickLitener(HelpInfoActivity.this);
            }

            @Override
            public void onFail(int code, String msg) {

            }
        });
    }

    @OnClick({R.id.title_back, R.id.tv_feedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_feedback:
                Intent intent = new Intent(this, AdviceActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADVICE);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADVICE && resultCode == RESULT_OK) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void OnClick(int position, HelpInfoResult result) {
        Intent intent = new Intent(this, HelpInfoContentActivity.class);
        intent.putExtra("HelpInfo", result);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
