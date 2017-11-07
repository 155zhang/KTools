package com.jiangkang.tools.utils;

import android.support.annotation.Nullable;

/**
 *
 * @author jiangkang
 * @date 2017/10/20
 */

public final class Utils {

    static <T> T checkNotNull(@Nullable T object, String message){
        if (object == null){
            throw new NullPointerException(message);
        }
        return object;
    }

}
