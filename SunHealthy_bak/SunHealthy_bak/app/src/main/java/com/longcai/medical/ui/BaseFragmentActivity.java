package com.longcai.medical.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.longcai.medical.R;
import com.longcai.medical.utils.AppManager;
import com.zcx.helper.activity.AppV4Activity;

/**
 * Created by Administrator on 4/29/2016.
 */
public class BaseFragmentActivity extends AppV4Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
       // BoundViewHelper.boundView(this, MyApplication.ScaleScreenHelper.loadView((ViewGroup) getWindow().getDecorView()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    public void onBack(View view){
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            this.finish();  //finish当前activity
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
