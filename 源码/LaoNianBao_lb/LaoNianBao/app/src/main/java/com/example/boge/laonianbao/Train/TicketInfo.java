package com.example.boge.laonianbao.Train;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class TicketInfo extends AppCompatActivity {
    private String from;
    private String to;
    private String date;
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    public static final String APPKEY = "a658835596fd4b6411672d127532ab54";
    private TextView pre;
    private TextView now;
    private TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticketinfo);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        date = intent.getStringExtra("date");
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        pre = (TextView) findViewById(R.id.pre);
        now = (TextView) findViewById(R.id.now);
        next = (TextView) findViewById(R.id.next);

        pre.setClickable(true);
        next.setClickable(true);
        ticket();
    }

    public void ticket() {
        String result = null;
        String url = "http://apis.juhe.cn/train/s2swithprice";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("start", from);//出发站
        params.put("end", to);//终点站
        params.put("date", date);//时间    格式：2016-12-12，默认明天
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "json");//返回数据的格式,xml或json，默认json

        try {
            result = net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                showInfo(object.get("result").toString());
            } else {
                Toast.makeText(TicketInfo.this, object.get("error_code") + ":" + object.get("reason"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    @NonNull
    public static String urlencode(Map<String, String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private void showInfo(String result) {


        TextView fromandto = (TextView) findViewById(R.id.fromandto);
        fromandto.setText(from + "-" + to);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.trainlist);

        JSONArray trainList = JSONObject.fromObject(result).getJSONArray("list");
        String train_no;
        String start_station;
        String end_station;
        String start_time;
        String end_time;
        String run_time;
        String price_type = null;
        String price = null;
        for (int i = 0; i < trainList.length(); i++) {
            JSONObject trainInfo = trainList.getJSONObject(i);
            train_no = trainInfo.getString("train_no");
            start_station = trainInfo.getString("start_station");
            end_station = trainInfo.getString("end_station");
            start_time = trainInfo.getString("start_time");
            end_time = trainInfo.getString("end_time");
            run_time = trainInfo.getString("run_time");

            JSONArray priceList = trainInfo.getJSONArray("price_list");

            for (int j = 0; j < priceList.length(); j++) {
                JSONObject priceInfo = priceList.getJSONObject(j);
                price_type = priceInfo.getString("price_type");
                price = priceInfo.getString("price");

            }

            OneTrain oneTrain = new OneTrain(TicketInfo.this, linearLayout);
            oneTrain.SetText(start_time, start_station, train_no, "￥" + price, end_time, end_station, run_time, price_type);

        }
    }
}
