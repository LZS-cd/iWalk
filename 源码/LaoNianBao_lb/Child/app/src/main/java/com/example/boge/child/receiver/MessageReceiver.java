package com.example.boge.child.receiver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boge.child.Global.global;
import com.example.boge.child.R;
import com.example.boge.child.olderInfo.OlderInfo;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by Boge on 2017/5/20.
 */

public class MessageReceiver extends XGPushBaseReceiver {
    private static boolean first=true;
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        final EditText editText=new EditText(global.getActivity());
        Log.i("XPush",xgPushTextMessage.getTitle()+xgPushTextMessage.getContent());
        String result=xgPushTextMessage.getContent();
        String message="";
        final String account=xgPushTextMessage.getTitle();
        AlertDialog.Builder builder = new AlertDialog.Builder(global.getActivity());
        builder.setTitle("添加结果");
        builder.setIcon(R.drawable.icon);
        if(result.equals("agree")){
            message="用户已经同意添加";
            builder.setMessage(message);
            builder.setView(editText);

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定",null);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(editText.getText().toString().equals("")){
                    }else {
                        OlderInfo.getOlderInfo().addOlder(editText.getText().toString(),account);
                        if(first){//如果第一次添加  那么
                            Intent mIntent = new Intent("Search");

                            //发送广播
                            global.getActivity().sendBroadcast(mIntent);
                            first=false;
                        }
                        alertDialog.dismiss();
                    }
                }
            });

        }else{
            message="用户拒绝添加";
            builder.setMessage(message);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }


    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
