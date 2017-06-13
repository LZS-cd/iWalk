package com.example.boge.laonianbao.weather;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Boge on 2017/4/16.
 */

public class WeatherAsyntask extends AsyncTask<String,Integer,String> {

    TextView textView;
    HashMap<String,String > hashMap;
    WeatherAsyntask(TextView textView,HashMap<String,String> hashMap)
    {
        this.textView=textView;
        this.hashMap=hashMap;
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

        String gets;
        JSONTokener jsonTokener1,jsonTokener2;
        JSONObject jsonObject1,jsonObject2;
        String temp;//当前实况温度
        String wind_direnction;//当前风向
        String wind_strength;//当前风力
        String humidity;//当前湿度
        String temperature;//当前温度
        String weather;//当前天气
        String wind;//风
        String dress_index;//穿衣指数
        String dress_advice;//穿衣建议
        String uv_index;//紫外线强度
        String future_day1_temperature;//未来第一天温度
        String future_day1_weather;//未来第一天天气
        String future_day1_wind;//未来第一天风力
        String json_result="";
        try {
            jsonTokener1=new JSONTokener(result);
            jsonObject1=new JSONObject(jsonTokener1);
            gets=jsonObject1.getString("resultcode");
            if(gets==null)
                return;
            if(gets!=null&&gets.equals("200"))
            {
                gets=jsonObject1.getString("result");
                jsonTokener1=new JSONTokener(gets);
                jsonObject1=new JSONObject(jsonTokener1);
                String sk=jsonObject1.getString("sk");///////////sk
                jsonTokener2=new JSONTokener(sk);
                jsonObject2=new JSONObject(jsonTokener2);
                temp=jsonObject2.getString("temp");
                wind_direnction=jsonObject2.getString("wind_direction");
                wind_strength=jsonObject2.getString("wind_strength");
                humidity=jsonObject2.getString("humidity");
                String today=jsonObject1.getString("today");//////////////today
                jsonTokener2=new JSONTokener(today);
                jsonObject2=new JSONObject(jsonTokener2);
                temperature=jsonObject2.getString("temperature");
                weather=jsonObject2.getString("weather");
                wind=jsonObject2.getString("wind");
                dress_index=jsonObject2.getString("dressing_index");
                dress_advice=jsonObject2.getString("dressing_advice");
                uv_index=jsonObject2.getString("uv_index");
                JSONArray jsonArray=jsonObject1.getJSONArray("future");///////////////future
                JSONObject c=jsonArray.getJSONObject(0);
                future_day1_temperature=c.getString("temperature");
                future_day1_weather=c.getString("weather");
                future_day1_wind=c.getString("wind");

                textView.setText(temperature);

                json_result="今天温度："+temperature+"\n"+
                        "今天天气："+weather+"\n"+
                        "今天风："+wind+"\n"+
                        "当前温度："+temp+"\n"+
                        "当前风向："+wind_direnction+"\n"+
                        "当前风力："+wind_strength+"\n"+
                        "紫外线强度"+uv_index+"\n"+
                        "当前湿度："+humidity+"\n"+
                        "穿衣指数:"+dress_index+"\n"+
                        "穿衣建议："+dress_advice+"\n"+
                        "明天温度:"+future_day1_temperature+"\n"+
                        "明天天气:"+future_day1_weather+"\n"+
                        "明天风力:"+future_day1_wind+"\n";
                hashMap.put("temp",temp);
                hashMap.put("weather",weather);
                hashMap.put("dress_advice",dress_advice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
