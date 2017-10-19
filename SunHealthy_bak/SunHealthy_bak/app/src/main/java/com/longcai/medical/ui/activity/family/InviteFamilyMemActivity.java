package com.longcai.medical.ui.activity.family;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.longcai.medical.R;
import com.longcai.medical.adapter.InviteAndCreateAdapter;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.utils.LogUtils;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/12.
 * 邀请和创建成员
 */

public class InviteFamilyMemActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.family_invite_lv)
    ListView familyInviteLv;
    @BindView(R.id.family_invite_save)
    Button familyInviteSave;
    private Unbinder unbinder;
    private ArrayList<String> avatarList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private String family_id;
    private boolean haveScanner;
    private boolean fromDevices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_mem);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("邀请新成员");
        titleRightTv.setText("跳过");
        family_id = getIntent().getStringExtra("family_id");
        haveScanner = getIntent().getBooleanExtra("haveScanner", false);
        fromDevices = getIntent().getBooleanExtra("from_devices", false);
    }

    @OnClick({R.id.title_back, R.id.title_right_tv, R.id.family_invite, R.id.family_invite_create, R.id.family_invite_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.title_right_tv://跳过
                startActivity(new Intent(this, FamilyDetailActivity.class)
                        .putExtra("family_id", family_id).putExtra("new_family", true).putExtra("haveScanner", haveScanner).putExtra("from_devices", fromDevices));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
                break;
            case R.id.family_invite://邀请新成员
                startActivityForResult(new Intent(this, SearchActivity.class)
                        .putExtra("family_id", family_id), 1);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.family_invite_create://创建新成员
                startActivityForResult(new Intent(this, FamilyMemberActivity.class)
                        .putExtra("family_id", family_id), 2);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                Constant.isCreateMem = true;
                break;
            case R.id.family_invite_save://下一步
                startActivity(new Intent(this, FamilyDetailActivity.class)
                        .putExtra("family_id", family_id).putExtra("new_family", true).putExtra("haveScanner", haveScanner).putExtra("from_devices", fromDevices));
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 结果码不等于取消时候
        if (resultCode != this.RESULT_CANCELED) {
            switch (resultCode){
                case 10://邀请和创建返回的数据
                    String member_avatar = data.getStringExtra("member_avatar");
                    String member_name = data.getStringExtra("member_name");
                    avatarList.add(member_avatar);
                    nameList.add(member_name);
                    InviteAndCreateAdapter inviteAndCreateAdapter = new InviteAndCreateAdapter(this, avatarList, nameList);
                    familyInviteLv.setAdapter(inviteAndCreateAdapter);
                    inviteAndCreateAdapter.notifyDataSetInvalidated();
                    LogUtils.e("onActivityResult: " + nameList.size() + nameList.get(0));
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
