package com.example.boge.child.forget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boge.child.GetAndPost.GetAndPost;
import com.example.boge.child.R;
import com.example.boge.child.login.Login;

public class changePass extends AppCompatActivity {

    EditText account;
    EditText password;
    Button sure;
    Handler handler=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        account=(EditText)findViewById(R.id.change_edit_account);
        password=(EditText)findViewById(R.id.change_edit_password);
        sure=(Button)findViewById(R.id.chnage_bt_submit);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what)
                {
                    case 100:
                        Toast.makeText(changePass.this,"修改成功",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(changePass.this,Login.class));
                        finish();
                        break;
                    case 200:
                        AlertDialog.Builder builder=new AlertDialog.Builder(changePass.this);
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
                        Toast.makeText(changePass.this,"修改失败",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(changePass.this,"修改的账号和密码都不能空",Toast.LENGTH_SHORT).show();
                    return;
                }
                final Message message=new Message();
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String text= GetAndPost.get(url,"POST",param);
                        if(text.equals("100"))
                        {
                            message.what=100;
                        }
                        else if(text.equals("200")){
                            message.what=200;
                        }
                        else if(text.equals("201"))
                        {
                            message.what=201;
                        }
                        handler.sendMessage(message);
                        //Looper.prepare();//Toast属于UI  不可以在非UI线程中 更新UI
                        // Toast.makeText(Change_password.this,text,Toast.LENGTH_SHORT).show();
                        // Looper.loop();
                    }
                });
                thread.start();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
