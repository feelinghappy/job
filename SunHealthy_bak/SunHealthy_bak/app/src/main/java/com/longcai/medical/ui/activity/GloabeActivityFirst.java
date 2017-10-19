package com.longcai.medical.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.ui.BaseActivity;
import com.longcai.medical.ui.fragment.RobotFg;


public class GloabeActivityFirst extends BaseActivity {
    private static final String TAG = "GloabeActivityFirst";
    private String fgMark;
    private Fragment fragment;
    FragmentTransaction ft;
    FragmentManager fm;
    private boolean refreshRobotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题

        initView();
        initData();

    }
    public void initView() {
        setContentView(R.layout.activity_gloabe);

    }

    public void initData() {
        fgMark = getIntent().getStringExtra(Constant.FRAGMENT_MARK);
        fgMark = Constant.ROBOT_FG;
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (fgMark) {

            /*case Constant.FAMILY_LIST:
                fragment = new FamilyFg();
                Constant.fragmentTag = Constant.FamilyFgTag;
                break;*/

            case Constant.ROBOT_FG:
                fragment = new RobotFg();
                Constant.fragmentTag = Constant.RobotFgTag;
                break;
        }
        ft.replace(R.id.gloabe_fm, fragment, Constant.fragmentTag);
        ft.commit();
    }



    Fragment f;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult: resultCode  " + resultCode);
        switch (resultCode) {
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_suc:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_fail:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_suc:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_watchScam_unBuild_fail:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_unBuild_suc:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_unBuild_fail:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.GF_ROBOT_Fg_REsult_CODE_robotScam_suc:
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                break;
            case Constant.Family_List_To_Message_CODE:
                f = fm.findFragmentByTag(Constant.FamilyFgTag);
                break;
        }
        if (f != null) {
            f.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        refreshRobotList = getIntent().getBooleanExtra("refreshRobotList", false);
        Log.d("test", refreshRobotList + "");
        try {
            if (refreshRobotList) {
                f = fm.findFragmentByTag(Constant.RobotFgTag);
                ((RobotFg)f).refreshRobotList();
                ((RobotFg)f).getHethFromService();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (refreshRobotList) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("source", 1);
            } else {
            }
            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
