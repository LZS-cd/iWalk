package com.example.boge.laonianbao.wheelview.set;

import android.app.AlertDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.login.Login;
import com.example.boge.laonianbao.wheelview.WheelView;
import com.example.boge.laonianbao.zpNUM.Data;

import java.util.Arrays;


public class Setting extends AppCompatActivity implements OnClickListener{
    private static final String TAG = "MainActivity";
    private String Sex="女";
    private static final String[] PLANETS = new String[]{"xiaoyan","xiaoyu","catherine","henry","vimary","vixy",
            "xiaoqi", "vixf","xiaomei" ,"xiaolin" ,"xiaorong","xiaoqian","xiaokun","xiaoqiang",
            "vixying","xiaoxin","nannan","vils"};
    private static final String[] lan = new String[]{"女青普通话","男青普通话",
            "女青英语","男青英语","女青英语",
            "女青普通话" ,"女青普通话",
            "男青普通话","女青粤语","女青台湾普通话",
            "女青四川话","女青东北话","男青河南话",
            "男青湖南话","女青陕西话","男童普通话",
            "女童普通话","男老普通话"};
    private TextView SexTv;
    private AlertDialog dialog;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_activity_main);
        SexTv= (TextView) findViewById(R.id.txt_Sex);
        findViewById(R.id.main_show_dialog_btn).setOnClickListener(this);

        seekBar = (SeekBar) findViewById(R.id.seekBar_brightness);

        int brightness;
        try {
            brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            seekBar.setProgress(brightness);
        } catch (Settings.SettingNotFoundException e) {
            Toast.makeText(Setting.this,"获取亮度失败", Toast.LENGTH_LONG);
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_show_dialog_btn:
                View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
                WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
                wv.setOffset(1);
                wv.setItems(Arrays.asList(lan));
                wv.setSeletion(1);
                wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        Sex=item;
                    }
                });

                dialog= new AlertDialog.Builder(this)
                        .setTitle("voice in Dialog")
                        .setView(outerView)
                        .show();
                TextView txtSure= (TextView) outerView.findViewById(R.id.txt_sure);
                TextView txtCancle= (TextView) outerView.findViewById(R.id.txt_cancel);
                txtSure.setOnClickListener(this);
                txtCancle.setOnClickListener(this);
                break;
            case R.id.txt_sure:
                String speaker = "";
                SexTv.setText(Sex);
                for(int i=0; i< lan.length; i++)
                {
                    if (lan[i].equals(Sex))
                    {
                        speaker = PLANETS[i];
                        break;
                    }
                }

                Data.setB(speaker);
                dialog.dismiss();
                break;
            case R.id.txt_cancel:
                dialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
