package com.jiangkang.requests;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.jiangkang.tools.King;
import com.jiangkang.tools.utils.NetworkUtils;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author jiangkang
 * @date 2017/10/21
 */

public abstract class BaseApi<Service> {

    private static final String TAG = "API";

    protected Service mService;

    protected Retrofit mRetrofit;

    private static int TIMEOUT_SECONDS = 12;

    protected BaseApi() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getBaseUrl())
                .client(createClient())
                .build();

        initService();

    }

    protected void initService() {

        mService = createProxyService(mRetrofit.create(getServiceClass()));
    }

    protected Service createProxyService(final Service service) {
        Class<Service> serviceClass = getServiceClass();
        return (Service) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (NetworkUtils.isNetAvailable()) {
                    return ((Flowable) method.invoke(service, args)).subscribeOn(Schedulers.io());
                } else {
                    return Flowable.error(new NetworkErrorException()).subscribeOn(Schedulers.io());
                }
            }
        });
    }

    private Class<Service> getServiceClass() {
        Type superClassType = getClass().getGenericSuperclass();
        if (!ParameterizedType.class.isAssignableFrom(superClassType.getClass())) {
            return null;
        }
        Class<Service> serviceClass = (Class<Service>) ((ParameterizedType) superClassType).getActualTypeArguments()[0];
        return serviceClass;
    }

    protected OkHttpClient createClient() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new ChuckInterceptor(King.getApplicationContext()))
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .build();
        return client;
    }

    protected abstract String getBaseUrl();

}
