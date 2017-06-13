package com.example.boge.laonianbao.register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.boge.laonianbao.getAndPost.GetAndPost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Boge on 2017/3/27.
 */

public class RegisterAsynTask extends AsyncTask<String,Integer,String> {
    Handler handler=null;
    private String param;
    RegisterAsynTask(String param,Handler handler)
    {
        this.handler=handler;
        this.param=param;
    }
    @Override
    protected String doInBackground(String... params) {
        return GetAndPost.get(params[0],"POST",param);
    }
    @Override
    protected void onPostExecute(String result)
    {
        Message message=handler.obtainMessage(Register.SUBMIT);
        Bundle bundle=new Bundle();
        bundle.putString("result",result);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
