package com.example.boge.child.olderInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Boge on 2017/5/29.
 */

public class OlderInfo {
    private static OlderInfo olderInfo=null;
    Map<String,String> olderList;
    private OlderInfo(){
        olderList=new HashMap<String,String >();
    }
    public static OlderInfo  getOlderInfo(){
        if(olderInfo == null ){
            olderInfo=new OlderInfo();
        }
        return olderInfo;
    }
    public void addOlder(String key,String account){
        olderList.put(key,account);
    }
    public Map<String,String> getOlderList(){
        return olderList;
    }
}
