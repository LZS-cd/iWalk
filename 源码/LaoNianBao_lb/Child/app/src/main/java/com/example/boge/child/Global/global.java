package com.example.boge.child.Global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;

import cn.jpush.sms.SMSSDK;

/**
 * Created by Boge on 2017/5/13.
 */

public class global extends Application{
    private  static global App;
    private static Activity mactivity;
    public void onCreate()
    {
        SDKInitializer.initialize(getApplicationContext());
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(60*1000);
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
