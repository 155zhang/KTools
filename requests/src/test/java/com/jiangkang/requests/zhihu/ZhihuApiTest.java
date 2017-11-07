package com.jiangkang.requests.zhihu;

import com.jiangkang.requests.KRequests;
import com.jiangkang.requests.zhihu.bean.LatestNews;

import org.junit.Rule;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

import static org.junit.Assert.*;

/**
 * Created by jiangkang on 2017/10/24.
 */
public class ZhihuApiTest {

    @Rule public final MockWebServer server = new MockWebServer();

    @Test
    public void getLatestNews() throws Exception {

    }

}