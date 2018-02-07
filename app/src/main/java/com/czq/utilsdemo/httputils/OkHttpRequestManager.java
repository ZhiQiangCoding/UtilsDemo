package com.czq.utilsdemo.httputils;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/12/21 10:31
 * 描述：okhttp网络请求工具类
 */

public class OkHttpRequestManager implements IRequestManager {
    private static final MediaType TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    private OkHttpClient okHttpClient;
    private Handler handler;

    /**
     * 提供返回OkHttpRequestManager对象的方法
     *
     * @return
     */
    public static OkHttpRequestManager getInstance() {
        return SingletonHolder.OK_HTTP_REQUEST_MANAGER;
    }

    /**
     * 提供一个静态类，用来实例化OkHttpRequestManager对象
     */
    public static class SingletonHolder {
        private static final OkHttpRequestManager OK_HTTP_REQUEST_MANAGER = new OkHttpRequestManager();
    }

    /**
     * 无参构造，配置okhttp的通用设置
     */
    public OkHttpRequestManager() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        //在那个线程创建对象，则最后的请求结果将在该线程回调
        handler = new Handler();
    }

    @Override
    public void get(String url, IRequestCallBack callBack) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        addCallBack(callBack, request);
    }


    @Override
    public void post(String url, String requestBodyJson, IRequestCallBack callBack) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        addCallBack(callBack, request);
    }

    @Override
    public void put(String url, String requestBodyJson, IRequestCallBack callBack) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        addCallBack(callBack, request);
    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallBack callBack) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        addCallBack(callBack, request);
    }

    /**
     * 添加到回调方法里面
     *
     * @param callBack 回调接口，返回结果
     * @param request  请求对象
     */
    private void addCallBack(final IRequestCallBack callBack, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (successful) {
                    final String json = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(json);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailure(new IOException(response.message() + "，" +
                                    "url=" + call.request().url().toString()));
                        }
                    });
                }
            }
        });
    }
}
