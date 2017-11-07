package com.jiangkang.requests;

import android.support.annotation.NonNull;

import java.lang.reflect.Constructor;

/**
 *
 * @author jiangkang
 * @date 2017/10/21
 */

public class KRequests {

    public static <T extends BaseApi> T request(@NonNull Class<T> api){
        try {
            return api.newInstance();
        } catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

}
