package com.jiangkang.ktools.audio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.Voice;
import android.util.Log;

import java.util.List;
import java.util.Random;

/**
 * 模拟接到推送通知
 * @author jiangkang
 * @date 2017/10/18
 */

public class VoiceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final List<String> list = new VoiceTemplate()
                .prefix("success")
                .numString("15.00")
                .suffix("yuan")
                .gen();
        VoiceSpeaker.getInstance().speak(list);
    }


}
