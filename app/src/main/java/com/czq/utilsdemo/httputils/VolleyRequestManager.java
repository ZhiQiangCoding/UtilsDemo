package com.czq.utilsdemo.httputils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.czq.utilsdemo.App;
import com.czq.utilsdemo.enums.NetCodeEnum;
import com.czq.utilsdemo.enums.RequestMethodEnum;
import com.czq.utilsdemo.utils.ObjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/12/21 09:31
 * 描述：volley请求的工具类
 */

public class VolleyRequestManager implements IRequestManager {
    /**
     * 提供返回VolleyRequestManager对象的方法
     *
     * @return
     */
    public static VolleyRequestManager getIntence() {
        return SingletonHolder.REQUEST_MANAGER;
    }

    /**
     * 提供一个静态类，用来实例化VolleyRequestManager对象
     */
    public static class SingletonHolder {
        private static final VolleyRequestManager REQUEST_MANAGER = new VolleyRequestManager();
    }

    /**
     * get请求
     *
     * @param url      url地址
     * @param callback 响应回调
     */
    @Override
    public void get(String url, final IRequestCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Throwable throwable = error.fillInStackTrace();
                callback.onFailure(NetCodeEnum.FAILURE.getValue(), NetCodeEnum.FAILURE.getKey(), throwable);
            }
        });
        //添加到请求队列
        App.mRequestQueue.add(request);
    }

    /**
     * post请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    @Override
    public void post(String url, String requestParamJson, IRequestCallback callback) {
        requestWithBody(url, requestParamJson, callback, RequestMethodEnum.POST.getKey());
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

    }

    /**
     * put请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    @Override
    public void put(String url, String requestParamJson, IRequestCallback callback) {
        requestWithBody(url, requestParamJson, callback, RequestMethodEnum.PUT.getKey());
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

    }

    /**
     * delete请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    @Override
    public void delete(String url, String requestParamJson, IRequestCallback callback) {
        requestWithBody(url, requestParamJson, callback, RequestMethodEnum.DELETE.getKey());
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

    }

    /**
     * 封装带请求参数请求体的请求方法
     *
     * @param url             请求url
     * @param requestBodyJson json 字符串 请求体
     * @param callback        回调接口，返回结果
     * @param methodKey       请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final IRequestCallback callback, int methodKey) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
            //// TODO: 2016/12/21 异常处理
        }
        RequestMethodEnum methodEnum = RequestMethodEnum.fromKey(methodKey);
        if (ObjectUtils.isEmpty(methodEnum)) {
            throw new NullPointerException("[methodaaaaaaaEnum==null，请重新确认key是否有效==]");
        }
        int method = -1;
        switch (methodEnum) {
            case GET:
                method = Request.Method.GET;
                break;
            case POST:
                method = Request.Method.POST;
                break;
            case PUT:
                method = Request.Method.PUT;
                break;
            case DELETE:
                method = Request.Method.DELETE;
                break;
            default:
                break;
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method,
                url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response != null ? response.toString() : null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Throwable throwable = error.fillInStackTrace();
                callback.onFailure(NetCodeEnum.FAILURE.getValue(), NetCodeEnum.FAILURE.getKey(), throwable);
            }
        });
        //添加到请求队列
        App.mRequestQueue.add(request);
    }


}
