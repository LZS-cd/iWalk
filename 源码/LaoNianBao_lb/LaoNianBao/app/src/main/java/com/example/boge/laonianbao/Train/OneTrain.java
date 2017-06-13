package com.example.boge.laonianbao.Train;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by sxd on 2017/5/20.
 */

public class OneTrain extends TableLayout {
    private TableRow line1;
    private TableRow line2;
    public TextView start_time;
    public TextView end_time;
    public TextView start_station;
    public TextView end_station;
    public TextView train_no;
    public TextView run_time;
    public TextView price_type;
    public TextView price;


    public OneTrain(Context context, LinearLayout linearLayout) {
        super(context);
        linearLayout.addView(this);

        line1 = new TableRow(context);
        line2 = new TableRow(context);

        start_station = new TextView(context);
        start_time = new TextView(context);
        end_station = new TextView(context);
        end_time = new TextView(context);
        train_no = new TextView(context);
        run_time = new TextView(context);
        price_type = new TextView(context);
        price = new TextView(context);

        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lllp.setMargins(30,30,30,30);
        this.setLayoutParams(lllp);
        this.setColumnStretchable(3, true);

        this.addView(line1);
        this.addView(line2);

        TableLayout.LayoutParams tllp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        tllp.setMargins(0,5,0,5);
        line1.setLayoutParams(tllp);
        line2.setLayoutParams(tllp);

        line1.addView(start_time);
        line1.addView(start_station);
        line1.addView(train_no);
        line1.addView(price);

        line2.addView(end_time);
        line2.addView(end_station);
        line2.addView(run_time);
        line2.addView(price_type);


        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;

        TableRow.LayoutParams start_time_lp = new TableRow.LayoutParams(
                width * 7 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        start_time_lp.gravity = Gravity.START;
        start_time.setLayoutParams(start_time_lp);
        start_time.setTextSize(18);
        start_time.setTextColor(0xff000000);
        start_time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        TableRow.LayoutParams start_station_lp = new TableRow.LayoutParams(
                width * 9 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        start_station_lp.gravity = Gravity.START;
        start_station.setLayoutParams(start_station_lp);
        start_station.setTextSize(18);
        start_station.setTextColor(0xff000000);
        start_station.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        TableRow.LayoutParams train_no_lp = new TableRow.LayoutParams(
                width * 9 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        train_no_lp.gravity = Gravity.START;
        train_no.setLayoutParams(train_no_lp);
        train_no.setTextSize(18);
        train_no.setTextColor(0xff000000);
        train_no.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        TableRow.LayoutParams price_lp = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        price_lp.gravity = Gravity.END;
        price.setLayoutParams(price_lp);
        price.setTextSize(18);
        price.setTextColor(0xffff0044);
        price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        TableRow.LayoutParams end_time_lp = new TableRow.LayoutParams(
                width * 7 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        end_time_lp.gravity = Gravity.START;
        end_time.setLayoutParams(end_time_lp);
        end_time.setTextSize(15);
        end_time.setTextColor(0xff777777);

        TableRow.LayoutParams end_station_lp = new TableRow.LayoutParams(
                width * 9 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        end_station_lp.gravity = Gravity.START;
        end_station.setLayoutParams(end_station_lp);
        end_station.setTextSize(15);
        end_station.setTextColor(0xff777777);

        TableRow.LayoutParams run_time_lp = new TableRow.LayoutParams(
                width * 9 / 36,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        run_time_lp.gravity = Gravity.START;
        run_time.setLayoutParams(run_time_lp);
        run_time.setTextSize(15);
        run_time.setTextColor(0xff777777);

        TableRow.LayoutParams price_type_lp = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        price_type_lp.gravity = Gravity.END;
        price_type.setLayoutParams(price_type_lp);
        price_type.setTextSize(15);
        price_type.setTextColor(0xff777777);

        View line = new View(context);
        this.addView(line);

        TableLayout.LayoutParams linelp = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                1
        );
        linelp.setMargins(0,20,0,0);
        line.setLayoutParams(linelp);
        line.setBackgroundColor(0xff777777);
    }

    public void SetText(String start_time, String start_station, String train_no, String price, String end_time, String end_station, String run_time, String price_type){
        this.start_time.setText(start_time);
        this.start_station.setText(start_station);
        this.train_no.setText(train_no);
        this.price.setText(price);
        this.end_time.setText(end_time);
        this.end_station.setText(end_station);
        this.run_time.setText(run_time);
        this.price_type.setText(price_type);
    }
}