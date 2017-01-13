package com.czq.utilsdemo.httputils;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/12/21 09:19
 * 描述：此接口提供的就是http 通用的方法，该接口可以用于volley okhttp等其他方式来实现
 * 接口说明：get方法参数包含一个url,以及返回数据的接口
 * post/put/delete 方法还需要一个请求参数
 */

public interface IRequestManager {
    void get(String url, IRequestCallBack callBack);

    void post(String url, String requestBodyJson, IRequestCallBack callBack);

    void put(String url, String requestBodyJson, IRequestCallBack callBack);

    void delete(String url, String requestBodyJson, IRequestCallBack callBack);
}
