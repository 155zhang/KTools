package com.jiangkang.ktools.effect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jiangkang.ktools.effect.fragment.EffectFragment;

public class EffectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new EffectFragment(), this.toString())
                .commit();
    }
}
