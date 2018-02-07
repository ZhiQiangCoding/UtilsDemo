package com.angel.retorfitrxmvpdemo.api;

import com.angel.retorfitrxmvpdemo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public class ServcerManager {
    private static final String BASE_URL = "http://www.tngou.net/api/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public static <T> T getApi(Class<T> tClass) {
        return retrofit.create(tClass);
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new LogingInterceptor())
                .connectTimeout(5000, TimeUnit.SECONDS)//设置超时时间
                .retryOnConnectionFailure(true);
        //如果不是在正式包，添加拦截 日志拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(logging);
        }
        return client.build();
    }
}
