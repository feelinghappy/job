package com.hrg.vtcall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.SyncReference;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    // 包裹滑动图片LinearLayout
    private ViewGroup main;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    private int currIndex = 0;// 当前页卡编号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Login();
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.item01, null));
        pageViews.add(inflater.inflate(R.layout.item02, null));
        pageViews.add(inflater.inflate(R.layout.item03, null));
        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup)inflater.inflate(R.layout.activity_main, null);
        viewPager = (ViewPager)findViewById(R.id.guidePages);
        group = (ViewGroup)findViewById(R.id.viewGroup);
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(12,12));
            imageViews[i] = imageView;
            if (i == 0) {
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(12,12);
            layoutParams.leftMargin = 30;
            layoutParams.bottomMargin = 64;
            group.addView(imageViews[i],layoutParams);
        }

        hideVirtualKey();
        hideNavigationBar();
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        TextView textViewButton = (TextView)findViewById(R.id.NavigateBack) ;
        textViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });
        ImageView ImgBack = (ImageView)findViewById(R.id.back);
        ImgBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
        });
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

    // 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            // TODO Auto-generated method stub
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).removeView(pageViews.get(arg1));
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            // TODO Auto-generated method stub
            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }
    }

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            Log.e("scrollStateChanged",arg0+"");


        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);

                if (arg0 != i) {
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);
                }
            }
        }
    }
    private static final int REQUEST_CODE = 0; // 请求码
    private WilddogAuth auth;
    private boolean isInlogin =false;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA };
    public void  Login()
    {
        if(isInlogin){return;}
        isInlogin = true;
        //获取Sync & Auth 对象
        auth = WilddogAuth.getInstance();
        //采用匿名登录方式认证
        //还可以选择其他登录方式
        //auth.signInWithEmailAndPassword();
        //auth.signInWithCredential();
        //auth.signInWithCustomToken();
        auth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //身份认证成功
                    String uid = auth.getCurrentUser().getUid();
                    //用户可以使用任意自定义节点来保存用户数据，但是不要使用 [wilddogVideo]节点存放私有数据
                    //以防和Video SDK 数据发生冲突
                    //本示例采用根节点下的[users] 节点作为用户列表存储节点
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(uid, true);
                    SyncReference userRef= WilddogSync.getInstance().getReference("users");
                    userRef.updateChildren(map);
                    userRef.child(uid).onDisconnect().removeValue();
                } else {
                    //处理失败
                    //throw new RuntimeException("auth 失败" + task.getException().getMessage());
                    Log.e("error",task.getException().getMessage());
                    Toast.makeText(MainActivity.this,"登录失败!",Toast.LENGTH_SHORT).show();
                    isInlogin = false;
                }
            }
        });
    }
}
