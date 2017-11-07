package com.jiangkang.ktools

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jiangkang.ktools.web.WebActivity
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        tv_git.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("launchUrl", "https://github.com/jiangkang/KTools")
            WebActivity.launch(this, bundle)
        }
    }

    companion object {
        fun launch(context: Context, bundle: Bundle?) {
            val intent = Intent(context, AboutActivity::class.java)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
        }
    }
}
