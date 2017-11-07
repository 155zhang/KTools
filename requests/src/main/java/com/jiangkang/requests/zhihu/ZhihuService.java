package com.jiangkang.requests.zhihu;

import com.jiangkang.requests.zhihu.bean.LatestNews;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.http.GET;

/**
 * Created by jiangkang on 2017/10/21.
 */

public interface ZhihuService {

    /*
    * 获取最新日报
    * */
    @GET("news/latest")
    Flowable<LatestNews> getLatestDailyNews();

}
