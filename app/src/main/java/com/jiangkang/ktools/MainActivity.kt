package com.jiangkang.ktools


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.jiangkang.ktools.web.WebActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item!!.itemId) {
        R.id.menu_about -> consume { AboutActivity.launch(this, null) }
        R.id.menu_git -> consume { openBrowser("https://github.com/jiangkang/KTools") }
        R.id.menu_article -> consume { openBrowser("http://www.jianshu.com/u/2c22c64b9aff") }
        else -> super.onOptionsItemSelected(item)
    }

    private inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }

    private fun openBrowser(url: String) {
        var bundle = Bundle()
        bundle.putString("launchUrl", url)
        WebActivity.launch(this, bundle)
    }


    private fun initViews() {
        rc_function_list.layoutManager = GridLayoutManager(this, 4)
        rc_function_list.adapter = FunctionAdapter(this)
    }

}
