package com.example.boge.child.forget;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boge.child.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;

public class forget extends AppCompatActivity {
    private EditText editText;
    private Button btn;
    private EditText codeText;
    private Button signBtn;
    private TimerTask timerTask;
    private Timer timer;
    private int timess;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        progressDialog = new ProgressDialog(this);
        editText = (EditText) findViewById(R.id.retri_edit_code);
        codeText = (EditText) findViewById(R.id.retri_edit_writeCode);
        btn = (Button) findViewById(R.id.retri_bt_getCode);
        signBtn = (Button) findViewById(R.id.retri_bt_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(forget.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String number = editText.getText().toString();
                btn.setClickable(false);
                startTimer();
                ////1+""代表  短信模板
                SMSSDK.getInstance().getSmsCodeAsyn(number, 1 + "", new SmscodeListener() {
                    @Override
                    public void getCodeSuccess(String s) {
                        //Toast.makeText(Retrieve_password.this, s, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void getCodeFail(int i, String s) {
                        stopTimer();
                        Toast.makeText(forget.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(forget.this, "电话不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (codeText.getText().toString().equals("")) {
                    Toast.makeText(forget.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = codeText.getText().toString();
                String phoneNum = editText.getText().toString();
                progressDialog.setTitle("正在验证...");
                progressDialog.show();
                SMSSDK.getInstance().checkSmsCodeAsyn(phoneNum, code, new SmscheckListener() {
                    @Override
                    public void checkCodeSuccess(String s) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        // Toast.makeText(Retrieve_password.this, s, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent().setClass(forget.this,changePass.class));
                        finish();
                    }

                    @Override
                    public void checkCodeFail(int i, String s) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(forget.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    void startTimer() {
        timess=(int) (SMSSDK.getInstance().getIntervalTime()/1000);
        btn.setText(timess+"s");
        if (timer == null)
            timer = new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(timess<=0) {
                            stopTimer();
                            return;
                        }
                        timess--;
                        btn.setText(timess+"s");
                    }
                });
            }
        };
        timer.schedule(timerTask,1000,1000);
    }

    void stopTimer() {
        if(timer!=null)
        {
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null)
        {
            timerTask.cancel();
            timerTask=null;
        }
        btn.setText("重新获取");
        btn.setClickable(true);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    }

