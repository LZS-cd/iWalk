package com.example.boge.laonianbao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.HealthTips.HealthTipsActivity;
import com.example.boge.laonianbao.Music.MusicActivity;
import com.example.boge.laonianbao.PersonInfo.PersonInfo;
import com.example.boge.laonianbao.Receiver.LocationReceiver;
import com.example.boge.laonianbao.Train.TrainTimes;
import com.example.boge.laonianbao.VoiceAndWord.WordToVoice;
import com.example.boge.laonianbao.help.HelpUse;
import com.example.boge.laonianbao.login.Login;
import com.example.boge.laonianbao.map.map;
import com.example.boge.laonianbao.news.NewsActivity;
import com.example.boge.laonianbao.wheelview.set.Setting;
import com.example.boge.laonianbao.step.activity.StepActivity;
import com.example.boge.laonianbao.weather.SearchWeather;
import com.example.boge.laonianbao.zpNUM.Data;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.util.Calendar;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    TextView weather;
    TextView train;
    TextView step;
    TextView healthTips;
    TextView news;
    TextView music;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XGPushConfig.enableDebug(this, false);
        Context context = getApplicationContext();
        Log.i("XPush", PersonInfo.getPersonInfo().getAccount());
        XGPushManager.registerPush(context, PersonInfo.getPersonInfo().getAccount(),new XGIOperateCallback() {
            @Override
            public void onSuccess(Object o, int i) {
                Log.i("XPush","注册成功 token:"+o);
            }

            @Override
            public void onFail(Object o, int i, String s) {
                Log.i("XPush","注册失败 "+"错误代码"+i+"错误原因:"+s);

            }
        });
        weather = (TextView) findViewById(R.id.weather);
        weather.setClickable(true);
        weather.setOnClickListener(this);

        train = (TextView) findViewById(R.id.train);
        train.setClickable(true);
        train.setOnClickListener(this);

        step = (TextView) findViewById(R.id.step);
        step.setClickable(true);
        step.setOnClickListener(this);

        healthTips = (TextView) findViewById(R.id.healthTips);
        healthTips.setClickable(true);
        healthTips.setOnClickListener(this);

        news = (TextView) findViewById(R.id.news);
        news.setClickable(true);
        news.setOnClickListener(this);

        music = (TextView) findViewById(R.id.MovieMusic);
        music.setClickable(true);
        music.setOnClickListener(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);    ///header view
        ImageButton imageButton = (ImageButton) headerView.findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        });
        startService(new Intent(MainActivity.this, LocationReceiver.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_selfInfo) {

        } else if (id == R.id.menu_timeSpeaker) {
            Calendar calendar = Calendar.getInstance();
            int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            String morningorafternoon;
            if (hourofday < 12) morningorafternoon = "上午";
            else morningorafternoon = "下午";
            String message = "现在是" + morningorafternoon + hour + "时" + minute + "分";

            WordToVoice wordToVoice = new WordToVoice(MainActivity.this, message,Data.getB());
            wordToVoice.GetVoiceFromWord();
        } else if (id == R.id.menu_sos) {
            //急救电话
            String phoneNum = "13547933655";
            try {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum)));
            } catch (SecurityException e) {
                Toast.makeText(MainActivity.this, "未获得通话权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.menu_call) {
            //子女电话
            String phoneNum = "13466933690";
            try {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum)));
            } catch (SecurityException e) {
                Toast.makeText(MainActivity.this, "未获得通话权限", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.menu_help) {
            startActivity(new Intent(MainActivity.this, HelpUse.class));
        } else if (id == R.id.menu_location) {
            startActivity(new Intent(MainActivity.this,map.class));
        } else if (id == R.id.menu_setting) {
            startActivity(new Intent(MainActivity.this, Setting.class));
        } else if (id == R.id.menu_exit) {
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather:
                startActivity(new Intent(MainActivity.this, SearchWeather.class));
                break;
            case R.id.train:
                startActivity(new Intent(MainActivity.this, TrainTimes.class));
                break;
            case R.id.step:
                startActivity(new Intent(MainActivity.this, StepActivity.class));
                break;
            case R.id.healthTips:
                startActivity(new Intent(MainActivity.this, HealthTipsActivity.class));
                break;
            case R.id.news:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
                break;
            case R.id.MovieMusic:
                startActivity(new Intent(MainActivity.this, MusicActivity.class));

        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
