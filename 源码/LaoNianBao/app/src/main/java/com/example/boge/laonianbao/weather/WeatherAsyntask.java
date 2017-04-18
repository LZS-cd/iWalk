package com.example.boge.laonianbao.weather;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Boge on 2017/4/16.
 */

public class WeatherAsyntask extends AsyncTask<String,Integer,String> {

    TextView textView;
    WeatherAsyntask(TextView textView)
    {
        this.textView=textView;
    }
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection httpURLConnection=null;
        try {
            URL url=new URL(params[0]);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()!=200)
            {
                return "连接失败";
            }
            else{
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String str;
                while((str=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(str).append(System.getProperty("line.separator"));
                }
                inputStream.close();//关闭输入流
                if(stringBuffer.toString().length()==0)
                    return null;
                return stringBuffer.toString().substring(0,
                        stringBuffer.toString().length() - System.getProperty("line.separator").length());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection!=null)
                httpURLConnection.disconnect();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result){

        JSONArray jsonArray=new JSONArray();
        textView.setText(result);
    }
}
