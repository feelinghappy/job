package com.hrg.anew;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class ReportActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.report_layout);
        hideNavigationBar();
        hideVirtualKey();
        ImageView imgback = (ImageView)findViewById(R.id.report_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //通过findViewById获得RadioGroup对象  
        RadioGroup raGrouphis =(RadioGroup)findViewById(R.id.history);
        //添加事件监听器  
        raGrouphis.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(checkedId == R.id.btn_0)
                {
                    LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.xueya));
                }
                else if(checkedId == R.id.btn_1)
                {
                    LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.heart));
                }
                else if(checkedId == R.id.btn_2)
                {
                    LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.sport));
                }
                else if(checkedId == R.id.btn_3)
                {
                    LinearLayout layout=(LinearLayout) findViewById(R.id.linearLayoutreport);//需要设置linearlayout的id为layout
                    layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.sleep));
                }

            }
        }
    );
    }
    private  void hideNavigationBar(){
        View decorView = getWindow().getDecorView();
        int uiOpions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOpions);

    }
    /**
     * 隐藏Android底部的虚拟按键
     */
    private void hideVirtualKey(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
    }
}