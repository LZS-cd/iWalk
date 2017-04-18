package com.example.boge.laonianbao.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.MainActivity;
import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.VoiceAndWord.VoiceToWord;
import com.example.boge.laonianbao.network.MyNetWork;
import com.example.boge.laonianbao.register.Register;
import com.example.boge.laonianbao.retrieve.Retrieve_password;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button sure;
    Button forget_password;
    Button register;
    EditText accout;
    EditText password;
    TextView text;
    ImageButton voice_accout;
    ImageButton voice_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        sure=(Button)findViewById(R.id.button);
        forget_password=(Button)findViewById(R.id.button3);
        register=(Button)findViewById(R.id.button2);
        accout=(EditText)findViewById(R.id.editText);
        password=(EditText)findViewById(R.id.editText2);
        text=(TextView)findViewById(R.id.textView3);
        voice_accout=(ImageButton)findViewById(R.id.imageButton2);
        voice_password=(ImageButton)findViewById(R.id.imageButton3);
        sure.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        register.setOnClickListener(this);
        voice_accout.setOnClickListener(this);
        voice_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if(!MyNetWork.isNetworkAvailable(Login.this))
                {
                    Toast.makeText(Login.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(accout.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(Login.this,"账号密码都不能为空",Toast.LENGTH_SHORT).show();
                else
                {
                    String url="http://192.168.18.136:8080/ServletTest/Login?account="+
                            accout.getText().toString()+"&password="+password.getText().toString();

                    new MyAsyncTask(text).execute(url);
                }
                break;
            case R.id.button2:
                Intent intent1=new Intent();
                intent1.setClass(Login.this, Retrieve_password.class);
                startActivity(intent1);
                break;
            case R.id.button3:
                Intent intent2=new Intent();
                intent2.setClass(Login.this, Register.class);
                startActivity(intent2);
                break;
            case R.id.imageButton2:
                VoiceToWord voiceToWord1=new VoiceToWord(accout,Login.this);
                voiceToWord1.GetWordFromVoice();
                break;
            case R.id.imageButton3:
                VoiceToWord voiceToWord2=new VoiceToWord(password,Login.this);
                voiceToWord2.GetWordFromVoice();
                break;

        }

    }
}
