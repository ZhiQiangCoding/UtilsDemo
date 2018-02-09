package com.czq.utilsdemo.httputils;

import java.util.Map;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2018/02/07  16:54
 * 包    名：com.yh.baselibrary.net
 * 描    述：http请求通用方法，该接口可以用于volley okhttp等其他方式来实现，get方法参数包含一个url,以及返回数据的接口，post/put/delete 方法还需要一个请求参数
 * 修订历史：
 * ================================================
 */
public interface IRequestManager {
    /**
     * get请求
     *
     * @param url      url地址
     * @param callback 响应回调
     */
    void get(String url, IRequestCallback callback);

    /**
     * post请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    void post(String url, String requestParamJson, IRequestCallback callback);

    /**
     * post请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    void post(String url, Map<String, Object> requestParams, IRequestCallback callback);

    /**
     * put请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    void put(String url, String requestParamJson, IRequestCallback callback);

    /**
     * put请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    void put(String url, Map<String, Object> requestParams, IRequestCallback callback);

    /**
     * delete请求
     *
     * @param url              url地址
     * @param requestParamJson 请求参数（json对象）
     * @param callback         响应回调
     */
    void delete(String url, String requestParamJson, IRequestCallback callback);

    /**
     * delete请求(表单)
     *
     * @param url           url地址
     * @param requestParams 请求参数（map对象--键值对）
     * @param callback      响应回调
     */
    void delete(String url, Map<String, Object> requestParams, IRequestCallback callback);
}
