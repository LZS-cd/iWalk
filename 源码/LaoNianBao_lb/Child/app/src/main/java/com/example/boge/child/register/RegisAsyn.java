package com.example.boge.child.register;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.boge.child.GetAndPost.GetAndPost;
import com.example.boge.child.login.Login;

/**
 * Created by Boge on 2017/5/22.
 */

public class RegisAsyn extends AsyncTask<String,Integer,String> {
    Handler handler;
    String param;
    public RegisAsyn(Handler handler,String param)
    {
        this.handler=handler;
        this.param=param;
    }
    @Override
    protected String doInBackground(String... params) {
        return GetAndPost.get(params[0],"POST",param);
    }
    protected void onPostExecute(String result)
    {
        Message message=handler.obtainMessage(register.SUBMIT);
        Bundle bundle=new Bundle();
        bundle.putString("result",result);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
