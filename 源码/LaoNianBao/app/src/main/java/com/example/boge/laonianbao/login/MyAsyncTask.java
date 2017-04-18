package com.example.boge.laonianbao.login;


import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.Buffer;
import java.util.concurrent.TimeoutException;

/**
 * Created by Boge on 2017/3/23.
 */

public class MyAsyncTask extends AsyncTask<String,Integer,String> {
    private TextView text;


    public MyAsyncTask(TextView text)
    {
        this.text=text;
    }
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection=null;
        String response="";
        try{
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);//网络连接时间
            connection.setReadTimeout(8000);//网络报文收发时间
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
    @Override
    protected void onPostExecute(String result) {
        text.setText(result);
    }




}
