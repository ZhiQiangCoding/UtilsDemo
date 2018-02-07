package com.angel.retorfitrxmvpdemo.presenter;

import com.angel.retorfitrxmvpdemo.view.iview.BaseView;

import java.lang.ref.WeakReference;

/**
 * <pre>
 *     author : 程志强
 *     e-mail : 18394188838@163.com
 *     time   : 2017/04/04
 *     version: 1.0
 *     desc   : xxxx描述
 * </pre>
 */
public class BasePresenter<V extends BaseView> {
    private WeakReference<V> weakReference;

    /**
     * 建立建立连接
     *
     * @param subView
     */
    public void attach(V subView) {
        weakReference = new WeakReference<V>(subView);
    }

    /**
     * 取消连接
     */
    public void deAttach() {
        if (weakReference != null) {
            weakReference.clear();
            weakReference = null;
        }
    }

    /**
     * 获取保持关系的view
     *
     * @return
     */

    public V getSubView() {
        return weakReference.get();
    }
}
