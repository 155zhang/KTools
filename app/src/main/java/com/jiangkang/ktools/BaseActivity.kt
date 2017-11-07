package com.jiangkang.ktools

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity

import com.jiangkang.ktools.device.MediaContentObserver

open class BaseActivity : AppCompatActivity() {

    private var mHandlerThread: HandlerThread = HandlerThread("ScreenShot")

    private var handler: Handler = Handler()

    private var mInternalObserver: ContentObserver? = null

    private var mExternalObserver: ContentObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initHandler()

        initObserver()

        addObservers()
    }


    private fun addObservers() {
        contentResolver.registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                false,
                mInternalObserver!!)
        contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false,
                mExternalObserver!!
        )
    }

    private fun initHandler() {
        mHandlerThread!!.start()
        handler = Handler(mHandlerThread!!.looper)
    }

    private fun initObserver() {
        mInternalObserver = MediaContentObserver(this, handler, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        mExternalObserver = MediaContentObserver(this, handler, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }


    private fun removeObservers() {
        contentResolver.unregisterContentObserver(mInternalObserver!!)
        contentResolver.unregisterContentObserver(mExternalObserver!!)
    }
}
