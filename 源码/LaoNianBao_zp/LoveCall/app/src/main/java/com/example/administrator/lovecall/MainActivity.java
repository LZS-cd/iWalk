package com.example.administrator.lovecall;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String phoneNum = "13540616875";
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
        try {
            startActivity(intent);
            finish();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
