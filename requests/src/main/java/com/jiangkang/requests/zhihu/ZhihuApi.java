package com.jiangkang.requests.zhihu;

import com.jiangkang.requests.BaseApi;
import com.jiangkang.requests.zhihu.bean.LatestNews;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;

/**
 * Created by jiangkang on 2017/10/21.
 */

public class ZhihuApi extends BaseApi<ZhihuService>{

    private static final String BASE_URL = "http://news-at.zhihu.com/api/4/";

    @Override
    protected String getBaseUrl() {
        return BASE_URL;
    }


    public Flowable<LatestNews> getLatestNews(){
        return mService.getLatestDailyNews();
    }



}
