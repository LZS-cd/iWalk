package com.example.boge.laonianbao.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boge.laonianbao.R;

public class Register extends AppCompatActivity implements View.OnClickListener{
    EditText account;
    EditText password;
    EditText re_password;
    TextView notifition;
    EditText name;
    EditText age;
    EditText tel_number;
    Button sure;
    TextView result;
    Boolean permit=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account=(EditText)findViewById(R.id.editText3);
        password=(EditText)findViewById(R.id.editText4);
        re_password=(EditText)findViewById(R.id.editText5);
        notifition=(TextView)findViewById(R.id.textView7);
        name=(EditText)findViewById(R.id.editText6);
        age=(EditText)findViewById(R.id.editText8) ;
        tel_number=(EditText)findViewById(R.id.editText7);
        sure=(Button)findViewById(R.id.button4);
        result=(TextView)findViewById(R.id.textView10) ;
        re_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(re_password.getText().toString().equals(password.getText().toString()))
                {
                    notifition.setText("密码正确");
                    permit=true;
                }
                else
                {
                    notifition.setText("两次密码输入不一致");
                    permit=false;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button4:
                String url="http://192.168.18.136:8080/ServletTest/RegisterServlet"; ////IP
                String str_account=account.getText().toString();
                String str_password=password.getText().toString();
                String str_name=name.getText().toString();
                String str_age=age.getText().toString();
                String str_telephone=tel_number.getText().toString();
                String param="account="+str_account
                                +"&password="+str_password
                                +"&name="+str_name
                                +"&age="+str_age
                                +"&telephone="+str_telephone;
                if(account.getText().toString().equals("")||password.getText().toString().equals("")|| re_password.getText().toString().equals("")||tel_number.getText().toString().equals(""))
                {
                    Toast.makeText(this,"账号密码电话号不能为空",Toast.LENGTH_SHORT).show();
                    permit=false;
                }
                if(permit)
                {

                    new RegisterAsynTask(param,result).execute(url);
                }
                break;

        }
    }



}
