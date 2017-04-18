package com.example.boge.laonianbao.register;

import android.os.AsyncTask;
import android.widget.TextView;

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
    TextView textView;
    private String param;
    public RegisterAsynTask(String param,TextView textView)
    {
        this.textView=textView;
        this.param=param;
    }
    @Override
    protected String doInBackground(String... params) {
        String response="";
        HttpURLConnection connection=null;
        try {
            URL url=new URL(params[0]);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoOutput(true);
            DataOutputStream out=new DataOutputStream(connection.getOutputStream());
            out.writeBytes(param);
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

    textView.setText(result);

    }
}
