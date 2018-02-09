package com.czq.utilsdemo.httputils;

/**
 * ================================================
 * 作    者：程志强
 * 邮    箱：zhiqiang8838@163.com
 * 版    本：1.0.0
 * 创建日期：2018/02/07  16:40
 * 包    名：com.yh.baselibrary.net
 * 描    述：网络请求回调
 * 修订历史：
 * ================================================
 */
public interface IRequestCallback {
    /**
     * 成功
     *
     * @param json json字符串
     */
    void onSuccess(String json);

    /**
     * 失败
     *
     * @param msg
     * @param code
     * @param throwable
     */
    void onFailure(String msg, String code, Throwable throwable);

}
