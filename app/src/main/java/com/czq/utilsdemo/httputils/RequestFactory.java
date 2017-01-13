package com.czq.utilsdemo.httputils;

/**
 * 公司：westsoft
 * 作者：程志强
 * 时间：2016/12/21 10:26
 * 描述：改类的作用：返回IRequestManager对象，这个IRequestManager的实现类可以是使用velley实现，
 * 也可以是okhttp等activity/fragment/Presenter中，只有调用getRequestManager()方法就能得到http
 * 请求的操作接口
 */

public class RequestFactory {
    public static IRequestManager getIRequestManager() {
//        return VolleyRequestManager.getIntence();
        return OkHttpRequestManager.getInstance();
    }
}
