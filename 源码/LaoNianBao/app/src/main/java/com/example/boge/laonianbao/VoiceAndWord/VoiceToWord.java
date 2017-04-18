package com.example.boge.laonianbao.VoiceAndWord;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Boge on 2017/4/16.
 */

public class VoiceToWord {
    private Context context;
    private EditText editText;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    public String result;
    public VoiceToWord(EditText editText,Context context)
    {
        this.context=context;
        this.editText=editText;
        mIatDialog = new RecognizerDialog(context, null);
        mSharedPreferences = context.getSharedPreferences("com.iflytek.setting",
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    } public void GetWordFromVoice()
    {
        FlowerCollector.onEvent(context, "iat_recognize");
        mIatResults.clear();
        setParam();
        if( null == mIatDialog ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
            return ;
        }
        boolean isShowDialog = mSharedPreferences.getBoolean(
                "iat_show", true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            showTip("请开始说话...");}
    }
    public void setParam() {
        // 清空参数
        mIatDialog.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIatDialog.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIatDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIatDialog.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIatDialog.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIatDialog.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIatDialog.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIatDialog.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "0"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIatDialog.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIatDialog.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };
    private void  printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        editText.setText(resultBuffer.toString());
    }
}
