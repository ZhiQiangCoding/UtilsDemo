package com.czq.utilsdemo.httputils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.czq.utilsdemo.enums.NetCodeEnum;
import com.czq.utilsdemo.enums.RequestMethodEnum;
import com.czq.utilsdemo.utils.ObjectUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2018/2/7 18:20
 * 包    名：com.yh.baselibrary.net
 * 描    述：okhttp网络请求工具类，并将相应结果回调到ui线程
 * 修订历史：
 * ================================================
 */

public class OkHttpRequestManager implements IRequestManager {
    private static final String TAG = "===" + OkHttpRequestManager.class.getSimpleName() + "::";
    private static final MediaType TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    /**
     * 权限
     */
    private static final String PERMISSION_DENIED = "Permission denied";
    /**
     * 网络
     */
    private static final String NETWORK_IS_UNREACHABLE = "Network is unreachable";
    private OkHttpClient okHttpClient;
    private Handler mHandler;

    //{
    //    //回调到主线程
    //    mHandler = new Handler(Looper.getMainLooper());
    //}

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
    private static class SingletonHolder {
        private static final OkHttpRequestManager OK_HTTP_REQUEST_MANAGER = new OkHttpRequestManager();
    }

    /**
     * 无参构造，配置okhttp的通用设置
     */
    private OkHttpRequestManager() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());

    }

    /**
     * get请求
     *
     * @param url      url地址
     * @param callback 响应回调
     */
    @Override
    public void get(String url, IRequestCallback callback) {
        handleRequest(url, null, callback, RequestMethodEnum.GET.getKey());
    }

    @Override
    public void post(String url, String requestBodyJson, IRequestCallback callback) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        setPost(url, callback, body);
    }

    /**
     * post请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    @Override
    public void post(String url, Map<String, Object> requestParams, IRequestCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (ObjectUtils.isNotEmpty(requestParams)) {
            Set<Map.Entry<String, Object>> entries = requestParams.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                builder.add(entry.getKey(), (String) entry.getValue());
            }
        }
        RequestBody body = builder.build();
        setPost(url, callback, body);
    }

    @Override
    public void put(String url, String requestBodyJson, IRequestCallback callback) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        setPut(url, body, callback);
    }


    /**
     * put请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    @Override
    public void put(String url, Map<String, Object> requestParams, IRequestCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (ObjectUtils.isNotEmpty(requestParams)) {
            Set<Map.Entry<String, Object>> entries = requestParams.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                builder.add(entry.getKey(), (String) entry.getValue());
            }
        }
        RequestBody body = builder.build();
        setPut(url, body, callback);
    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallback callback) {
        RequestBody body = RequestBody.create(TYPE_JSON, requestBodyJson);
        setDelete(url, body, callback);
    }


    /**
     * delete请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    @Override
    public void delete(String url, Map<String, Object> requestParams, IRequestCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (ObjectUtils.isNotEmpty(requestParams)) {
            Set<Map.Entry<String, Object>> entries = requestParams.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                builder.add(entry.getKey(), (String) entry.getValue());
            }
        }
        RequestBody body = builder.build();
        setDelete(url, body, callback);
    }

    /**
     * post请求
     *
     * @param url      url地址
     * @param callback 请求body
     * @param body     响应回调
     */
    private void setPost(String url, IRequestCallback callback, RequestBody body) {
        handleRequest(url, body, callback, RequestMethodEnum.POST.getKey());
    }

    /**
     * put请求
     *
     * @param url      url地址
     * @param body     请求body
     * @param callback 响应回调
     */
    private void setPut(String url, RequestBody body, IRequestCallback callback) {
        handleRequest(url, body, callback, RequestMethodEnum.PUT.getKey());
    }

    /**
     * delete请求
     *
     * @param url      url地址
     * @param body     请求body
     * @param callback 响应回调
     */
    private void setDelete(String url, RequestBody body, IRequestCallback callback) {
        handleRequest(url, body, callback, RequestMethodEnum.DELETE.getKey());
    }

    /**
     * 处理请求
     *
     * @param url       url地址
     * @param body      请求body
     * @param callback  响应回调
     * @param methodKey 请求方法的key
     */
    private void handleRequest(String url, RequestBody body, IRequestCallback callback, int methodKey) {
        RequestMethodEnum methodEnum = RequestMethodEnum.fromKey(methodKey);
        if (ObjectUtils.isEmpty(methodEnum)) {
            throw new NullPointerException("[methodaaaaaaaEnum==null，请重新确认key是否有效==]");
        }
        Request.Builder builder = new Request.Builder()
                .url(url);
        switch (methodEnum) {
            case GET:
                builder.get();
                break;
            case POST:
                builder.post(body);
                break;
            case PUT:
                builder.put(body);
                break;
            case DELETE:
                builder.delete(body);
                break;
            default:
                break;
        }
        Request request = builder.build();
        addCallBack(callback, request);
    }

    /**
     * 添加到回调方法里面
     *
     * @param callback 回调接口，返回结果
     * @param request  请求对象
     */
    private void addCallBack(final IRequestCallback callback, Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (successful) {
                    final String json = response.body().string();
                    //mHandler.post(() -> callback.onSuccess(json));
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            callback.onSuccess(json);
                        }
                    });
                } else {
                    // TODO: 2018/2/8 是否有问题  服务器错误
                    //String msg = response.message() + "=url=" + call.request().url().toString();
                    //mHandler.post(() -> callback.onFailure(msg, String.valueOf(response.code()), null));
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            String msg = response.message() + "  服务器错误=url=" + call.request().url().toString();
                            callback.onFailure(msg, String.valueOf(response.code()), null);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                handleFailure(e, callback);
            }

        });
    }

    private String mCode;
    private String mMsg;

    /**
     * 处理适配的逻辑
     *
     * @param e
     * @param callback
     */
    private void handleFailure(IOException e, final IRequestCallback callback) {
        final Throwable throwable = e.fillInStackTrace();
        String localizedMessage = e.getLocalizedMessage();
        Log.e(TAG, "[==localizedMessage:: " + localizedMessage + "==]", throwable);
        if (throwable instanceof SocketTimeoutException) {
            mCode = NetCodeEnum.TIMEOUT.getKey();
            mMsg = NetCodeEnum.TIMEOUT.getValue();
        } else if (throwable instanceof ConnectException) {
            if (NETWORK_IS_UNREACHABLE.equals(localizedMessage)) {
                mCode = NetCodeEnum.NETWORK.getKey();
                mMsg = NetCodeEnum.NETWORK.getValue();
            } else {
                // TODO: 2018/2/8  貌似有问题
                mCode = NetCodeEnum.CONNECT.getKey();
                mMsg = NetCodeEnum.CONNECT.getValue();
            }
        } else if (throwable instanceof SocketException) {
            if (PERMISSION_DENIED.equals(localizedMessage)) {
                mCode = NetCodeEnum.PERMISSION.getKey();
                mMsg = NetCodeEnum.PERMISSION.getValue();
            } else {
                e.printStackTrace();
                mCode = NetCodeEnum.FAILURE.getKey();
                mMsg = NetCodeEnum.FAILURE.getValue();
            }
        } else {
            e.printStackTrace();
            mCode = NetCodeEnum.FAILURE.getKey();
            mMsg = NetCodeEnum.FAILURE.getValue();
        }
        //mHandler.post(() -> callback.onFailure(mMsg, mCode, throwable));
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                callback.onFailure(mMsg, mCode, throwable);
            }
        });
    }
}
