package com.example.boge.laonianbao.VoiceAndWord;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.thirdparty.S;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Boge on 2017/4/16.
 */

public class WordToVoice {
    private Context context;
    private String word;
    // 语音合成对象
    private SpeechSynthesizer mTts;
    // 默认发音人
    private String voicer;
    // 缓冲进度
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private Toast mToast;
    private String[] mCloudVoicersEntries; //云端发音人列表
    private String[] mCloudVoicersValue ;
    private SharedPreferences mSharedPreferences;
    public WordToVoice(Context context, String word, String voicer)
    {
        this.context=context;
        this.word=word;
        this.voicer = voicer;
        mCloudVoicersEntries=new String[]{"小燕—女青、中英、普通话","小宇—男青、中英、普通话",
                "凯瑟琳—女青、英","亨利—男青、英","玛丽—女青、英",
                "小研—女青、中英、普通话" ,"小琪—女青、中英、普通话",
                "小峰—男青、中英、普通话","小梅—女青、中英、粤语","小莉—女青、中英、台湾普通话",
                "小蓉—女青、中、四川话","小芸—女青、中、东北话","小坤—男青、中、河南话",
                "小强—男青、中、湖南话","小莹—女青、中、陕西话","小新—男童、中、普通话",
                "楠楠—女童、中、普通话","老孙—男老、中、普通话"};
        mCloudVoicersValue=new String[]{"xiaoyan","xiaoyu","catherine","henry","vimary","vixy",
                "xiaoqi", "vixf","xiaomei" ,"xiaolin" ,"xiaorong","xiaoqian","xiaokun","xiaoqiang",
                "vixying","xiaoxin","nannan","vils"};
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(context, null);
        mSharedPreferences = context.getSharedPreferences("com.iflytek.setting", MODE_PRIVATE);
        mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }
    public void GetVoiceFromWord()
    {
        if(mTts==null) {
            showTip("创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化");
            return;
        }
        // 开始合成
        // 收到onCompleted 回调时，合成结束、生成合成音频
        // 合成的音频格式：只支持pcm格式
        setParam();
        int code = mTts.startSpeaking(word, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            showTip("语音合成失败,错误码: " + code);
        }
      /*  // 取消合成
        mTts.stopSpeaking();
        // 暂停播放
        mTts.pauseSpeaking();
        // 继续播放
        mTts.resumeSpeaking();
      */

    }
    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            showTip("开始播放");
        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {
// 合成进度
            mPercentForBuffering = i;
            showTip(String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            mPercentForPlaying = i;
            showTip(String.format("缓冲进度为%d%%，播放进度为%d%%",
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                showTip("播放完成");
            } else if (speechError != null) {
                showTip(speechError.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
    private void showTip(final String str) {
        //mToast.setText(str);
        //mToast.show();
    }
    private void setParam(){
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数

        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        // 设置在线合成发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }
}
