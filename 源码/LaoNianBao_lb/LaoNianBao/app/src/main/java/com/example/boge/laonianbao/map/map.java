package com.example.boge.laonianbao.map;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.boge.laonianbao.PersonInfo.LocationInfo;
import com.example.boge.laonianbao.PersonInfo.PersonInfo;
import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.map.MyOrientationListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class map extends AppCompatActivity implements BaiduMap.OnMapClickListener,OnGetRoutePlanResultListener,BaiduMap.OnMarkerClickListener {
    MapView mapView;
    BaiduMap baiduMap;
    LocationClient locationClient;
    BDLocationListener bdLocationListener;
    MyLocationConfiguration myLocationConfiguration;
    BitmapDescriptor bitmapDescriptor_home;
    boolean click=false;
    double home_latitude;
    double home_longitude;
    LatLng home,now;
    int  radius=600;
    MyOrientationListener myOrientationListener;
    float direction;
    boolean finishElec=false;
    //导航
    RoutePlanSearch mSearch;
    WalkingRouteLine route;
    PlanNode stNode,enNode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Resources resources =getResources();

        ImageView icon = new ImageView(this);

        Drawable drawable = resources.getDrawable(R.drawable.add);
        icon.setImageDrawable(drawable);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        ImageView itemIcon1 = new ImageView(this);
        Drawable drawable1 = resources.getDrawable(R.drawable.house);
        itemIcon1.setImageDrawable(drawable1);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon1).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home=null;
                click=true;
            }
        });

        ImageView itemIcon2 = new ImageView(this);
        Drawable drawable2 = resources.getDrawable(R.drawable.route);
        itemIcon2.setImageDrawable(drawable2);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon2).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home == null){
                    Toast.makeText(map.this,"请确定家的位置",Toast.LENGTH_SHORT).show();
                    return;
                }
                planRoute();
            }
        });
        ImageView itemIcon3 = new ImageView(this);
        Drawable drawable3 = resources.getDrawable(R.drawable.finish);
        itemIcon3.setImageDrawable(drawable3);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon3).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(home == null){
                    Toast.makeText(map.this,"请确定家的位置",Toast.LENGTH_SHORT).show();
                    return;
                }

                click=false;
                electriCircle();
                finishElec=true;
            }
        });
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button3)
                .addSubActionView(button2)
                .attachTo(actionButton)
                .build();


        mapView = (MapView) findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();   //成功加载
        mapView.removeViewAt(1);
        mapView.removeViewAt(2);
        mapView.showZoomControls(false);
        mSearch=RoutePlanSearch.newInstance();//加载地图搜寻
        mSearch.setOnGetRoutePlanResultListener(this);

        myOrientationListener =new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                direction=x;
            }
        });


        bitmapDescriptor_home= BitmapDescriptorFactory.fromResource(R.drawable.home);//家的点标
        baiduMap.setOnMapClickListener(this);

        baiduMap.setIndoorEnable(true);//室内导航
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);//设置 地图显示的初始化级别
        baiduMap.setMapStatus(mapStatusUpdate);

        baiduMap.setMyLocationEnabled(true);//定位系统

        myLocationConfiguration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
        baiduMap.setMyLocationConfiguration(myLocationConfiguration);
        locationClient=new LocationClient(getApplicationContext());

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setOpenGps(true);
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);

        bdLocationListener=new MyListener();
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
        locationClient.requestLocation();
        LocationInfo locationInfo=LocationInfo.getLocationInfo();
        if(locationInfo.isLog()){
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom((int)locationInfo.getZoomLevel()).build()));
            this.radius=locationInfo.getRadius();
            this.home=locationInfo.getHome();
            MarkerOptions options = new MarkerOptions().position(home)
                    .icon(bitmapDescriptor_home);
            // 在地图上添加Marker，并显示mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() mark点击事件
            baiduMap.addOverlay(options);
            electriCircle();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(click) {
            home_latitude = latLng.latitude;
            home_longitude = latLng.longitude;
            baiduMap.clear();//清除地图图层
            // 定义Maker坐标点
            home = new LatLng(home_latitude, home_longitude);
            // 构建MarkerOption，用于在地图上添加Marker
            MarkerOptions options = new MarkerOptions().position(home)
                    .icon(bitmapDescriptor_home);
            // 在地图上添加Marker，并显示                                      mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() mark点击事件
            baiduMap.addOverlay(options);
        }
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        //Toast.makeText(com.example.boge.laonianbao.map.map.this,"点击家一下",Toast.LENGTH_SHORT).show();
        return false;
    }


    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            Log.d("baiduMap", "起终点或途经点地址有岐义");
            return;
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.PERMISSION_UNFINISHED) {
            //权限鉴定未完成则再次尝试
            Log.d("baiduMap", "权限鉴定未完成,再次尝试");
            //////////////////////// startSearch(loc_start,loc_end);
            return;
        }
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            return;
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            route = walkingRouteResult.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
            try {
                overlay.setData(route);
                overlay.addToMap();
                overlay.zoomToSpan();
            }catch (Exception e){
                Toast.makeText(this, "路径规划异常", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    class MyListener implements BDLocationListener {

        boolean firstLocat=true;
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if(bdLocation==null||mapView==null)
                return ;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(direction).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            now=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            if(home != null) {
                double length = DistanceUtil.getDistance(home, now);
                if(length>radius){
                    Toast.makeText(map.this,"已经超过最大距离",Toast.LENGTH_SHORT).show();
                }

            }
            if(firstLocat)
            {
                //Toast.makeText(map.this,"firstLocat",Toast.LENGTH_SHORT).show();
                //地理坐标基本数据结构
                firstLocat=false;
                LatLng latLng=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                //改变地图状态
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng);//.zoom(18.0f);//MapStatusUpdateFactory.newMapStatus(builder.build())
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.unRegisterLocationListener(bdLocationListener);
        //取消位置提醒
        locationClient.stop();
    }
    void electriCircle()
    {
        /**
         *CircleOptions:创建圆的选项
         *   fillColor(int color):设置圆填充颜色
         *   center(LatLng center):设置圆心坐标
         *   stroke(Stroke stroke):设置圆边框信息
         *
         *Stroke:边框类，可以给圆、多边形设置一个边框
         *   Stroke(int strokeWidth, int color):
         *      color:边框的颜色
         *      strokeWidth:边框的宽度， 默认为 5， 单位：像素
         * */

        OverlayOptions ooCircle = new CircleOptions().fillColor(0x000000FF)
                .center(home).stroke(new Stroke(5, 0xAA000000))
                .radius(radius);//1400米
        baiduMap.addOverlay(ooCircle);

        //LatLng llDot = new LatLng(home_latitude, home_latitude);
        //OverlayOptions ooDot = new DotOptions().center(llDot).radius(6)
        //        .color(0xFF0000FF);
        //baiduMap.addOverlay(ooDot);
    }
    @Override
    protected void onStart() {
        super.onStart();
        myOrientationListener.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        myOrientationListener.stop();
    }
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {
        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public int getLineColor() {
            //红色的路径
            return Color.RED;
        }
        @Override
        public BitmapDescriptor getStartMarker() {
            //自定义的起点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }
        @Override
        public BitmapDescriptor getTerminalMarker() {
            //自定义的终点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.home);
        }
    }
    public void planRoute()
    {
        stNode=PlanNode.withLocation(now);
        enNode=PlanNode.withLocation(home);
        mSearch.walkingSearch((new WalkingRoutePlanOption())
                .from(stNode)
                .to(enNode));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            if(finishElec){//完成绘制，保存下来
            LocationInfo locationInfo=LocationInfo.getLocationInfo();
            locationInfo.setLog(true);
            locationInfo.setHome(home);
            locationInfo.setRadius(radius);
            locationInfo.setZoomLevel(baiduMap.getMapStatus().zoom);
            }
            return super.onKeyDown(keyCode, event);
        }

        return false;

    }
}

