package com.czq.utilsdemo.httputils;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/12/21 09:24
 * 描述：请求返回成功、失败，成功时，把服务器返回的结果回调出去，失败时回调异常信息
 * onSuccess中的参数类型，可以是字符串，也可以是jsonobject对象等
 */

public interface IRequestCallBack {
    void onSuccess(String response);

    void onFailure(Throwable throwable);
}
