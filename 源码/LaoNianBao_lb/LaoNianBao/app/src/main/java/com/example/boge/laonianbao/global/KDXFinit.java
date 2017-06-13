package com.example.boge.laonianbao.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import cn.jpush.sms.SMSSDK;

/**
 * Created by Boge on 2017/4/15.
 */

public class KDXFinit extends Application {
    private  static KDXFinit App;
    private static Activity mactivity;
    public void onCreate() {

        SpeechUtility.createUtility(KDXFinit.this,  SpeechConstant.APPID + "=58beb639");
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(60*1000);
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate();
        App=this;
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

                    mactivity=activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
    public static Context getAppContext() {
        return App;
    }

    public static Resources getAppResources() {
        return App.getResources();
    }

    public static Activity getActivity(){
        return mactivity;
    }
}
