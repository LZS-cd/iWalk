package com.example.boge.laonianbao.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.boge.laonianbao.MainActivity;
import com.example.boge.laonianbao.PersonInfo.PersonInfo;
import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.VoiceAndWord.VoiceToWord;
import com.example.boge.laonianbao.network.MyNetWork;
import com.example.boge.laonianbao.register.Register;
import com.example.boge.laonianbao.retrieve.Retrieve_password;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Login
 * 登录服务器
 * 服务器返回100  正确登录
 * 服务器返回200  账号存在，密码不正确
 * 服务器返回201  账号不存在
 */
public class Login extends AppCompatActivity implements View.OnClickListener{
    private Button sure;
    private Button forget_password;
    private Button register;
    private EditText accout;
    private EditText password;
    private ImageButton voice_accout;
    private ImageButton voice_password;
    public Handler handler=null;
    public static final int SUBMIT=1;
    JSONObject jsonObject=null;
    JSONTokener jsonTokener=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        sure=(Button)findViewById(R.id.login_bt_submit);
        forget_password=(Button)findViewById(R.id.login_bt_forpssword);
        register=(Button)findViewById(R.id.login_bt_register);
        accout=(EditText)findViewById(R.id.login_edit_account);
        password=(EditText)findViewById(R.id.login_edit_password);
        voice_accout=(ImageButton)findViewById(R.id.login_imag_account);
        voice_password=(ImageButton)findViewById(R.id.login_imag_password);
        sure.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        register.setOnClickListener(this);
        voice_accout.setOnClickListener(this);
        voice_password.setOnClickListener(this);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.icon);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case SUBMIT:
                        String result = msg.getData().getString("result");
                        String resCode = null;
                        try {
                            jsonTokener = new JSONTokener(result);
                            jsonObject = new JSONObject(jsonTokener);
                            resCode = jsonObject.getString("resultCode");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            break;
                        }
                        if (resCode.equals("100")) {
                            Log.i("Login",accout.getText().toString());
                            PersonInfo.getPersonInfo().setAccount(accout.getText().toString());
                            Log.i("Login",PersonInfo.getPersonInfo().getAccount());
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else if (resCode.equals("101")) {
                           builder.setMessage("密码错误，请重新输入");
                           builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {                                @Override
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
                           builder.setMessage("登录客户端错误");
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
        switch (v.getId()) {
            case R.id.login_bt_submit:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(accout.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(Login.this,"账号密码都不能为空",Toast.LENGTH_SHORT).show();
                else
                {
                    String url="http://120.25.225.163:8080/ServletTest/Login?account="+
                            accout.getText().toString()+"&password="+password.getText().toString()+
                            "&type=old";

                    new MyAsyncTask(handler).execute(url);
                }
                break;
            case R.id.login_bt_forpssword:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent1=new Intent();
                intent1.setClass(Login.this, Retrieve_password.class);
                startActivity(intent1);
                break;
            case R.id.login_bt_register:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent2=new Intent();
                intent2.setClass(Login.this, Register.class);
                startActivity(intent2);
                break;
            case R.id.login_imag_account:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                VoiceToWord voiceToWord1=new VoiceToWord(accout,Login.this);
                voiceToWord1.GetWordFromVoice();
                break;
            case R.id.login_imag_password:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                VoiceToWord voiceToWord2=new VoiceToWord(password,Login.this);
                voiceToWord2.GetWordFromVoice();
                break;

        }
    }
}
