package com.jiangkang.tools.utils;

import android.util.Log;

import com.orhanobut.logger.Logger;


/**
 * Created by jiangkang on 2017/9/28.
 */

public class LogUtils {


    private static final String LOG_DEFAULT = "LogUtils";


    public static void d(Object msg){
        Logger.d(msg);
    }


    public static void d(String format, Object...msg){
        Logger.d(format,msg);
    }

    public static void json(String jsonString){
        Logger.json(jsonString);
    }

}
