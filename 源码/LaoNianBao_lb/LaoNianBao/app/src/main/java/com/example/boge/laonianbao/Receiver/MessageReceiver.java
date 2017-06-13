package com.example.boge.laonianbao.Receiver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.example.boge.laonianbao.PersonInfo.PersonInfo;
import com.example.boge.laonianbao.R;
import com.example.boge.laonianbao.getAndPost.GetAndPost;
import com.example.boge.laonianbao.global.KDXFinit;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by Boge on 2017/5/28.
 */

public class MessageReceiver extends XGPushBaseReceiver {
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
        Log.i("XPush",xgPushTextMessage.getTitle()+xgPushTextMessage.getContent());
        final String child=xgPushTextMessage.getContent();
        AlertDialog.Builder builder = new AlertDialog.Builder(KDXFinit.getActivity());
        builder.setTitle("添加结果");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("用户"+child+"请求添加");
        builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://120.25.225.163:8080/ServletTest/AddParent?type=2&child="
                                +child+"&parent="+ PersonInfo.getPersonInfo().getAccount()+"&res=disagree";
                        GetAndPost.get(url,"GET",null);
                    }
                });
                thread.start();
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://120.25.225.163:8080/ServletTest/AddParent?type=2&child="
                                +child+"&parent="+ PersonInfo.getPersonInfo().getAccount()+"&res=agree";
                        GetAndPost.get(url,"GET",null);
                    }
                });

                thread.start();
            dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
