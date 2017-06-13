package com.example.boge.laonianbao.register;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;

import static com.example.boge.laonianbao.login.Validator.isPassword;
import static com.example.boge.laonianbao.login.Validator.isUserName;

/**
 * 注册
 * 返回 100 注册成功
 * 返回 200 账号存在
 * 返回 201 表插入错误  开发者测试
 */
public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText account;
    EditText password;
    EditText re_password;
    TextView notifition;
    TextView isaccount;
    TextView ispassword;
    EditText name;
    EditText age;
    EditText tel_number;
    Button sure;
    Boolean permit = false;
    Boolean empty = false;
    Boolean permitAcc = false;
    Boolean permitPass = false;
    Handler handler = null;
    public static final int SUBMIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = (EditText) findViewById(R.id.regis_edit_account);
        password = (EditText) findViewById(R.id.regis_edit_password);
        re_password = (EditText) findViewById(R.id.regis_edit_repassword);
        isaccount = (TextView) findViewById(R.id.isaccount);
        ispassword = (TextView) findViewById(R.id.ispassword);
        notifition = (TextView) findViewById(R.id.regis_text_notify);
        name = (EditText) findViewById(R.id.regis_edit_name);
        age = (EditText) findViewById(R.id.regis_edit_age);
        tel_number = (EditText) findViewById(R.id.regis_edit_tel);
        sure = (Button) findViewById(R.id.regis_bt_submit);
        sure.setOnClickListener(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.icon);
        //我加的
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUserName(account.getText().toString())) {
                    isaccount.setText("用户名合法");
                    permitAcc = true;

                } else {
                    if (account.getText().toString().length() < 3) {
                        isaccount.setText("用户名非法(长度小于3)");
                    } else if (account.getText().toString().length() > 10) {
                        isaccount.setText("用户名非法(长度大于10)");
                    } else
                        isaccount.setText("用户名非法(包含非法字符)");

                    permitAcc = false;
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isPassword(password.getText().toString())) {
                    ispassword.setText("密码合法");
                    permitPass = true;
                } else {
                    if (password.getText().toString().length() < 6) {
                        ispassword.setText("密码非法(长度小于6)");
                    } else if (password.getText().toString().length() > 20) {
                        ispassword.setText("密码非法(长度大于20)");
                    } else
                        ispassword.setText("密码非法(包含非法字符)");

                    permitPass = false;
                }
            }

        });

        re_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (re_password.getText().toString().equals(password.getText().toString())) {
                    notifition.setText("密码正确");
                    permit = true;
                } else {
                    notifition.setText("两次密码输入不一致");
                    permit = false;
                }
            }
        });
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case SUBMIT:
                        String resCode = msg.getData().getString("result");
                        if (resCode.equals("100")) {
                            Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (resCode.equals("200")) {
                            builder.setMessage("账号存在，请重新输入");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else if (resCode.equals("201")) {
                            builder.setMessage("表格插入错误");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {

                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regis_bt_submit:
                String url = "http://120.25.225.163:8080/ServletTest/RegisterServlet"; ////IP
                String str_account = account.getText().toString();
                String str_password = password.getText().toString();
                String str_name = name.getText().toString();
                String str_age = age.getText().toString();
                String str_telephone = tel_number.getText().toString();
                String param = "account=" + str_account
                        + "&password=" + str_password
                        + "&name=" + str_name
                        + "&age=" + str_age
                        + "&telephone=" + str_telephone;
                empty = true;
                if (name.getText().toString().equals("") || age.getText().toString().equals("") || account.getText().toString().equals("") || password.getText().toString().equals("") || re_password.getText().toString().equals("") || tel_number.getText().toString().equals("")) {
                    empty = false;
                }
                if (!empty) {
                    Toast.makeText(this, "所有信息均不能为空", Toast.LENGTH_SHORT).show();
                } else if (!permit) {
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                } else if (!permitAcc) {
                    Toast.makeText(this, "账号不合法，请重新输入", Toast.LENGTH_SHORT).show();
                } else if (!permitPass) {
                    Toast.makeText(this, "密码不合法，请重新输入", Toast.LENGTH_SHORT).show();
                }

                if (empty && permit && permitAcc && permitPass) {
                    Toast.makeText(this, "正在注册", Toast.LENGTH_LONG).show();
                    new RegisterAsynTask(param, handler).execute(url);
                }
                break;

        }
    }
}
