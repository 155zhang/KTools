package com.jiangkang.ktools.requests.zhihu;


import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jiangkang.ktools.R;
import com.jiangkang.requests.KRequests;
import com.jiangkang.requests.zhihu.ZhihuApi;
import com.jiangkang.requests.zhihu.bean.LatestNews;
import com.jiangkang.tools.utils.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhihuFragment extends Fragment {


    public ZhihuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_zhihu, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        getLatestDailyNews();
    }

    private void getLatestDailyNews() {
        KRequests.request(ZhihuApi.class)
                .getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LatestNews>() {
                    @Override
                    public void accept(LatestNews latestNews) throws Exception {
                        LogUtils.json(new Gson().toJson(latestNews));
                    }
                });
    }
}
