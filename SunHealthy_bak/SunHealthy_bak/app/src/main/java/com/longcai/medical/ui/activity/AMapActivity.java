package com.longcai.medical.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.longcai.medical.R;
import com.longcai.medical.gloabe.Constant;
import com.longcai.medical.utils.AppManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AMapActivity extends AppCompatActivity implements GeocodeSearch.OnGeocodeSearchListener {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.title_right_tv)
    TextView titleRightTv;
    @BindView(R.id.bmapView)
    MapView amapView;

    private UiSettings mUiSettings;
    private CameraUpdate mUpdate;
    private String latitude;
    private String longitude;

    private double mLongitude;
    private double mLatitude;

    private String manor_name;
    private AMap aMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_amap_location);
        ButterKnife.bind(this);
        amapView.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        longitude = intent.getStringExtra(Constant.LONGITUDE);
        latitude = intent.getStringExtra(Constant.LATITUDE);
        manor_name = intent.getStringExtra(Constant.NAME);
        if (!TextUtils.isEmpty(longitude) && !TextUtils.isEmpty(latitude)){
            mLongitude = Double.valueOf(longitude);
            mLatitude = Double.valueOf(latitude);
        }
        // 经纬度坐标
        titleTv.setText(manor_name);
        titleRightTv.setVisibility(View.GONE);

        if (aMap == null) {
            aMap = amapView.getMap();
        }
//        LatLng latLng = new LatLng(mLatitude, mLongitude);
        latLng = new LatLng(mLatitude, mLongitude);
//        try {
//            CoordinateConverter converter = new CoordinateConverter(this);
////            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.from(CoordinateConverter.CoordType.GPS);
//            converter.coord(new DPoint(mLatitude, mLongitude));
////            converter.coord(new DPoint(37.7041466, 112.7116002));
//            DPoint destLatLng = converter.convert();
//            latLng = new LatLng(destLatLng.getLatitude(), destLatLng.getLongitude());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUpdate = CameraUpdateFactory.newCameraPosition(
                new CameraPosition(latLng, 15, 0, 30));
        aMap.moveCamera(mUpdate);
        GeocodeSearch geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        LatLonPoint latLonPoint = new LatLonPoint(mLatitude, mLongitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);
        super.onDestroy();
        //在activity执行onDestroy时执行bmapView.onDestroy()，实现地图生命周期管理
        amapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行bmapView. onResume ()，实现地图生命周期管理
        amapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行bmapView. onPause ()，实现地图生命周期管理
        amapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        amapView.onSaveInstanceState(outState);
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

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 1000) {
            RegeocodeAddress regeocodeAddress = regeocodeResult.getRegeocodeAddress();
            String formatAddress = regeocodeAddress.getFormatAddress();
            aMap.addMarker(new MarkerOptions().position(latLng).title(formatAddress).snippet(""));
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

}
