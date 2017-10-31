package com.hrg.family;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends  AppCompatActivity{
    private ViewPager viewPager;
    private ArrayList<View> pageViews;
    private ImageView imageView;
    private ImageView[] imageViews;
    // 包裹滑动图片LinearLayout
    private ViewGroup main;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    private int currIndex = 0;// 当前页卡编号
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.main);
        LayoutInflater inflater = getLayoutInflater();
        pageViews = new ArrayList<View>();
        pageViews.add(inflater.inflate(R.layout.item01, null));
        pageViews.add(inflater.inflate(R.layout.item02, null));
        pageViews.add(inflater.inflate(R.layout.item03, null));
        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup)inflater.inflate(R.layout.main, null);
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
            layoutParams.bottomMargin = 14;
            group.addView(imageViews[i],layoutParams);
        }

        hideVirtualKey();
        hideNavigationBar();
        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
        ImageView ImgBack = (ImageView)findViewById(R.id.back);
        ImgBack.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                /*Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                startActivity(intent);*/////LocationActivity ok
                /*Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);*/
                /*Intent intent  = new Intent(MainActivity.this,HealthActivity.class);
                startActivity(intent);*/
                /*Intent intent  = new Intent(MainActivity.this,VideoActivity.class);
                startActivity(intent);*/



            }
        });
        View pager1 = getLayoutInflater().inflate(R.layout.item01,null);
        ImageView ImgReport = (ImageView) pager1.findViewById(R.id.imgbtnreport);
        ImgReport.setOnClickListener(new View.OnClickListener()
        {
        @Override
            public void onClick(View v)
            {

                Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);
                finish();

            }
        });
        ImageView ImgLocation = (ImageView)pager1.findViewById(R.id.imgbtnposition);
        ImgLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                startActivity(intent);
                finish();


            }
        });

    }
    private  void init()
    {
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
        ImageView ImgCall = (ImageView)findViewById(R.id.imgbtncall);
       /* ImgCall.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);

            }
        });*/
        ImageView ImgReport = (ImageView)findViewById(R.id.imgbtnreport);
        ImgReport.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);

            }
        });
        ImageView ImgPosition = (ImageView)findViewById(R.id.imgbtnposition);
        ImgReport.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                startActivity(intent);

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
      /*      ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);*/

  /*          View v = new View(arg0.getContext());
            LayoutInflater inflater =
                    (LayoutInflater) arg0.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            int resId = 0;
            switch (arg1) {
                case 0:
                    resId = R.layout.item01;
                    v = inflater.inflate(R.layout.item01, null, false);
                    ImageView imgposition1 = (ImageView) v.findViewById(R.id.imgbtnposition);
                    imgposition1.setOnClickListener( new View.OnClickListener() {
                        public void onClick(View m) {
                            Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imghealth1 = (ImageView)v.findViewById(R.id.imgbtnreport);
                    imghealth1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgreport1 = (ImageView)v.findViewById(R.id.imgbtnhistory);
                    imgreport1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgcall = (ImageView)v.findViewById(R.id.imgbtncall);
                    imgcall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,VideoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;
                case 1:
                    resId = R.layout.item02;
                    break;
                case 2:
                    resId = R.layout.item03;
                    break;
            }

            ((ViewPager) arg0).addView(pageViews.get(arg1));
            return pageViews.get(arg1);*/
            switch (arg1) {
            case 0:
            ImageView imgcall0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
            imgcall0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent intent  = new Intent(MainActivity.this,VideoActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
                ImageView imgposition0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                imgposition0.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View m) {
                        Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                ImageView imghealth0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                imghealth0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(MainActivity.this,HealthActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                ImageView imgreport0 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                imgreport0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            break;
                case 1:
                    ImageView imgcall1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
                    imgcall1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent intent  = new Intent(MainActivity.this,VideoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgposition1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                    imgposition1.setOnClickListener( new View.OnClickListener() {
                        public void onClick(View m) {
                            Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imghealth1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                    imghealth1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,HealthActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgreport1 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                    imgreport1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;
                case 2:
                    ImageView imgcall2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtncall);
                    imgcall2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent intent  = new Intent(MainActivity.this,VideoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgposition2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnposition);
                    imgposition2.setOnClickListener( new View.OnClickListener() {
                        public void onClick(View m) {
                            Intent intent  = new Intent(MainActivity.this,LocationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imghealth2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnreport);
                    imghealth2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,HealthActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    ImageView imgreport2 = (ImageView) pageViews.get(arg1).findViewById(R.id.imgbtnhistory);
                    imgreport2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent  = new Intent(MainActivity.this,ReportActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    break;

             default:
             break;
            }
            ((ViewPager) arg0).addView(pageViews.get(arg1), 0);
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

}
