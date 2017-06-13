package com.example.boge.child.addParent;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boge.child.GetAndPost.GetAndPost;
import com.example.boge.child.MainActivity;
import com.example.boge.child.R;

public class AddParent extends AppCompatActivity {

    Button button;
    EditText editText;
    String accout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);


        //按钮 编辑框
        button=(Button)findViewById(R.id.button3);
        editText=(EditText)findViewById(R.id.editText);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        toolbar.setTitle("添加老人");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.undo);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setResult(MainActivity.DO_NOTHING);
                finish();
            }
        });
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setIcon(R.drawable.builder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accout=editText.getText().toString();
                if(accout.equals(""))
                {
                    builder.setMessage("查询用户不能为空");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return;
                }
                else {
                    builder.setIcon(R.drawable.cool);
                    builder.setMessage("添加消息已发送");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url="http://120.25.225.163:8080/ServletTest/AddParent?type=1&child="
                                    +PersonInfo.getPersonInfo().getAccount()+"&parent="+accout;
                            String code=GetAndPost.get(url,"GET",null);
                            Message message=new Message();
                            message.what=1;
                            Bundle bundle=new Bundle();
                            bundle.putString("result",code);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    });
                    thread.start();
                }

            }
        });
    }
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String code=msg.getData().getString("result");
                    if(code.equals("100")){
                        Toast.makeText(AddParent.this,"成功发送",Toast.LENGTH_SHORT).show();
                    }else if(code.equals("200")){
                        Toast.makeText(AddParent.this,"账号不存在",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddParent.this,"其他错误",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });

}
