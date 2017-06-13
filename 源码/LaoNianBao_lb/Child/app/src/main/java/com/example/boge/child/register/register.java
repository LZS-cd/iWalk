package com.example.boge.child.register;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.child.GetAndPost.GetAndPost;
import com.example.boge.child.R;

import static com.example.boge.child.R.drawable.builder;

public class register extends AppCompatActivity {

    EditText account;
    EditText password;
    EditText repassword;
    Button sumbit;
    boolean permit=false;
    TextView textView;
    public static final int SUBMIT=1;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account=(EditText)findViewById(R.id.editText6);
        password=(EditText)findViewById(R.id.editText7);
        repassword=(EditText)findViewById(R.id.editText8);
        textView=(TextView)findViewById(R.id.textView2);
        sumbit=(Button)findViewById(R.id.button);
         builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.icon);
        repassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(repassword.getText().toString().equals(password.getText().toString()))
                {
                    textView.setText("密码输入正确");
                    permit=true;
                }
                else
                {
                    textView.setText("两次密码输入不一致");
                    permit=false;
                }
            }
        });
        sumbit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(account.getText().toString().equals(""))
                permit=false;
            if(permit){
                String url="http://120.25.225.163:8080/ServletTest/ChildRegister";
                String my_account=account.getText().toString();
                String my_password=account.getText().toString();
                String param="account="+my_account
                        +"&password="+my_password;
                new RegisAsyn(handler,param).execute(url);
            }else{
                Toast.makeText(register.this,"请输入完整信息",Toast.LENGTH_SHORT).show();
            }
        }
        });
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what)
            {
                case SUBMIT:
                    String resCode=msg.getData().getString("result");
                   if(resCode.equals("100")){
                       finish();
                   }
                    else if(resCode.equals("200")){
                       builder.setMessage("账号存在，请重新输入");
                       builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       });
                       AlertDialog alertDialog = builder.create();
                       alertDialog.show();
                   }else {
                       Toast.makeText(register.this,"插入出错",Toast.LENGTH_SHORT).show();
                   }
                    break;
            }
            return false;
        }
    });
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
