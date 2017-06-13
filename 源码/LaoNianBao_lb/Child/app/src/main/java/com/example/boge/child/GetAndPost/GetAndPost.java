package com.example.boge.child.GetAndPost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Boge on 2017/5/13.
 */

public class GetAndPost {
    public static String get(String murl,String way,String contest)
    {
        HttpURLConnection connection=null;
        String response="";
        try{
            URL url=new URL(murl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod(way);
            connection.setConnectTimeout(8000);//网络连接时间
            connection.setReadTimeout(8000);//网络报文收发时间
            if(way.equals("POST"))
            {
                connection.setDoOutput(true);
                DataOutputStream out=new DataOutputStream(connection.getOutputStream());
                out.writeBytes(contest);
            }
            connection.connect();
            if(connection.getResponseCode()!=200)
            {
                return "网络错误";
            }
            InputStream in=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            String result;
            while((result=reader.readLine())!=null)
            {
                response+=result;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
