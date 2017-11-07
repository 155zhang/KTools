package com.jiangkang.ktools.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiangkang.ktools.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

public class WebActivity extends AppCompatActivity implements WebContract.IView{


    private static final String TAG = "WebActivity";
    @BindView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @BindView(R.id.tv_title_middle)
    TextView tvTitleMiddle;
    @BindView(R.id.tv_title_right)
    TextView tvTitleRight;
    @BindView(R.id.web_container)
    WebView webContainer;
    private String launchUrl;


    /*
    * launchUrl : 网址
    *
    * */
    public static void launch(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WebActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initVar();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initVar() {
        launchUrl = getIntent().getStringExtra("launchUrl");

        Log.d(TAG, "initVar: launchUrl = " + launchUrl);

        webContainer.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        webContainer.setWebChromeClient(new KWebChromeClient(this));
        webContainer.setWebViewClient(new KWebViewClient(this));

        //webContainer.setLongClickable(false);
        //webContainer.setOnLongClickListener(new View.OnLongClickListener() {
        //  @Override public boolean onLongClick(View v) {
        //    return true;
        //  }
        //});

        WebView.setWebContentsDebuggingEnabled(true);

        webContainer.getSettings().setJavaScriptEnabled(true);
        webContainer.getSettings().getAllowFileAccessFromFileURLs();

        webContainer.addJavascriptInterface(new KJavaInterface(this),"jk");

        webContainer.loadUrl(launchUrl);

    }


    @OnClick(R.id.iv_title_left)
    public void onIvTitleLeftClicked() {
        if (webContainer.canGoBack()){
            webContainer.goBack();
        }else {
            finish();
        }
    }

    @OnClick(R.id.tv_title_right)
    public void onTvTitleRightClicked() {

    }

    @Override
    public void onBackPressed() {
        if (webContainer.canGoBack()) {
            webContainer.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webContainer.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public TextView getTvTitle() {
        return tvTitleMiddle;
    }

    @Override
    public ImageView getIvBack() {
        return ivTitleLeft;
    }


}
