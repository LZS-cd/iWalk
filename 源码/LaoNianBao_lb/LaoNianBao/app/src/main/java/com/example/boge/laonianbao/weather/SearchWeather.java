package com.example.boge.laonianbao.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.VoiceAndWord.WordToVoice;
import com.example.boge.laonianbao.zpNUM.Data;

import java.util.HashMap;

public class SearchWeather extends AppCompatActivity {

    ImageButton chaxun;
    ImageButton bobao;
    TextView textView;
    EditText editText;
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);
        chaxun = (ImageButton) findViewById(R.id.button6);
        bobao = (ImageButton) findViewById(R.id.button9);
        editText = (EditText) findViewById(R.id.editText10);
        textView = (TextView) findViewById(R.id.textView14);
        hashMap = new HashMap<String, String>();



        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityname = editText.getText().toString();
                String url = "http://v.juhe.cn/weather/index?format=2&cityname=" + cityname + "&key=" + "a5655f4b7a827e3857a8296cf82534a7";
                //http://v.juhe.cn/weather/index?format=2&cityname="+cityname+"&key="+"a5655f4b7a827e3857a8296cf82534a7
                new WeatherAsyntask(textView, hashMap).execute(url);
            }
        });

        chaxun.callOnClick();
        bobao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals("")) {
                    Toast.makeText(SearchWeather.this, "当前无可播报消息", Toast.LENGTH_SHORT).show();
                } else {
                    String message = "今天温度" + hashMap.get("temp").toString() +
                            "天气情况" + hashMap.get("weather").toString() +
                            hashMap.get("dress_advice").toString();
                    WordToVoice wordToVoice = new WordToVoice(SearchWeather.this, message, Data.getB());
                    wordToVoice.GetVoiceFromWord();
                }
            }
        });
    }
}
