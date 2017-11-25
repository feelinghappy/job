package com.hrg.anew;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static com.baidu.mapapi.map.MapStatusUpdateFactory.*;

public class LocationActivity extends Activity {
    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private double x,y;
    private LatLng point;
    private Bundle bundle;
    private boolean isFirstLocate = true;
    private BitmapDescriptor mCurrentMarker;
    private View mPop;
    private  TextView address;
    private  String lng;
    private String lat;
    private String strDetails_Data;
    private  static final int UPDATE_DETAILS_DATA = 40;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        lng = getIntent().getStringExtra("lngData");
        lat = getIntent().getStringExtra("latData");
        //隐藏状态栏
        double dlng = Double.valueOf(lng).doubleValue();
        double dlat = Double.valueOf(lat).doubleValue();
        point = new LatLng(dlat,dlng);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        SDKInitializer.initialize(getApplicationContext());//初始化百度地图
        // 初始化MKSearch  
        setContentView(R.layout.bdmap);

        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        BDLocation location = new BDLocation();
        location.setLongitude(x);
        location.setLatitude(y);
        navigateTo();
        reverseGeoCode(point);
        hideNavigationBar();
        hideVirtualKey();
        ImageView imgback = (ImageView)findViewById(R.id.position_back);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        initUI();
    }

    public void initUI() {

        LayoutInflater mInflater = getLayoutInflater();
        mPop = (View) mInflater.inflate(R.layout.address_details, null, false);
        address = (TextView)mPop.findViewById(R.id.address_details_text);
    }
    /**
     * 反地理编码得到地址信息
     */
    private void reverseGeoCode(LatLng latLng) {
        // 创建地理编码检索实例

        GeoCoder geoCoder = GeoCoder.newInstance();
        //
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            // 反地理编码查询结果回调函数
            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    reverseGeoCode(point);
                }

                if(result.getAddress()!=null) {

      /*              InfoWindow mInfoWindow = new InfoWindow(mPop, point, -47);
                    address.setText(result.getAddress());
                    mBaiduMap.showInfoWindow(mInfoWindow);
                    MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(point);
                    mBaiduMap.setMapStatus(update);*/
                    strDetails_Data = result.getAddress();
                    Message msg = new Message();
                    msg.what =UPDATE_DETAILS_DATA;
                    handler.sendMessage(msg);



                }

            }

            // 地理编码查询结果回调函数
            @Override
            public void onGetGeoCodeResult(GeoCodeResult result) {
                if (result == null
                        || result.error != SearchResult.ERRORNO.NO_ERROR) {
                    // 没有检测到结果
                }
            }
        };
        // 设置地理编码检索监听者
        geoCoder.setOnGetGeoCodeResultListener(listener);
        //
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
        // 释放地理编码检索实例
        // geoCoder.destroy();
    }
	
	   private void navigateTo() {
        //LatLng point = new LatLng(location.getLongitude(), location.getLatitude());
        MapStatus mMapStatus = new MapStatus.Builder().target(point).build();
        MapStatusUpdate update = newMapStatus(mMapStatus);
        mBaiduMap.setMapStatus(update);
//构建Marker图标
       BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_geo);

//构建MarkerOption，用于在地图上添加Marker

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

//在地图上添加Marker，并显示

        mBaiduMap.addOverlay(option);
        //定义文字所显示的坐标点
        //实例化一个地理编码查询对象  
        reverseGeoCode(point);
    }
	
    @Override
    protected void onStop()
    {
        super.onStop();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
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

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_DETAILS_DATA:
                   try {
                        Log.e("UPDATE_DETAILS_DATA","UPDATE_DETAILS_DATA");
                       OverlayOptions textOption = new TextOptions()
                               .bgColor(0xAA4284E2)
                               .fontSize(24)
                               .fontColor(0xFFFFFFFF)
                               .text(strDetails_Data)
                               .rotate(0)
                               .position(point);

//在地图上添加该文字对象并显示
                       mBaiduMap.addOverlay(textOption);


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("UPDATE_BLOOD_DAY", e.toString() );
                    }
                    break;
            }
        }
    };
}