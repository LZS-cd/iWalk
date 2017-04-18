package com.example.boge.laonianbao.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boge.laonianbao.R;

public class SearchWeather extends AppCompatActivity {

    Button button;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_weather);
        button=(Button)findViewById(R.id.button6);
        editText=(EditText)findViewById(R.id.editText10);
        textView=(TextView)findViewById(R.id.textView14);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityname=editText.getText().toString();
                String url="http://v.juhe.cn/weather/index?format=2&cityname="+cityname+"&key="+"a5655f4b7a827e3857a8296cf82534a7";
                new WeatherAsyntask(textView).execute(url);
            }
        });
    }
}
