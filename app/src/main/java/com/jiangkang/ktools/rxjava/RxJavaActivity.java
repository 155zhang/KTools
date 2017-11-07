package com.jiangkang.ktools.rxjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jiangkang.ktools.rxjava.fragments.RxJavaFragment;


public class RxJavaActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,new RxJavaFragment(),this.toString())
                .commit();

    }


}
