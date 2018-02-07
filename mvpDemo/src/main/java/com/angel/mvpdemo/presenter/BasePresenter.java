package com.angel.mvpdemo.presenter;

import com.angel.mvpdemo.view.BaseView;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/03
 *     version: 1.0
 *     desc   : 用于协调view和model的中间类的基类
 * </pre>
 */
public class BasePresenter<V extends BaseView> {
    private WeakReference<V> weakReference;

    /**
     * 建立连接的方法
     */
    public void attach(V subView) {
        weakReference = new WeakReference<V>(subView);

    }

    /**
     * 断开连接
     */
    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 获取view对象
     *
     * @return
     */
    public V getSubView() {
        return weakReference.get();
    }

}
