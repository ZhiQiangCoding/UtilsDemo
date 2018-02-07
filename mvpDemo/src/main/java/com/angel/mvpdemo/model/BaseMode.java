package com.angel.mvpdemo.model;

import java.util.List;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/03
 *     version: 1.0
 *     desc   : 用于获取数据的模型层
 * </pre>
 */
public interface BaseMode {
    /**
     * 获取数据的方法，异步的一般需要接口回调传回来
     *
     * @param onLoadCompleteListener
     */
    void getData(OnLoadCompleteListener onLoadCompleteListener);

    public interface OnLoadCompleteListener {
        /**
         * 请求完成的回调
         */
        void onLoadComplete(List<String> lists);
    }
}
