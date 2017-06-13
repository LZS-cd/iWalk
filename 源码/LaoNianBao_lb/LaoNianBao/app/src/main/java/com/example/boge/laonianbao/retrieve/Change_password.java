package com.example.boge.laonianbao.retrieve;

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
import com.example.boge.laonianbao.getAndPost.GetAndPost;

import static com.example.boge.laonianbao.login.Validator.isPassword;
import static com.example.boge.laonianbao.login.Validator.isUserName;

/**
 * 100 修改成功
 * 200 账号不存在
 * 201 修改失败
 */
public class Change_password extends AppCompatActivity {
    EditText account;
    EditText password;
    Button sure;
    Handler handler=null;
    boolean permitAcc = false;
    boolean permitPass = false;
    TextView isaccount;
    TextView ispassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        isaccount = (TextView)findViewById(R.id.change_isaccount);
        ispassword = (TextView)findViewById(R.id.change_ispassword);
        account=(EditText)findViewById(R.id.change_edit_account);
        password=(EditText)findViewById(R.id.change_edit_password);
        sure=(Button)findViewById(R.id.chnage_bt_submit);
        account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isaccount.setText("用户名长度至少为3");
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
                    }
                    else if (account.getText().toString().length() > 10 ){
                        isaccount.setText("用户名非法(长度大于10)");
                    }
                    else
                        isaccount.setText("用户名非法(包含非法字符)");

                    permitAcc = false;
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ispassword.setText("密码长度至少为6");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isPassword(password.getText().toString())) {
                    ispassword.setText("密码合法");
                    permitPass = true;
                }
                else {
                    if (password.getText().toString().length() < 6) {
                        ispassword.setText("密码非法(长度小于6)");
                    }
                    else if (password.getText().toString().length() > 20 ){
                        ispassword.setText("密码非法(长度大于20)");
                    }
                    else
                        ispassword.setText("密码非法(包含非法字符)");

                    permitPass = false;
                }
            }

        });


        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 100:
                        Toast.makeText(Change_password.this,"修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 200:
                        AlertDialog.Builder builder=new AlertDialog.Builder(Change_password.this);
                        builder.setTitle("提示");
                        builder.setIcon(R.drawable.icon);
                        builder.setMessage("账号不存在，请重新输入");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog=builder.create();
                        alertDialog.show();
                        break;
                    case 201:
                        Toast.makeText(Change_password.this,"修改失败",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  String url="http://120.25.225.163:8080/ServletTest/ChangePassword"; ////IP
                final  String param="account="+account.getText().toString()+
                        "&password="+password.getText().toString();

                if(account.getText().toString().equals("")||password.getText().toString().equals(""))
                {
                    Toast.makeText(Change_password.this,"修改的账号和密码都不能空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(permitAcc && permitPass)
                {
                    final Message message = new Message();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String text = GetAndPost.get(url, "POST", param);
                            if (text.equals("100")) {
                                message.what = 100;
                            } else if (text.equals("200")) {
                                message.what = 200;
                            } else if (text.equals("201")) {
                                message.what = 201;
                            }
                            handler.sendMessage(message);
                            //Looper.prepare();//Toast属于UI  不可以在非UI线程中 更新UI
                            // Toast.makeText(Change_password.this,text,Toast.LENGTH_SHORT).show();
                            // Looper.loop();
                        }

                    });
                    thread.start();
                }
            }
        });
    }
}
