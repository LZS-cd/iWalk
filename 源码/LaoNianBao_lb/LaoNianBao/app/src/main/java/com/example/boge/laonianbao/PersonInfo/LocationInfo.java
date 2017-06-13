package com.example.boge.laonianbao.PersonInfo;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Boge on 2017/5/28.
 */

public class LocationInfo {
    private static LocationInfo locationInfo=null;
    private LatLng home;
    private int radius;
    private boolean log;
    double zoomLevel;
    private LocationInfo(){

    }
    public static LocationInfo getLocationInfo(){
        if(locationInfo == null){
            locationInfo=new LocationInfo();
        }
        return locationInfo;
    }
    public void setLog(boolean log){
        this.log=log;
    }
    public boolean isLog(){
        return log;
    }
    public void setHome(LatLng home){
        this.home=home;
    }
    public LatLng getHome(){
        return home;
    }
    public void  setRadius(int radius){
        this.radius=radius;
    }
    public int getRadius(){
        return radius;
    }
    public void setZoomLevel(double zoomLevel){
        this.zoomLevel=zoomLevel;
    }
    public double getZoomLevel(){
        return this.zoomLevel;
    }
}
