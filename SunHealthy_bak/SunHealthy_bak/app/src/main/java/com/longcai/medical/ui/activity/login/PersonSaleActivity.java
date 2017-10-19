package com.longcai.medical.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.activity.MainActivity;
import com.longcai.medical.utils.AppManager;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/14.
 * 注册- 成为个人消费商
 */

public class PersonSaleActivity extends BaseActivity {
    @BindView(R.id.sale_btn_next)
    TextView saleBtnNext;
    @BindView(R.id.sale_btn_join)
    Button saleBtnJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_sale);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.title_back, R.id.sale_btn_next, R.id.sale_btn_join})
    public void onViewClicked(View view) {
        int source = getIntent().getIntExtra("source", -1);
        boolean loginSuccess = getIntent().getBooleanExtra("login_success", false);
        boolean weixin_login = getIntent().getBooleanExtra("weixin_login", false);
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.sale_btn_next://跳过
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("source", source).putExtra("login_success", loginSuccess);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                if (weixin_login) {
                    AppManager.getAppManager().leaveTopActivity();
                }
                break;
            case R.id.sale_btn_join://我要加入
                startActivity(new Intent(this,PersonApplyActivity.class).putExtra("source", source)
                        .putExtra("login_success", loginSuccess).putExtra("weixin_login", weixin_login));
                finish();
                break;
        }
    }

}
