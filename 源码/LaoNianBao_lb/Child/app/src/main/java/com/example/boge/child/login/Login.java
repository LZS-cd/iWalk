package com.example.boge.child.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boge.child.MainActivity;
import com.example.boge.child.Network.MyNetWork;
import com.example.boge.child.R;
import com.example.boge.child.addParent.PersonInfo;
import com.example.boge.child.forget.forget;
import com.example.boge.child.register.register;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Login
 * 登录服务器
 * 服务器返回100  正确登录
 * 服务器返回101  密码错误
 * 服务器返回200  客户端错误
 * 服务器返回201  账号不存在
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText account,password;
     private Button bt_register,bt_forget,sumbit;
    public Handler handler=null;
    public static final int SUBMIT=1;
    private JSONObject jsonObject=null;
    private JSONTokener jsonTokener=null;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account=(EditText)findViewById(R.id.edit_account);
        password=(EditText)findViewById(R.id.edit_password);
        bt_register=(Button)findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        bt_forget=(Button)findViewById(R.id.bt_forget);
        bt_forget.setOnClickListener(this);
        sumbit=(Button)findViewById(R.id.bt_submit);
        sumbit.setOnClickListener(this);
        builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.icon);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case SUBMIT:
                        String result = msg.getData().getString("result");
                        String resCode = "";
                        try {
                            Log.i("Login",result);
                            jsonTokener = new JSONTokener(result);
                            jsonObject = new JSONObject(jsonTokener);
                            resCode = jsonObject.getString("resultCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            break;
                        }
                        if (resCode.equals("100")) {
                            PersonInfo.getPersonInfo().setAccount(account.getText().toString());
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else if (resCode.equals("101")) {
                            builder.setMessage("密码错误，请重新输入");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else if (resCode.equals("201")) {
                            builder.setMessage("账号不存在");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            builder.setMessage("客户端错误");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bt_forget:
                startActivity(new Intent(this,forget.class));
                break;
            case R.id.bt_register:
                startActivity(new Intent(this,register.class));
                break;
            case R.id.bt_submit:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(account.getText().toString().equals("") || password.getText().toString().equals(""))
                {
                    builder.setMessage("账号密码不能为空");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    break;
                }

                else
                {
                    String url="http://120.25.225.163:8080/ServletTest/Login?account="+
                            account.getText().toString()+"&password="+password.getText().toString()+"&type=child";

                    new MyAsyncTask(handler).execute(url);
                }
                break;

        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
