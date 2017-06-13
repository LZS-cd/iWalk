package com.example.boge.laonianbao.Train;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrainTimes extends AppCompatActivity {
    private TextView timeText;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket);
        timeText = (TextView) findViewById(R.id.dateChoice);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        timeText.setText(year + "-" + (month + 1) + "-" + (day + 1));

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPick((TextView) v);
            }
        });

        button = (Button) findViewById(R.id.getTIcket);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText fromwhere = (EditText) findViewById(R.id.textView9);
                EditText towhere = (EditText) findViewById(R.id.textView10);

                if (fromwhere.getText().toString().equals("") || towhere.getText().toString().equals("")) {
                    Toast.makeText(TrainTimes.this, "请输入正确的起始地点", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle data = new Bundle();
                data.putString("from",fromwhere.getText().toString());
                data.putString("to",towhere.getText().toString());
                data.putString("date",timeText.getText().toString());

                Intent intent = new Intent(TrainTimes.this, TicketInfo.class);
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    private void showDialogPick(final TextView timeText) {
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(TrainTimes.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                timeText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);
        String minDate = year + "-";
        if (month < 9) minDate += "0" + (month + 1) + "-";
        else minDate += "0" + (month + 1) + "-";
        minDate += day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            long mintime = sdf.parse(minDate).getTime();
            long interval = 60L * 60L * 24L * 30L * 1000L;
            datePickerDialog.getDatePicker().setMinDate(mintime);
            datePickerDialog.getDatePicker().setMaxDate(mintime + interval);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //弹出选择日期对话框
        datePickerDialog.show();

    }

}
