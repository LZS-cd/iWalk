package com.example.boge.laonianbao.Receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.boge.laonianbao.PersonInfo.PersonInfo;
import com.example.boge.laonianbao.getAndPost.GetAndPost;
import com.example.boge.laonianbao.map.map;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Boge on 2017/5/29.
 */

public class LocationReceiver extends Service {
    LocationClient locationClient;
    double latitude,longitude;

    @Override
    public void onCreate() {
        super.onCreate();
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setOpenGps(true);
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5分钟
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);
        MyListener bdLocationListener = new MyListener();
        locationClient.registerLocationListener(bdLocationListener);
        locationClient.start();
        locationClient.requestLocation();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
            String url="http://120.25.225.163:8080/ServletTest/UploadLocation?latitude="+latitude
                    +"&longitude="+longitude+"&account="+ PersonInfo.getPersonInfo().getAccount();
                String result=GetAndPost.get(url,"GET",null);
            Log.i("LocationReceiver", PersonInfo.getPersonInfo().getAccount()+":"+result);
            }
        };
        timer.schedule(timerTask,2000,1000*60*5);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LocationReceiver", "onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyListener implements BDLocationListener {



        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation == null)
                return;
            latitude=bdLocation.getLatitude();
            longitude=bdLocation.getLongitude();
            Log.i("LocationReceiver",latitude+" : "+longitude);

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }
}
