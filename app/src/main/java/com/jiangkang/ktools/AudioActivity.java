package com.jiangkang.ktools;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jiangkang.annotations.Safe;
import com.jiangkang.ktools.audio.VoiceBroadcastReceiver;
import com.jiangkang.ktools.audio.VoiceSpeaker;
import com.jiangkang.ktools.audio.VoiceTemplate;
import com.jiangkang.tools.utils.LogUtils;
import com.jiangkang.tools.utils.ToastUtils;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;

/**
 * @author jiangkang
 */
public class AudioActivity extends AppCompatActivity {

    private static final String TAG = "AudioActivity";

    @BindView(R.id.et_text_content)
    EditText etTextContent;

    @BindView(R.id.btn_text_to_speech)
    Button btnTextToSpeech;

    @BindView(R.id.btn_play_single_sound)
    Button btnPlaySingleSound;

    @BindView(R.id.btn_play_multi_sounds)
    Button btnPlayMultiSounds;

    private TextToSpeech.OnInitListener onInitListener;

    private TextToSpeech speech;


    private MediaPlayer.OnCompletionListener onCompletionListener;

    private MediaPlayer player = new MediaPlayer();

    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, AudioActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speech != null) {
            speech.shutdown();
        }
    }

    @OnClick(R.id.btn_text_to_speech)
    public void onBtnTextToSpeechClicked() {
        onInitListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = speech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ToastUtils.showShortToast("语言不支持");
                    } else {
                        String content = "This is a default voice";
                        if (!TextUtils.isEmpty(etTextContent.getText().toString())) {
                            content = etTextContent.getText().toString();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            speech.speak(content, TextToSpeech.QUEUE_FLUSH, null, null);
                        } else {
                            speech.speak(content, TextToSpeech.QUEUE_FLUSH, null);
                        }

                    }
                }
            }
        };
        speech = new TextToSpeech(this, onInitListener);
    }

    @Safe
    @OnClick(R.id.btn_play_single_sound)
    public void onBtnPlaySingleSoundClicked() {
        MediaPlayer player = MediaPlayer.create(this, R.raw.tts_success);
        player.start();
    }

    @OnClick(R.id.btn_play_multi_sounds)
    public void onBtnPlayMultiSoundsClicked() {
        sendBroadcast(new Intent(this, VoiceBroadcastReceiver.class));
    }


}
