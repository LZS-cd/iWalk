package com.example.boge.child.login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.boge.child.GetAndPost.GetAndPost;

/**
 * Created by Boge on 2017/5/13.
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
