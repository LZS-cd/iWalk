package com.example.boge.child;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.boge.child.GetAndPost.GetAndPost;
import com.example.boge.child.addParent.AddParent;
import com.example.boge.child.addParent.PersonInfo;
import com.example.boge.child.map.MyOrientationListener;
import com.example.boge.child.olderInfo.OlderInfo;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class MainActivity extends AppCompatActivity implements BaiduMap.OnMarkerClickListener {
    MapView mapView;
    BaiduMap baiduMap;
    LocationClient locationClient;
    BDLocationListener bdLocationListener;
    MyLocationConfiguration myLocationConfiguration;
    MyOrientationListener myOrientationListener;
    float direction;
    public static final int REQUEST_ADD=1;
    public static final int DO_NOTHING=2;
    public static final int DO_FINISH=3;
    private Timer timer;
    private TimerTask timerTask;
    private Map<String,String> oldList;
    private Vector<LatLng> locations;
    BitmapDescriptor bitmapDescriptor_home;
    private InfoWindow infoWindow;
    /**
     * 弹出窗口图层
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XGPushConfig.enableDebug(this, false);
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, PersonInfo.getPersonInfo().getAccount(), new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("XPush","注册成功 token:"+o);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.i("XPush","注册失败 "+"错误代码"+i+"错误原因:"+s);

            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("oWalk");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        startActivityForResult(new Intent(MainActivity.this, AddParent.class),REQUEST_ADD);
                }
                return true;
            }
        });
        mapView = (MapView) findViewById(R.id.bmapView);
        mapView.removeViewAt(1);
        mapView.removeViewAt(2);
        mapView.showZoomControls(false);
        baiduMap=mapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);//设置 地图显示的初始化级别
        baiduMap.setMapStatus(mapStatusUpdate);
        baiduMap.setOnMarkerClickListener(this);
        baiduMap.setMyLocationEnabled(true);//定位系统
        myLocationConfiguration=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
        baiduMap.setMyLocationConfiguration(myLocationConfiguration);
        locationClient=new LocationClient(getApplicationContext());
        bitmapDescriptor_home= BitmapDescriptorFactory.fromResource(R.drawable.parent);
        myOrientationListener =new MyOrientationListener(this);
        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                direction=x;
            }
        });

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setOpenGps(true);
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
        bdLocationListener=new MyListener();
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
        locationClient.requestLocation();
        timer=new Timer();
         timerTask=new TimerTask() {
            @Override
            public void run() {
                Log.i("Main","进入timertask");
                baiduMap.clear();
                for(String key:oldList.keySet()){
                     String url="http://120.25.225.163:8080/ServletTest/GetLocation?userAccount="+oldList.get(key);
                    String result= GetAndPost.get(url,"GET",null);
                    JSONTokener jsonTokener=new JSONTokener(result);
                    try {
                        JSONObject jsonObject=new JSONObject(jsonTokener);
                        LatLng latLng=new LatLng(jsonObject.getDouble("latitude"),jsonObject.getDouble("longitude"));
                        MarkerOptions options = new MarkerOptions().position(latLng)
                                .icon(bitmapDescriptor_home);
                        // 在地图上添加Marker，并显示                                      mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() mark点击事件
                        baiduMap.addOverlay(options);
                        Marker marker=(Marker) baiduMap.addOverlay(options);
                        Bundle bundle = new Bundle();
                        //info必须实现序列化接口
                        bundle.putSerializable("info",key);
                        marker.setExtraInfo(bundle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("Search");
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("Main","marker click");
        Bundle bundle=marker.getExtraInfo();
        String name=bundle.getString("info");
       LatLng latLng=marker.getPosition();
        View popview = getLayoutInflater().inflate(
                R.layout.parent_info, null);// 获取要转换的View资源
        TextView textView=(TextView)popview.findViewById(R.id.tv_name);
        textView.setText(name);
        final BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory
                .fromView(popview);
        infoWindow=new InfoWindow(bitmapDescriptor,latLng,-47, new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                baiduMap.hideInfoWindow();
            }
        });
        baiduMap.showInfoWindow(infoWindow);
        return false;
    }

    class MyListener implements BDLocationListener {


        boolean firstLocat = true;

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(direction).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (firstLocat) {
                Toast.makeText(MainActivity.this, "firstLocat", Toast.LENGTH_SHORT).show();
                //地理坐标基本数据结构
                firstLocat = false;
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                //改变地图状态
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(18.0f);//MapStatusUpdateFactory.newMapStatus(builder.build())
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (resultCode)
        {
            case DO_NOTHING:
                Toast.makeText(MainActivity.this,"Do_Nothing",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private BroadcastReceiver mBroadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals("Search")){//开始搜寻
                Log.i("Main","Main的广播");
                oldList= OlderInfo.getOlderInfo().getOlderList();
                timer.schedule(timerTask,1000,1000*60);
            }
        }
    };

}
