package com.example.boge.laonianbao.login;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boge.laonianbao.getAndPost.GetAndPost;

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
    private Handler handler=null;
    MyAsyncTask(Handler handler)
    {
        this.handler=handler;
    }
    @Override
    protected String doInBackground(String... params) {
        return GetAndPost.get(params[0],"GET",null);
    }
    protected void onPostExecute(String result)
    {
        Message message=handler.obtainMessage(Login.SUBMIT);
        Bundle bundle=new Bundle();
        bundle.putString("result",result);
        message.setData(bundle);
        handler.sendMessage(message);
    }


}
