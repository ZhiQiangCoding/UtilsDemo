package com.czq.utilsdemo.httputils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.czq.utilsdemo.App;

import org.json.JSONException;
import org.json.JSONObject;

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
     * @param url      请求url
     * @param callBack 回调接口，返回结果
     */
    @Override
    public void get(String url, final IRequestCallBack callBack) {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error);
            }
        });
        //添加到请求队列
        App.mRequestQueue.add(request);
    }

    /**
     * post请求
     *
     * @param url             请求url
     * @param requestBodyJson json 字符串 请求体
     * @param callBack        回调接口，返回结果
     */
    @Override
    public void post(String url, String requestBodyJson, IRequestCallBack callBack) {
        requestWithBody(url, requestBodyJson, callBack, Request.Method.POST);
    }


    @Override
    public void put(String url, String requestBodyJson, IRequestCallBack callBack) {
        requestWithBody(url, requestBodyJson, callBack, Request.Method.PUT);
    }

    @Override
    public void delete(String url, String requestBodyJson, IRequestCallBack callBack) {
        requestWithBody(url, requestBodyJson, callBack, Request.Method.DELETE);
    }

    /**
     * 封装带请求参数请求体的请求方法
     *
     * @param url             请求url
     * @param requestBodyJson json 字符串 请求体
     * @param callBack        回调接口，返回结果
     * @param method          请求方法
     */
    private void requestWithBody(String url, String requestBodyJson, final IRequestCallBack callBack, int method) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(requestBodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
            //// TODO: 2016/12/21 异常处理 
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccess(response != null ? response.toString() : null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error);
            }
        });
        //添加到请求队列
        App.mRequestQueue.add(request);
    }

}
