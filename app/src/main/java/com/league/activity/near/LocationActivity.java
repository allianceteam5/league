package com.league.activity.near;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.league.activity.BaseActivity;
import com.league.bean.LocationBean;
import com.league.utils.StoreUtils;
import com.mine.league.R;

public class LocationActivity extends BaseActivity implements OnGetGeoCoderResultListener {
    private ImageButton imgBack;
    private TextView nearTitle;
    private TextView nearLocation;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private ImageView request;
    boolean isFirstLoc = true; // 是否首次定位
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    /**
     * 当前地点击点
     */
    private LatLng currentPt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        imgBack = (ImageButton) findViewById(R.id.imgbtn_back);
        nearTitle = (TextView) findViewById(R.id.oh_title);
        nearLocation = (TextView) findViewById(R.id.location);
        nearTitle.setText("定位");
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                onBackPressed();
            }
        });
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        request = (ImageView) findViewById(R.id.request);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(600000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocClient.requestLocation();
            }
        });
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                currentPt = point;
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(currentPt));
//                updateMapState();
            }

            public boolean onMapPoiClick(MapPoi poi) {
                return false;
            }
        });
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(reverseGeoCodeResult.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(reverseGeoCodeResult
                .getLocation()));
        if (!TextUtils.isEmpty(reverseGeoCodeResult.getAddress()))
            Toast.makeText(LocationActivity.this, reverseGeoCodeResult.getAddress(),
                    Toast.LENGTH_LONG).show();
        StoreUtils.setLocationBean(new LocationBean(reverseGeoCodeResult.getLocation().latitude, reverseGeoCodeResult.getLocation().longitude, reverseGeoCodeResult.getAddressDetail().city));
        nearLocation.setText(reverseGeoCodeResult.getAddressDetail().city);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            mBaiduMap.clear();
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            mBaiduMap.addOverlay(new MarkerOptions().position(ll)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_marka)));
            if (!TextUtils.isEmpty(location.getAddrStr()))
                Toast.makeText(LocationActivity.this, location.getAddrStr(),
                        Toast.LENGTH_LONG).show();
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng llg = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llg);
                mBaiduMap.animateMapStatus(u);
            }
            nearLocation.setText(location.getCity());
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**
     * 更新地图状态显示面板
     */
    private void updateMapState() {
        String state = "";
        if (currentPt == null || mMapView == null) {
            state = "点击、长按、双击地图以获取经纬度和地图状态";
        } else {
            state = String.format("当前经度： %f 当前纬度：%f",
                    currentPt.longitude, currentPt.latitude);
        }
        state += "\n";
        MapStatus ms = mBaiduMap.getMapStatus();
        state += String.format(
                "zoom=%.1f rotate=%d overlook=%d",
                ms.zoom, (int) ms.rotate, (int) ms.overlook);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(currentPt));
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}
